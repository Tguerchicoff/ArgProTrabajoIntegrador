package clases;

import java.util.ArrayList;


public class Ronda {
	private int nro;
	private ArrayList<Partido> partidos;
	
	
	
	public Ronda(int nro) {
		this.nro = nro;
		this.partidos = new ArrayList<>();
	}
	
	public void agregarPartido(Partido p) {
		partidos.add(p);
	}
	
	public ArrayList<Partido> getPartidos(){
		return partidos;
	}
	
	public int cantPartidos() {
		int cantidad = 0;
		if(this.partidos != null) {
			cantidad = this.partidos.size();		
		}
		return cantidad;
	}
	
	
	public Partido buscarPartido(String e1, String e2) {
    	Partido parti = null;	
    	
    	for(int i = 0; i < partidos.size(); i++) {
			if ((partidos.get(i).equipoParticipa(e1)) && (partidos.get(i).equipoParticipa(e2))){		
	    		parti = partidos.get(i);
			}
		}
    	
    	return parti;
	}
	
	public int getNro() {
		return this.nro;
	}
	
	public int puntos(Partido parti, ResultadoEnum resul) {
		int puntos = 0;

		return puntos;
	}

	@Override
	public String toString() {
		return "Ronda [nro=" + nro + ", partidos=" + partidos + "]";
	}
}


