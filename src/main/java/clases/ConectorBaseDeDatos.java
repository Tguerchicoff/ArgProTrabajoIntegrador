package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConectorBaseDeDatos {
    
	static Connection conexion = null;
    static ResultSet resultado = null;
	static Statement consulta = null;

	public static ResultSet conectarBd(String host1, String puerto1, String nombreBD1, String usuario1, String contrasena1, ArrayList<Ronda> fase, ArrayList<Persona> personas, ArrayList<Equipo> equipos) {
		  try {
			    String host = host1;
			    String puerto = puerto1;
			    String nombreBD = nombreBD1;
			    String usuario =  usuario1;
			    String contrasena = contrasena1;

			    // CONEXION A LA BASE
			    String url = "jdbc:mysql://" + host + ":" + puerto + "/" + nombreBD;
			    Connection conexion = DriverManager.getConnection(url, usuario, contrasena);

			    System.out.println("Conexión exitosa a la base de datos " + nombreBD + "\n");
			    

			    // CONSULTA DE PRONOSTICOS
			    Statement consulta = conexion.createStatement();
			    ResultSet resultado = consulta.executeQuery("SELECT * FROM pronosticos");

			    while (resultado.next()) {
			    	String equipo1 = resultado.getString("nombreEquipo1");
			        String resultadoEquipo = resultado.getString("resultadoEquipo");
			        String equipo2 = resultado.getString("nombreEquipo2");
			        String persona = resultado.getString("nombrePersona");

			        Partido partido = Buscador.buscarPartido(equipo1, equipo2, fase);

			        if (partido != null) {
			        	Equipo eq1 = EquipoHandler.darEquipo(equipo1, equipos);
			        	Pronostico pronostico = new Pronostico(partido, eq1, resultadoEquipo);

			        	PersonaHandler.agregarPersona(persona, personas);
			        	PersonaHandler.cargarPronosticoAPersona(persona, pronostico, personas);
			        } else {
			        	System.out.println("El partido entre " + equipo1 + " y " + equipo2 + " no existe.");
			        }
			    }

			    resultado.close();
			    consulta.close();
			    conexion.close();
			    
			    
			  } catch (SQLException e) {
			    System.out.println("Error al conectarse a la base de datos: " + e.getMessage());
			  }
		  	return resultado;
			}


	
	
}
