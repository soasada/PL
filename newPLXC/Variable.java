public class Variable{
	
	private String valor;
	private int tipo;
	private int nivel;
	private boolean isPointer;
	private boolean isArray;
	private int tam;

	public Variable(String valor, int tipo, int nivel){
		this.valor = valor;
		this.tipo = tipo;
		this.nivel = nivel;
		this.isPointer = false;
		this.isArray = false;
		this.tam = 0;
	}

	public String getValor(){
		return this.valor;
	}

	public int getTipo(){
		return this.tipo;
	}

	public int getNivel(){
		return this.nivel;
	}

	public boolean isPointer(){
		return this.isPointer;
	}

	public boolean isArray(){
		return this.isArray;
	}

	public int getTam(){
		return this.tam;
	}

	public void setValor(String valor){
        if(valor == null){
            this.valor = "0";
			return;
        }

		if(Generador.isIdent(valor) || Generador.isTemp(valor)){
			valor = SymbolTable.getLastVar(valor).getValor();
		}
		else{
			this.valor = valor;
		}
	}

	public void setTipo(int tipo){
		this.tipo = tipo;
	}

	public void setNivel(int nivel){
		this.nivel = nivel;
	}

	public void setIsPointer(boolean flag){
		this.isPointer = flag;
	}

	public void setIsArray(boolean flag){
		this.isArray = flag;
	}

	public void setTam(int tam){
		this.tam = tam;
	}

	@Override
	public String toString(){
		return "(" + "valor: " + valor + ", tipo: " + tipo + ", isArray: " + isArray + ")";
	}
}
