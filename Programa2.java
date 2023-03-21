package trabajoIntegrador;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class Programa2 {
	Scanner sc = new Scanner(System.in);
	private ArrayList<Equipo> equipos;
	private ArrayList<Ronda> fase;
	private ArrayList<Persona> personas;
	
	
	final static int largoCorrecto = 4;
	private int contador = 1;
	private String resultadosFile;
	private String pronosticosFile;
	private int PuntosRachaRonda;
	private int PuntosRachaFase;
	
	
	public Programa2() {
		this.equipos = new ArrayList<>();
		this.fase = new ArrayList<>();
		this.personas = new ArrayList<>();
	}
	
	
	public void inicializar() {
		solicitarValoresResultados();
		solicitarResultados();
		solicitarPronostico();
		

		
	}

	
	private void solicitarPronostico() {
		try {
		System.out.println("Ingrese el path de los pronosticos: ");
		pronosticosFile = sc.nextLine();
		File prono = new File(pronosticosFile);
			Scanner scanP = new Scanner(prono);
			while(scanP.hasNextLine()) {
				
				//scaneo la linea
				String linea = scanP.nextLine();
				//genero el array que necesito
				String[] separadoP = new String[4];
				//spliteo la linea y la agrego al array
				separadoP = linea.split("\\s*,\\s*");
				if(!fase.isEmpty()) {
					if(verificarArray(separadoP)) {
					if(buscarPartido(separadoP[0],separadoP[2]) != null) {
						Partido p = buscarPartido(separadoP[0],separadoP[2]);
						//if(p.equipoParticipa(separadoP[0])) {
						// si los equipos existen genero el partido
						Pronostico pronostic = new Pronostico(p, darEquipo(separadoP[0]), separadoP[1]);
						if(buscarPersona(separadoP[3])==null) {
							agregarPersona(separadoP[3]);
						}
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
		

	private void solicitarResultados() {
		boolean seguir = true;
		
		System.out.println("Ingrese el path de los resultados: ");
		resultadosFile = sc.nextLine();	
		File resul = new File(resultadosFile);
		try {
			Scanner scanR = new Scanner(resul);
			Ronda nuevaRonda = new Ronda(contador);
			contador++;
			while(scanR.hasNextLine()) {
				//scaneo la linea
				String linea = scanR.nextLine();
				//genero el array que necesito
				String[] separado = new String[4];
				//spliteo la linea y la agrego al array
				separado = linea.split("\\s*,\\s*");
				if(verificarArray(separado)) {
						if(buscarEquipo(separado[1]) && buscarEquipo(separado[3])) {
							if(esInt(separado[0])&& esInt(separado[2])) {	
							// si los equipos existen genero el partido
							Partido parti = new Partido(separado[0], darEquipo(separado[1]), separado[2], darEquipo(separado[3]));
							nuevaRonda.agregarPartido(parti);
							}else {
								System.out.println("Ingresaste valores que no son enteros loko!!");
							}

					
						}else {
							System.out.println("Alguno de los equipo es inexsistente, por favor verifique");
						}
				}else {
					System.out.println("Datos incorrectos, por favor verifique");
				}
			}
			fase.add(nuevaRonda);
		} catch (FileNotFoundException e) {
			System.out.println("Ingresaste algo mal..." + e.getMessage());
		}

	}
	
	
	public void solicitarValoresResultados() {
		Scanner input = new Scanner(System.in);
		
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
				if(sumaRondas == pp.getRacha()) {
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
	
	
	public void agregarEquipo(Equipo e) {
		if(!buscarEquipo(e.getNombre())) {
		equipos.add(e);
		}else {
		 System.out.println("El equipo ya esta registrado");
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
}	
	
	
	

