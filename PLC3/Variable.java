public class Variable {

	private String valor;
	private int tipo;

	public Variable(String valor, int tipo){
		this.valor = valor;
		this.tipo = tipo;
	}

	public String getValor(){
		return this.valor;
	}

	public int getTipo(){
		return this.tipo;
	}

	public void setValor(String c){
		this.valor = c;
	}

	public void setTipo(int c){
		this.tipo = c;
	}
}
