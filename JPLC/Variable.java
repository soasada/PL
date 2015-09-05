public class Variable{

	private String nombre;
	private String valor;
	private int tipo;

	public Variable(String nombre, String valor,int tipo){
		this.nombre = nombre;
		this.valor = valor;
		this.tipo = tipo;
	}

	public String getNombre(){
		return this.nombre;
	}

	public int getTipo(){
		return this.tipo;
	}

	public void setNombre(String nuevo){
		this.nombre = nuevo;
	}

	public void setTipo(int n){
		this.tipo = n;
	}

	public String getValor(){
		return this.valor;
	}

	public void setValor(String valor){
		this.valor = valor;
	}
}
