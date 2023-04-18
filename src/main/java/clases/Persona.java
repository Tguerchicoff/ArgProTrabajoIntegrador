package clases;

import java.util.ArrayList;


public class Persona {
	private String nombre;
	private ArrayList<Pronostico> pronosticos;
	private int puntos;
	private int cantAciertos;
	private int racha;
	
	public Persona(String nombre) {
		this.nombre = nombre;
		this.pronosticos = new ArrayList<>();
	}
	
	public void agregarPronostico(Pronostico p) {
		pronosticos.add(p);

	}
	
	
	public void  jugar() {
		puntos();
	}
	
	private void puntos() {
		for (Pronostico pronostico : pronosticos) {
			this.puntos += pronostico.puntos();
			if(pronostico.exito()) {
				cantAciertos++;
				racha++;
			}else {
				racha = 0;
			}
		}

	}
	
	public int getPuntos() {
		return this.puntos;
	}
	
	public int getCantPronosticos() {
		return this.pronosticos.size();
	}
	
	public int getCantAciertos() {
		return this.cantAciertos;
	}
	
	public void setPuntos(int nuevosPuntos) {
		this.puntos = nuevosPuntos;
	}
	
	public int getRacha() {
		return this.racha;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void resultado() {
		puntos(); 
		System.out.println(nombre + " puntos:" + puntos +"  cantidad aciertos: " + this.cantAciertos);
	}
	

	@Override
	public String toString() {
		return nombre;
	}
	
	
}
