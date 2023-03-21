package trabajoIntegrador;

public class Partido {
	private Equipo e1;
	private Equipo e2;
	private int golesE1;
	private int golesE2;
	
	public Partido(Equipo e1, Equipo e2, int g1, int g2) {
		this.e1 = e1;
		this.e2 = e2;
		this.golesE1 = g1;
		this.golesE2 = g2;
	}
	
	public Partido(String golesE1, Equipo e1, String golesE2, Equipo e2) {
		this.golesE1 = Integer.parseInt(golesE1);
		this.golesE2 = Integer.parseInt(golesE2);
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public boolean equipoParticipa(String nombre) {
		return (e1.getNombre().equals(nombre)|| e2.getNombre().equals(nombre));
	}
	
	
	public ResultadoEnum resultado(Equipo e) {
		ResultadoEnum resul  = ResultadoEnum.empate;
		if(e.getNombre() == e1.getNombre()) {
			if(golesE1 > golesE2) {
				resul = ResultadoEnum.ganador;
			} 
			if(golesE1 < golesE2) {
				resul = ResultadoEnum.perdedor;
			}
		}
		
		if(e.getNombre() == e2.getNombre()) {
			if(golesE2 > golesE1) {
				resul = ResultadoEnum.ganador;
			} 
			if(golesE2 < golesE1) {
				resul = ResultadoEnum.perdedor;
			}
		}
		
		if(e.getNombre() != e1.getNombre() && e.getNombre() != e2.getNombre()) {
			resul = ResultadoEnum.error;
		}
		
		return resul;
	}

	
	@Override
	public String toString() {
		return "Partido [e1=" + e1 + ", e2=" + e2 + ", golesE1=" + golesE1 + ", golesE2=" + golesE2 + "]";
	}
	
	
	
	
	
	
}
