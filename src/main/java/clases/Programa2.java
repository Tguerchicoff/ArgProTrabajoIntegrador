package clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;


public class Programa2 {
	
	private ArrayList<Equipo> equipos;
	private ArrayList<Ronda> fase;
	private ArrayList<Persona> personas;
	
	
	final static int largoCorrecto = 4;
	private int contador = 1;
	private String resultadosFile;
	private String conexionYPuntosFile;
	private String pronosticosFile;
	private int PuntosRachaRonda;
	private int PuntosRachaFase;
	private File resul;
	private File conexionYPuntos;
	
	Scanner sc = new Scanner(System.in);
	Scanner input = new Scanner(System.in);
	Scanner scanR;
	Scanner scanP;
	
	public Programa2() {
		this.equipos = new ArrayList<>();
		this.fase = new ArrayList<>();
		this.personas = new ArrayList<>();
	}
	
	public void inicializar() {
		solicitarResultados();
		solicitarValoresConexion();
		
		//solicitarPronostico();
		cerrarScanners();
		
	}

	
	private void solicitarResultados() {
		
		System.out.println("Ingrese el path de los resultados: ");
		//path = D:\Eclipse\eclipse-workspace\programaDeportivo\src\main\resources\resultado.txt
		resultadosFile = sc.nextLine();	
		resul = new File(resultadosFile);
		try {
			scanR = new Scanner(resul);
			Ronda nuevaRonda = new Ronda(contador);
			contador++;
			while(scanR.hasNextLine()) {
				//ESCANEO LA LINEA
				String linea = scanR.nextLine();
				//GENERO UN ARRAY
				String[] separado = new String[4];
				//SEPARO POR COMAS EN EL ARRAY, OCUPANDO EL TOTAL DE LAS POSICIONES
				separado = linea.split("\\s*,\\s*");
				if(verificarArray(separado)) {
					//ESTE METODO CREA EL EQUIPO, Y SI YA EXISTE NO HACE NADA	
					agregarEquipo(separado[1]);
					agregarEquipo(separado[3]) ;
					//VERIFICO (SOLICITADO) QUE SE INGRESEN INT EN LA CANT DE GOLES
					if(esInt(separado[0])&& esInt(separado[2])) {	
						Partido parti = new Partido(separado[0], darEquipo(separado[1]), separado[2], darEquipo(separado[3]));
						//CREO EL PARTIDO Y LO AGREGO A LA RONDA
						nuevaRonda.agregarPartido(parti);
						}else {
							System.out.println("Ingresaste valores que no son enteros loko!!");
						}
				}else {
					System.out.println("Datos incorrectos, por favor verifique");
				}
			}
			//AGREGO LA RONDA A LA FASE (CONJUNTO DE RONDAS)
			fase.add(nuevaRonda);
			scanR.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ingresaste algo mal..." + e.getMessage());
		}
		
		

	}
	
	private void solicitarPronostico() {
		try {
		System.out.println("Ingrese el path de los pronosticos: ");
		//path = D:\Eclipse\eclipse-workspace\programaDeportivo\src\main\resources\ronda.txt
	
		pronosticosFile = sc.nextLine();
		File prono = new File(pronosticosFile);
			scanP = new Scanner(prono);
			while(scanP.hasNextLine()) {
				
				//ESCANEO LA LINEA
				String linea = scanP.nextLine();
				//GENERO UN ARRAY
				String[] separadoP = new String[4];
				//SEPARO POR COMAS EN EL ARRAY, OCUPANDO EL TOTAL DE LAS POSICIONES
				separadoP = linea.split("\\s*,\\s*");
				//VERIFICO QUE LA FASE TENGA POR LO MENOS 1 RONDA
				if(!fase.isEmpty()) {
					//VERIFICO QUE EL ARRAY CONTENGA LA CANTIDAD DE DATOS QUE NECESITO
					if(verificarArray(separadoP)) {
						//BUSCO EL PARTIDO A PARTIR DE LOS 2 EQUIPOS PARTICIPANTES
					if(buscarPartido(separadoP[0],separadoP[2]) != null) {
						Partido p = buscarPartido(separadoP[0],separadoP[2]);
						
						// SI EL PARTIDO EXITE GENERO EL PRONOSTICO
						Pronostico pronostic = new Pronostico(p, darEquipo(separadoP[0]), separadoP[1]);
						// SI LA PERSONA NO EXISTE LA CREO
						if(buscarPersona(separadoP[3])==null) {
							
							agregarPersona(separadoP[3]);
						}
						//CARGO EL PRONOSTICO A SU DUENIO
						cargarPronosticoAPersona(separadoP[3], pronostic);
						}else {
							System.out.println("El equipo no participa de tal partido");
						}
					}else {
						System.out.println("Datos insuficientes");
					}
					}else {
						System.out.println("Partido inexsistente, por favor verifique");
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
			Scanner scanC = new Scanner(conexionYPuntos);
			while(scanC.hasNextLine()) {
				//ESCANEO LA LINEA
				String linea = scanC.nextLine();
				//GENERO UN ARRAY
				String[] separadoConexion = new String[9];
				//SEPARO POR COMAS EN EL ARRAY, OCUPANDO EL TOTAL DE LAS POSICIONES
				separadoConexion = linea.split("\\s*,\\s*");
				if(verificarArray(separadoConexion, 10)) {
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
		
		
		
//		
//		//PIDE LOS VALORES DE LOS DISTINTOS TIPOS DE PUNTOS (SOLICITADO)
//		System.out.println("Ingrese el valor de cada resultado ganador");
//		//array[5]
//		ResultadoEnum.ganador.setValor(input.nextInt());
//		
//		System.out.println("Ingrese el valor de cada resultado de empate");
//		//array[6]
//		ResultadoEnum.empate.setValor(input.nextInt());
//		
//		System.out.println("Ingrese el valor de cada resultado perdido");
//		//array[7]
//		ResultadoEnum.perdedor.setValor(input.nextInt());
//		
//		System.out.println("Ingrese el valor por racha en ronda");
//		//array[8]
//		PuntosRachaRonda = input.nextInt();
//		//array[9]
//		System.out.println("Ingrese el valor por racha en fase");
//		PuntosRachaFase = input.nextInt();
//		
	}
	
	private Partido buscarPartido(String e1, String e2) {
        int i = 0;
		Partido parti = null;
        while(i < fase.size() && parti == null) {
        	Ronda rondaBuscando = fase.get(i);
        	parti = rondaBuscando.buscarPartido(e1, e2);
        	i++;
        		}
        
        return parti;
	}
	
	
	private boolean verificarLargo(String[] array) {
		boolean resultado = true;
		if(array.length != largoCorrecto) {
			resultado = false;
		}		
		return resultado;
	}

	
	private boolean verificarArray(String[] array) {
		boolean verificado = verificarLargo(array);
		if(verificado) {
			for (String str : array) {
				if(str == null || str.length() == 0) {
					verificado = false;
				}
			}
		}
	return verificado;
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
				if((this.fase.size() > 1) && (sumaRondas == pp.getRacha())) {
					pp.setPuntos(pp.getPuntos() + this.PuntosRachaFase);
				}
			}

			System.out.println(fase.get(0).cantPartidos());
			imprimirResultado(pp.getNombre(),pp.getPuntos(),pp.getCantAciertos());
		}
	}
	
	private void imprimirResultado(String nombre, int puntos, int cantAciertos) {
		System.out.println(nombre + " puntos:" + puntos +"  cantidad aciertos: " + cantAciertos);
	}
	
	
	public void cargarPronosticoAPersona(String nombre, Pronostico p) {
		Persona per = buscarPersona(nombre);
		per.agregarPronostico(p);
		
	}
	

	public void mostrarEquipos() {
		for (Equipo equipo : equipos) {
			System.out.println(equipo);
		}
	}
	
	
	public void agregarPersona(Persona p) {
		if(buscarPersona(p.getNombre()) == null) {
		personas.add(p);
		}else {
			System.out.println("La persona " +p.getNombre()+ " ya se encuentra inscripta");
		}
	}
	
	
	public void agregarPersona(String nombre) {
		if(buscarPersona(nombre) == null) {
		Persona p = new Persona(nombre);
		personas.add(p);
		}else {
			System.out.println("La persona " +nombre+ " ya se encuentra inscripta");
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
	
	
	private Persona buscarPersona(String nombre) {
		int i = 0;
		Persona buscada = null;
		
		while(i < personas.size() && buscada == null) {
			if(personas.get(i).getNombre().equals(nombre)){
				buscada = personas.get(i);
			}else {
				i++;
			}
		}
		return buscada;
	}
	
	public void agregarEquipo(String nombre) {
		
		if(!buscarEquipo(nombre)) {
			Equipo equi = new Equipo(nombre, "A confirmar");
			equipos.add(equi);
		//}else {
		// System.out.println("El equipo ya esta registrado");
		}
	}
	
	
	public void agregarEquipo(Equipo e) {
		if(!buscarEquipo(e.getNombre())) {
		equipos.add(e);
		//}else {
		// System.out.println("El equipo ya esta registrado");
		}
	}

	
	private boolean buscarEquipo(String nombre) {
		int i = 0;
		boolean encontrado = false;
		
		while(i < equipos.size() && !encontrado) {
			if(equipos.get(i).getNombre().equals(nombre)) {
				encontrado = true;
				//System.out.println("Encontre el equipo!");
			} else {
				i++;
			}		
		}
		return encontrado;
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
		    Scanner sc = new Scanner(System.in);

		    System.out.println("Ingrese los datos de conexión a la base de datos: ");
		    System.out.println("Host: ");
		    //array[0]
		    String host = host1;
		    System.out.println("Puerto: ");
		  //array[1]
		    String puerto = puerto1;
		    System.out.println("Nombre de la base de datos: ");
		  //array[2]
		    String nombreBD = nombreBD1;
		    System.out.println("Nombre de usuario: ");
		  //array[3]
		    String usuario =  usuario1;
		    System.out.println("Contraseña: ");
		  //array[4]
		    String contrasena = contrasena1;

		    // Conexión a la base de datos
		    String url = "jdbc:mysql://" + host + ":" + puerto + "/" + nombreBD;
		    Connection conexion = DriverManager.getConnection(url, usuario, contrasena);

		    System.out.println("Conexión exitosa a la base de datos " + nombreBD);

		    // Consulta de pronósticos
		    Statement consulta = conexion.createStatement();
		    ResultSet resultado = consulta.executeQuery("SELECT * FROM pronosticos");

		    while (resultado.next()) {
		      String equipo1 = resultado.getString("nombreEquipo1");
		      String resultadoEquipo = resultado.getString("resultadoEquipo");
		      String equipo2 = resultado.getString("nombreEquipo2");
		      String persona = resultado.getString("nombrePersona");

		      // Buscar partido
		      Partido partido = buscarPartido(equipo1, equipo2);

		      if (partido != null) {
		        // Generar pronóstico
		        Equipo eq1 = darEquipo(equipo1);
		        Equipo eq2 = darEquipo(equipo2);
		        Pronostico pronostico = new Pronostico(partido, eq1, resultadoEquipo);

		        // Cargar pronóstico a la persona
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
		//cierro todos los scanners que utilice en el programa
		sc.close();
		input.close();
		scanR.close();
		//scanP.close();
	}
	
}	
	
	
	

