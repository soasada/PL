import java.io.PrintStream;

public class Generador {

	private static int varTemp = 0;
	private static int tagCont = 0;
	protected static PrintStream out = System.out;

	private static String getTemp(){
		return "t" + varTemp++;
	}

	private static String getTag(){
		return "L" + tagCont++;
	}

	public static void print(String exp){
		out.println("	print " + exp + ";");
	}
	
	public static String assignment(String ident, String exp){
		out.println("	" + ident + " = " + exp + ";");
		return ident;
	}

	public static String arithmetic(String op){
		String tmp = getTemp();
		out.println("	" + tmp + " = " + op + ";");
		return tmp;
	}

}
