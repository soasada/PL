import java.io.PrintStream;
import java.util.regex.Pattern;

public class Generador {

	protected static PrintStream out = System.out;
	
	private static int tagCont = 0;

	public static String getTag(){
		return "L" + tagCont++;
	}
	

	public static void funcion(String f){
		out.println(".method public static " + f + " (I)I");
	}

	public static void sipush(String valor){
		Var.push(valor);
		out.println("	sipush " + valor);
	}

	public static void iload(String n){
		int pos = new Integer(n);
		Var.push(Var.getVar(pos).getValor());
		out.println("	iload " + n);
	}

	public static void istore(String n){
		int pos = new Integer(n);
		
		Var.modificar(pos, new Variable(Var.getNombre(pos), Var.pop(), 1));
		out.println("	istore " + n);
	}

	public static String iadd(){

		String x = Var.pop();
        String y = Var.pop();

        int res = new Integer(x) + new Integer(y);

        Var.push(Integer.toString(res));
		out.println("	iadd");

		return Integer.toString(res);
	}
	
	public static String isub(){
        String x = Var.pop();
        String y = Var.pop();

        int res = new Integer(x) - new Integer(y);

        Var.push(Integer.toString(res));
		out.println("	isub");
		return Integer.toString(res);
	}

	public static String imul(){
		String x = Var.pop();
		String y = Var.pop();

		int res = new Integer(x) * new Integer(y);

		Var.push(Integer.toString(res));
		out.println("	imul");
		return Integer.toString(res);
	}

	public static void idiv(){
        String x = Var.pop();
        String y = Var.pop();

        int res = new Integer(x) / new Integer(y);

        Var.push(Integer.toString(res));
		out.println("	idiv");
	}

	public static void pop(){
		Var.pop();
		out.println("	pop");
	}

	public static void dup(){
		Var.push(Var.peek());
		out.println("	dup");
	}

	public static void nop(){
		out.println("	nop");
	}

	public static void label(String label){
		out.println(label + ":");
	}

	public static void goTo(String label){
		out.println("	goto " + label);
	}

	public static void ifeq(String l){
		out.println("	ifeq " + l);
	}

	public static void ifne(String l){
		out.println("	ifne " + l);
	}

	public static void ifge(String l){
		out.println("	ifge " + l);
	}

	public static void ifle(String l){
		out.println("	ifle " + l);
	}

	public static void invoke(String f){
		out.println("	invokestatic JPL/" + f + "(I)I");
	}

	public static void ireturn(){
		out.println("	ireturn");
	}

	public static void limitStack(){
		out.println("	.limit stack " + Var.getStackTam());
	}

	public static void limitLocal(){
		out.println("	.limit locals " + Var.getVarTam());
	}

	public static void endMethod(){
		out.println(".end method");
	}

	public static boolean isInteger(String in){

		Pattern p = Pattern.compile("[+-]?(0|[1-9][0-9]*)");

		return Pattern.matches(p.pattern(), in);
	}

	public static boolean isIdent(String in){

		Pattern p = Pattern.compile("[_a-zA-Z][_a-zA-Z0-9]*");

		return Pattern.matches(p.pattern(), in);
	}
}
