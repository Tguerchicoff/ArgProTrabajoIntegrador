package trabajoIntegrador;
import java.util.ArrayList;


public class Persona {
	private String nombre;
	private ArrayList<Pronostico> pronosticos;
	private int puntos;
	private int cantAciertos;
	
	
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
			if(pronostico.exito()) {
				cantAciertos++;
			}
		}

	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void resultado() {
		puntos(); 
		System.out.println(nombre + " puntos=" + puntos + " pronosticos:" + this.pronosticos.size() + " y la cantidad aciertos: " + this.cantAciertos + " exito:");
	}
	

	@Override
	public String toString() {
		return nombre;
	}
	
	
}
