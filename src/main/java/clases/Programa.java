package clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


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
		jugar();
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
					EquipoHandler.agregarEquipo(separado[2], equipos);
					EquipoHandler.agregarEquipo(separado[4], equipos);
					//VERIFICO (SOLICITADO) QUE SE INGRESEN INT EN LA CANT DE GOLES
					if(Verificador.esInt(separado[1])&& Verificador.esInt(separado[3])) {	
						Partido parti = new Partido(separado[1], EquipoHandler.darEquipo(separado[2], equipos), separado[3], EquipoHandler.darEquipo(separado[4], equipos));
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
					ConectorBaseDeDatos.conectarBd(separadoConexion[0],separadoConexion[1],separadoConexion[2], separadoConexion[3], separadoConexion[4], fase, personas, equipos);
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

	
	private void jugar() {
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
	
	private void cerrarScanners() {
		sc.close();
		input.close();
		scanR.close();
		scanC.close();
	}

}	
	
	
	

