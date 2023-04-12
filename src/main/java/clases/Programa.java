package clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;


public class Programa {
	
	private ArrayList<Equipo> equipos;
	private ArrayList<Ronda> fase;
	private ArrayList<Persona> personas;
	
	private String resultadosFile;
	private String conexionYPuntosFile;
	private int PuntosRachaRonda;
	private int PuntosRachaFase;
	private File resul;
	private File conexionYPuntos;
	
	Scanner sc = new Scanner(System.in);
	Scanner input = new Scanner(System.in);
	Scanner scanR;
	Scanner scanC;
	
	public Programa() {
		this.equipos = new ArrayList<>();
		this.fase = new ArrayList<>();
		this.personas = new ArrayList<>();
	}
	
	public void inicializar() {
		solicitarResultados();
		solicitarValoresConexion();
		cerrarScanners();
		
	}

	
	private void solicitarResultados() {
		
		System.out.println("Ingrese el path de los resultados: ");
		//path = D:\Eclipse\eclipse-workspace\programaDeportivo\src\main\resources\resultado.txt
		resultadosFile = sc.nextLine();	
		resul = new File(resultadosFile);
		try {
			scanR = new Scanner(resul);
			while(scanR.hasNextLine()) {
				//ESCANEO LA LINEA
				String linea = scanR.nextLine();
				//GENERO UN ARRAY
				String[] separado = new String[5];
				//SEPARO POR COMAS EN EL ARRAY, OCUPANDO EL TOTAL DE LAS POSICIONES
				separado = linea.split("\\s*,\\s*");
				if(Verificador.verificarArray(separado, 5)) {
					Ronda nuevaRonda = Buscador.buscarRonda(Integer.parseInt(separado[0]), fase);
					//ESTE METODO CREA EL EQUIPO, Y SI YA EXISTE NO HACE NADA	
					agregarEquipo(separado[2]);
					agregarEquipo(separado[4]) ;
					//VERIFICO (SOLICITADO) QUE SE INGRESEN INT EN LA CANT DE GOLES
					if(esInt(separado[1])&& esInt(separado[3])) {	
						Partido parti = new Partido(separado[1], darEquipo(separado[2]), separado[3], darEquipo(separado[4]));
						//CREO EL PARTIDO Y LO AGREGO A LA RONDA
						nuevaRonda.agregarPartido(parti);
						}else {
							System.out.println("Ingresaste valores que no son enteros loko!!");
						}
					fase.add(nuevaRonda);

				}else {
					System.out.println("Datos incorrectos, por favor verifique");
				}
			}
			

		} catch (FileNotFoundException e) {
			System.out.println("Ingresaste algo mal..." + e.getMessage());
		}
		
		

	}
	
	
	public void solicitarValoresConexion() {
		System.out.println("Ingrese el path de la conexión y el valor de los puntajes: ");
		//path = D:\Eclipse\eclipse-workspace\programaDeportivo\src\main\resources\conexionYPuntos.txt
		conexionYPuntosFile= sc.nextLine();	
		conexionYPuntos = new File(conexionYPuntosFile);
		
		try {
			scanC = new Scanner(conexionYPuntos);
			while(scanC.hasNextLine()) {
				//ESCANEO LA LINEA
				String linea = scanC.nextLine();
				//GENERO UN ARRAY
				String[] separadoConexion = new String[9];
				//SEPARO POR COMAS EN EL ARRAY, OCUPANDO EL TOTAL DE LAS POSICIONES
				separadoConexion = linea.split("\\s*,\\s*");
				if(Verificador.verificarArray(separadoConexion, 10)) {
					solicitarPronosticoDB(separadoConexion[0],separadoConexion[1],separadoConexion[2], separadoConexion[3], separadoConexion[4]);
					ResultadoEnum.ganador.setValor(Integer.parseInt(separadoConexion[5]));
					ResultadoEnum.empate.setValor(Integer.parseInt(separadoConexion[6]));
					ResultadoEnum.perdedor.setValor(Integer.parseInt(separadoConexion[7]));
					PuntosRachaRonda = Integer.parseInt(separadoConexion[8]);
					PuntosRachaFase = Integer.parseInt(separadoConexion[9]);
				}else {
					System.out.println("Datos incorrectos, por favor verifique");
				}
			}
			

		} catch (FileNotFoundException e) {
			System.out.println("Ingresaste algo mal..." + e.getMessage());
		}	
	}
	

	



	
	public void jugar() {
		for (Persona pp : personas) {
			pp.jugar();
			int sumaRondas = 0;
			for (Ronda r : fase) {
				sumaRondas += r.cantPartidos();
				if(r.cantPartidos() <= pp.getRacha()) {
					pp.setPuntos(pp.getPuntos() + this.PuntosRachaRonda);
				}

			}
			if((this.fase.size() > 1) && (sumaRondas == pp.getRacha())) {
				pp.setPuntos(pp.getPuntos() + this.PuntosRachaFase);
			}

			
			imprimirResultado(pp.getNombre(),pp.getPuntos(),pp.getCantAciertos());
		}
	}
	
	private void imprimirResultado(String nombre, int puntos, int cantAciertos) {
		System.out.println(nombre + " puntos:" + puntos +"  cantidad aciertos: " + cantAciertos);
		System.out.println("-----------------------------------------");
	}
	
	
	public void cargarPronosticoAPersona(String nombre, Pronostico p) {
		Persona per = Buscador.buscarPersona(nombre, personas);
		per.agregarPronostico(p);
		
	}
	
	public void agregarPersona(String nombre) {
		if(Buscador.buscarPersona(nombre, personas) == null) {
			Persona p = new Persona(nombre);
			personas.add(p);
		}
	}
	
	
	private boolean esInt(String cadena) {
		//Recibe string xq levanta la cadena del archivo
	    boolean es = true;
		try {
	        Integer.parseInt(cadena);
	        
	    } catch (NumberFormatException e1) {
	    	es =  false;
	    }
	return es;
	}
	
	
	public void agregarEquipo(String nombre) {
		if(!Buscador.buscarEquipo(nombre, equipos)) {
			Equipo equi = new Equipo(nombre, "A confirmar");
			equipos.add(equi);
		}
	}
	
	
	public void agregarEquipo(Equipo e) {
		if(!Buscador.buscarEquipo(e.getNombre(), equipos)) {
		equipos.add(e);
		}
	}
	
	private Equipo darEquipo(String nombre) {
		int i = 0;
		Equipo encontrado = null;
		
		while(i < equipos.size() && encontrado == null) {
			if(equipos.get(i).getNombre().equals(nombre) ) {
				encontrado = equipos.get(i);
			} else {
				i++;
			}		
		}
	return encontrado;
	}
	
	private void solicitarPronosticoDB(String host1, String puerto1, String nombreBD1, String usuario1, String contrasena1 ) {
		  try {
		    String host = host1;
		    String puerto = puerto1;
		    String nombreBD = nombreBD1;
		    String usuario =  usuario1;
		    String contrasena = contrasena1;

		    // CONEXION A LA BASE
		    String url = "jdbc:mysql://" + host + ":" + puerto + "/" + nombreBD;
		    Connection conexion = DriverManager.getConnection(url, usuario, contrasena);

		    System.out.println("Conexión exitosa a la base de datos " + nombreBD + "\n");
		    

		    // CONSULTA DE PRONOSTICOS
		    Statement consulta = conexion.createStatement();
		    ResultSet resultado = consulta.executeQuery("SELECT * FROM pronosticos");

		    while (resultado.next()) {
		    	String equipo1 = resultado.getString("nombreEquipo1");
		        String resultadoEquipo = resultado.getString("resultadoEquipo");
		        String equipo2 = resultado.getString("nombreEquipo2");
		        String persona = resultado.getString("nombrePersona");

		        Partido partido = Buscador.buscarPartido(equipo1, equipo2, fase);

		        if (partido != null) {
		        	Equipo eq1 = darEquipo(equipo1);
		        	Pronostico pronostico = new Pronostico(partido, eq1, resultadoEquipo);

		        	agregarPersona(persona);
		        	cargarPronosticoAPersona(persona, pronostico);
		        } else {
		        	System.out.println("El partido entre " + equipo1 + " y " + equipo2 + " no existe.");
		        }
		    }

		    resultado.close();
		    consulta.close();
		    conexion.close();
		    sc.close();

		  } catch (SQLException e) {
		    System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
		  }
		}
	
	private void cerrarScanners() {
		//CIERRO LOS SCANNERS
		sc.close();
		input.close();
		scanR.close();
		scanC.close();
	}

}	
	
	
	

