package trabajoIntegrador;
import java.util.ArrayList;


public class Persona {
	private String nombre;
	private ArrayList<Pronostico> pronosticos;
	private int puntos;
	
	
	public Persona(String nombre) {
		this.nombre = nombre;
		this.pronosticos = new ArrayList<>();
	}
	
	public void agregarPronostico(Pronostico p) {
		pronosticos.add(p);
	}
	
	
	private void puntos() {
		for (Pronostico pronostico : pronosticos) {
			this.puntos += pronostico.puntos();
		}

	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void resultado() {
		puntos();
		System.out.println("El pronostico de: " + this.nombre + " es de: " + this.puntos);
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", pronosticos=" + pronosticos + ", puntos=" + puntos + " pronosticos:" + this.pronosticos.size() ;
	}
	
	
}
