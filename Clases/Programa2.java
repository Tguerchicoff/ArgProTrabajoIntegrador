package clases;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class Programa2 {
	
	private ArrayList<Equipo> equipos;
	private ArrayList<Ronda> fase;
	private ArrayList<Persona> personas;
	
	
	final static int largoCorrecto = 4;
	private int contador = 1;
	private String resultadosFile;
	private String pronosticosFile;
	private int PuntosRachaRonda;
	private int PuntosRachaFase;
	
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
		solicitarValoresResultados();
		solicitarResultados();
		solicitarPronostico();
		cerrarScanners();
		
	}

	
	private void solicitarResultados() {
		
		System.out.println("Ingrese el path de los resultados: ");
		resultadosFile = sc.nextLine();	
		File resul = new File(resultadosFile);
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
	
	public void solicitarValoresResultados() {
		
		//PIDE LOS VALORES DE LOS DISTINTOS TIPOS DE PUNTOS (SOLICITADO)
		System.out.println("Ingrese el valor de cada resultado ganador");
		ResultadoEnum.ganador.setValor(input.nextInt());
		
		System.out.println("Ingrese el valor de cada resultado de empate");
		ResultadoEnum.empate.setValor(input.nextInt());
		
		System.out.println("Ingrese el valor de cada resultado perdido");
		ResultadoEnum.perdedor.setValor(input.nextInt());
		
		System.out.println("Ingrese el valor por racha en ronda");
		PuntosRachaRonda = input.nextInt();
		
		System.out.println("Ingrese el valor por racha en fase");
		PuntosRachaFase = input.nextInt();
		
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
	
	private void cerrarScanners() {
		//cierro todos los scanners que utilice en el programa
		sc.close();
		input.close();
		scanR.close();
		scanP.close();
	}
	
}	
	
	
	

