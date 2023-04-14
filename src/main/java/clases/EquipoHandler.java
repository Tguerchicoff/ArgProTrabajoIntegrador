package clases;

import java.util.ArrayList;

public class EquipoHandler {

	public static void agregarEquipo(String nombre, ArrayList<Equipo> equipos) {
		if(!Buscador.buscarEquipo(nombre, equipos)) {
			Equipo equi = new Equipo(nombre, "A confirmar");
			equipos.add(equi);
		}
	}
	
	public static Equipo darEquipo(String nombre, ArrayList<Equipo> equipos) {
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
