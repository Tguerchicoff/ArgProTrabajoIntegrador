package programaDeportivo;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import clases.Programa;

public class Test {

	@org.junit.Test
	public void archivosCorrectos() {
		String input = 	"D:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\main\\resources\\resultado.txt\nD:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\main\\resources\\conexionYPuntos.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
		pr.inicializar();
	}
	
	@org.junit.Test
	public void dosArchivosVacios() {
		String input = 	"D:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\vacio.txt\nD:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\vacio.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
	    try {
	        pr.inicializar();  
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
	}
	
	@org.junit.Test
	public void primerArchivoVacios() {
		String input = 	"D:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\vacio.txt\nD:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\conexionYPuntos.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
	    try {
	        pr.inicializar();  
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
	}
	
	@org.junit.Test
	public void segundoArchivoVacio() {
		String input = 	"D:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\resultado.txt\nD:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\vacio.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
	    try {
	        pr.inicializar();  
	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
	}
}



