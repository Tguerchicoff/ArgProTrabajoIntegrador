package programaDeportivo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import clases.Programa;

public class Test {

	@org.junit.Test
	public void archivosCorrectos() {
		System.out.println("Test OK");
		String input = 	"D:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\main\\resources\\resultado.txt\nD:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\main\\resources\\conexionYPuntos.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
		pr.inicializar();
		System.out.println("-------------");
	}
	
	@org.junit.Test
	public void dosArchivosVacios() {
		System.out.println("Test archivos vacios");
		String input = 	"D:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\vacio.txt\nD:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\vacio.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();

	    pr.inicializar();
	    System.out.println("-------------");
	}
	
	@org.junit.Test
	public void primerArchivoVacios() {
		System.out.println("Test primer archivo vacio");
		String input = 	"D:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\vacio.txt\nD:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\conexionYPuntos.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
		pr.inicializar(); 
	    System.out.println("-------------");
	}
	
	@org.junit.Test
	public void segundoArchivoVacio() {
		System.out.println("Test segundo archivo vacio");
		String input = 	"D:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\resultado.txt\nD:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\vacio.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
	    pr.inicializar();  
	    System.out.println("-------------");
	}
	@org.junit.Test
	public void emptyParams() {
		System.out.println("Test parametros vacios");
		String input = 	" \n ";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
	    pr.inicializar();  
	    System.out.println("-------------");
	}
	
	@org.junit.Test
	public void emptyParam1() {
		System.out.println("Test parametro 1 vacio");
		String input = 	" \nD:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\vacio.txt";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
	    pr.inicializar();  
	    System.out.println("-------------");
	}
	
	@org.junit.Test
	public void emptyParam2() {
		System.out.println("Test parametro 2 vacio");
		String input = 	"D:\\Eclipse\\eclipse-workspace\\programaDeportivo\\src\\test\\resources\\resultado.txt\nhola ";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		Programa pr = new Programa();
	    pr.inicializar();  
	    System.out.println("-------------");
	}
}



