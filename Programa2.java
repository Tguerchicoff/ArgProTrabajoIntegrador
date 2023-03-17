package trabajoIntegrador;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;



public class Programa2 {
	Scanner sc = new Scanner(System.in);
	private ArrayList<Equipo> equipos;
	private ArrayList<Pronostico> pronosticos;
	private ArrayList<Partido> partidos;
	private ArrayList<Persona> personas;
	
	
	
	private String resultadosFile;
	private String pronosticosFile;
	
	
	public Programa2() {
		this.equipos = new ArrayList<>();
		this.partidos = new ArrayList<>();
		this.personas = new ArrayList<>();
	}
	
	
	public void solicitarPaths() {
		
		System.out.println("Ingrese el path de los resultados: ");
		resultadosFile = sc.nextLine();
		File resul = new File(resultadosFile);
		try {
			Scanner scanR = new Scanner(resul);
			while(scanR.hasNextLine()) {
				
				//scaneo la linea
				String linea = scanR.nextLine();
				//genero el array que necesito
				String[] separado = new String[4];
				//spliteo la linea y la agrego al array
				separado = linea.split("\\s*,\\s*");
				System.out.println(separado[3]);
				System.out.println(separado[1].equals("Argentina"));
					if(buscarEquipo(separado[1]) && buscarEquipo(separado[3])) {
						// si los equipos existen genero el partido
						Partido parti = new Partido(separado[0], darEquipo(separado[1]), separado[2], darEquipo(separado[3]));
						partidos.add(parti);
					} else {
						System.out.println("Alguno de los equipo es inexsistente, por favor verifique");
				
				}
			}
	
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
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
				if(!partidos.isEmpty()) {
					if(buscarPartido(Integer.parseInt(separadoP[0])) != null) {
						Partido p = buscarPartido(Integer.parseInt(separadoP[0]));
						if(p.equipoParticipa(separadoP[1])) {
						// si los equipos existen genero el partido
						Pronostico pronostic = new Pronostico(p, darEquipo(separadoP[1]), separadoP[2]);
						if(buscarPersona(separadoP[3])==null) {
							agregarPersona(separadoP[3]);
						}
						cargarPronosticoAPersona(separadoP[3], pronostic);
					}else {
						System.out.println("El equipo no participa de tal partido");
					}
					}else {
						System.out.println("Partido inexsistente, por favor verifique");
					}
				}
			}
				
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		

		
	}
	
	public void jugar() {
		for (Persona pp : personas) {
			pp.resultado();
		}
	}
	
	public void cargarPronosticoAPersona(String nombre, Pronostico p) {
		Persona per = buscarPersona(nombre);
		per.agregarPronostico(p);
		
	}
	
	
	private Partido buscarPartido(int num) {
		Partido p = null;
		num = num -1;
		if(!partidos.isEmpty()) {
			if(partidos.get(num) != null) {
				p  = partidos.get(num);
			}
		}
		return p;
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
				System.out.println("Encontre el equipo!");
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
	
	
	

