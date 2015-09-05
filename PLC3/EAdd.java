public class EAdd extends Exp{

	public final Exp exp1, exp2;
	
	public EAdd(Exp e1, Exp e2){
		exp1 = e1;
		exp2 = e2;
	}

	public Object generate(){
		String tmp = Generator.getInstance().newTmp();
		Generator.getInstance().out().println(tmp + " = " + exp1.generate() + " + " + exp2.generate() + ";");
		return tmp;
	}
}
