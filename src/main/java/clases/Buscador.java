package clases;

import java.util.ArrayList;

public class Buscador {

	
	public static Partido buscarPartido(String e1, String e2, ArrayList<Ronda> fase) {
        int i = 0;
		Partido parti = null;
        while(i < fase.size() && parti == null) {
        	Ronda rondaBuscando = fase.get(i);
        	parti = rondaBuscando.buscarPartido(e1, e2);
        	i++;
        }
        
       return parti;
	}
	
	
	public static Ronda buscarRonda(int numRonda, ArrayList<Ronda> fase) {
	    Ronda ronda = null;
	    int i = 0;
	    while(i<fase.size() && ronda == null) {
	    	if(fase.get(i).getNro() == numRonda) {
	    		ronda = fase.remove(i);
	    		break;
	    	}else {
	    		i++;
	    	}    	
	    }	    
	    if (ronda == null) {
	        ronda = new Ronda(numRonda);
	    }
	    return ronda;
	}
	
	public static Persona buscarPersona(String nombre, ArrayList<Persona> personas) {
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
	
	public static boolean buscarEquipo(String nombre, ArrayList<Equipo> equipos) {
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
	
	
}
