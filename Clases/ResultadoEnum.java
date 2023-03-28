package trabajoIntegrador;

public enum ResultadoEnum {
	ganador(5), perdedor(0), empate(1), error(0);
	
	
	public int valor;
	
	ResultadoEnum(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	
}
