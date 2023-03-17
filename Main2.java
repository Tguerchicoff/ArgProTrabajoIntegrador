package trabajoIntegrador;

public class Main2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Programa2 pr2 = new Programa2();
		
		
		Equipo Argentina = new Equipo("Argentina","Seleccion");
		Equipo Arabia = new Equipo("Arabia","Seleccion");
		Equipo Polonia = new Equipo("Polonia","Seleccion");
		Equipo Mexico = new Equipo("Mexico","Seleccion");
		
		pr2.agregarEquipo(Argentina);
		pr2.agregarEquipo(Arabia);
		pr2.agregarEquipo(Polonia);
		pr2.agregarEquipo(Mexico);
		
		pr2.mostrarEquipos();
		pr2.solicitarPaths();
		
		pr2.jugar();
	}

}
