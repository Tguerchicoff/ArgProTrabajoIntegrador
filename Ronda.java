package trabajoIntegrador;
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
	
	public Partido buscarPartido(String e1, String e2) {
    	Partido parti = null;	
    	
    	for(int i = 0; i < partidos.size(); i++) {
			if ((partidos.get(i).equipoParticipa(e1)) && (partidos.get(i).equipoParticipa(e2))){		
	    		parti = partidos.get(i);
			}
		}
    	
    	return parti;
	}
	
	
	
	public int puntos() {
		int puntos = 0;
		
		return puntos;
	}
}
