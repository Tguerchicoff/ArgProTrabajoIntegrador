package clases;

public class Verificador {

	private static boolean verificarLargo(String[] array, int largo) {
		boolean resultado = true;
		if(array.length != largo) {
			resultado = false;
		}		
		return resultado;
	}
	
	public static boolean verificarArray(String[] array, int largo) {
		boolean verificado = verificarLargo(array, largo);
		if(verificado) {
			for (String str : array) {
				if(str == null || str.length() == 0) {
					verificado = false;
				}
			}
		}
	return verificado;
	}

	public static boolean esInt(String cadena) {
		//Recibe string xq levanta la cadena del archivo
	    boolean es = true;
		try {
	        Integer.parseInt(cadena);    
	    } catch (NumberFormatException e1) {
	    	es =  false;
	    }
	return es;
	}
	
}
