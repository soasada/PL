package absyn;

public class ESub extends Exp{

    public final Exp exp1, exp2;

    public EAdd(Exp e1, Exp e2){
        exp1 = e1;
        exp2 = e2;
    }

    public Object generate(){
        String tmp = Generador.getInstance().newTmp();
        Generador.getInstance().out().println(tmp + " = " + exp1.generate() + " - " + exp2.generate());
    }
}
