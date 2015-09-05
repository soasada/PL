public class DInt extends Dec{

	public final String name;
	public final Exp exp1;
	
	public DInt(String name){
		this.name = name;
		exp1 = null;
	}

	public DInt(String name, Exp e1){
		this.name = name;
		exp1 = e1;
	}

	public Object generate(){
		return null;
	}
}
