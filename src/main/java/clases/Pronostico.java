package clases;


public class Pronostico {
	private Partido partido;
	private Equipo equipo;
	private ResultadoEnum resultado;
	
	
	
	
	public Pronostico(Partido partido, Equipo equipo, ResultadoEnum resultado) {
		this.partido = partido;
		this.equipo = equipo;
		this.resultado = resultado;
	}
	
	public Pronostico(Partido partido, Equipo equipo, String resultado) {
		this.partido = partido;
		this.equipo = equipo;
		this.resultado = ResultadoEnum.valueOf(resultado);
	}
	
	public int puntos() {
		int puntos = 0;
		if(partido.resultado(equipo) == resultado) {
			puntos = 5;
		}
		return puntos;
	}
	
	public boolean exito() {
		boolean exito = false;
		if(partido.resultado(equipo) == resultado) {
			exito = true;
		}
		return exito;
	}
	
	
	@Override
	public String toString() {
		return "Pronostico [partido=" + partido + ", equipo=" + equipo + ", resultado=" + resultado + "]";
	}
	

}
