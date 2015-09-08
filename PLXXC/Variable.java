import java.util.ArrayList;

public class Variable{

	public static final int INTEGER = 1;
	public static final int REAL = 2;
	public static final int ARRAY = 3;
	public static final int PUNTERO = 4;
	public static final int CHAR = 5;
	
	private int nivel;
	private int tipo;
	private boolean isArray;
	private int tam1, tam2;
	private boolean isPointer;

	public Variable(int nivel, int tipo){
		this.nivel = nivel;
		this.tipo = tipo;
		this.tam1 = 0;
		this.tam2 = 0;
		this.isArray = false;
		this.isPointer = false;
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
		return this.tam1;
	}

	public int getTam2(){
		return this.tam2;
	}

	public void setTam(ArrayList<Integer> tams){
		if(tams.size() == 1){
			this.tam1 = tams.get(0);
		}
		else{
			for(int i = 0; i < tams.size(); i++){
				if(i == 0){
					this.tam1 = tams.get(i);
				}
				if(i == 1){
					this.tam2 = tams.get(i);
				}
			}
		}
	}

	public boolean isPointer(){
		return this.isPointer;
	}

	public void setIsPointer(boolean state){
		this.isPointer = state;
	}

	@Override
	public String toString(){
		return "(" + tam1 + ", " + tam2 +  ")";
	}
}
