package clases;

import java.util.ArrayList;

public class PersonaHandler {
	
	
	public static void agregarPersona(String nombre, ArrayList<Persona> personas) {
		if(Buscador.buscarPersona(nombre, personas) == null) {
			Persona p = new Persona(nombre);
			personas.add(p);
		}
	}
	
	public static void cargarPronosticoAPersona(String nombre, Pronostico p, ArrayList<Persona> personas) {
		Persona per = Buscador.buscarPersona(nombre, personas);
		per.agregarPronostico(p);
		
	}
}
