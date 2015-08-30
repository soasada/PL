public class Variable{

	public static final int INTEGER = 1;
	public static final int REAL = 2;
	public static final int ARRAY = 3;
	public static final int PUNTERO = 4;
	public static final int CHAR = 5;
	

	private int nivel;
	private int tipo;
	private boolean isArray;
	private int tam;

	public Variable(int nivel, int tipo){
		this.nivel = nivel;
		this.tipo = tipo;
		this.isArray = false;
		this.tam = 0;
	}

	public int getNivel(){
		return this.nivel;
	}
	
	public int getTipo(){
		return this.tipo;
	}
	
	public void setNivel(int nivel){
		this.nivel = nivel;
	}
	
	public void setTipo(int tipo){
		this.tipo = tipo;
	}

	public boolean isArray(){
		return this.isArray;
	}

	public void setIsArray(boolean flag){
		this.isArray = flag;
	}

	public int getTam(){
		return this.tam;
	}

	public void setTam(int tam){
		this.tam = tam;
	}
}
