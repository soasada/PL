import java.io.PrintStream;

public class Generador {

	private static int varTemp = 0;
	private static int tagCont = 0;
	protected static PrintStream out = System.out;

	private static String getTemp(){
		return "t" + varTemp++;
	}

	public static String getTag(){
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

	public static void label(String tag){
		out.println(tag + ":");
	}
	
	public static void goTo(String tag){
		out.println("	goto " + tag + ";");
	}

	public static Tag condition(String arg1, int cond, String arg2){
		String tagV = getTag();
		String tagF = getTag();

		switch(cond){
			case Tag.MAQ:
				out.println("	if (" + arg2 + " < " + arg1 + ") goto " + tagV + ";");
				out.println("	goto " + tagF + ";");
				break;
			case Tag.MEQ:
				out.println("   if (" + arg1 + " < " + arg2 + ") goto " + tagV + ";");
                                out.println("   goto " + tagF + ";");
                                break;
			case Tag.EQ:
				out.println("   if (" + arg1 + " == " + arg2 + ") goto " + tagV + ";");
                                out.println("   goto " + tagF + ";");
                                break;
			case Tag.MEOQ:
				out.println("   if (" + arg2 + " < " + arg1 + ") goto " + tagF + ";");
                                out.println("   goto " + tagV + ";");
                                break;
			case Tag.MAOQ:
				out.println("   if (" + arg1 + " < " + arg2 + ") goto " + tagF + ";");
                                out.println("   goto " + tagV + ";");
                                break;
			case Tag.NEQ:
				out.println("   if (" + arg1 + " == " + arg2 + ") goto " + tagF + ";");
                                out.println("   goto " + tagV + ";");
                                break;
		}

		return new Tag(tagV, tagF);
	}

}
