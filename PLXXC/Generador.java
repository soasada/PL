import java.io.PrintStream;
import java.util.regex.Pattern;

public class Generador {

	public static final int MAS = 5;
	public static final int MENOS = 6;
	public static final int MULT = 7;
	public static final int DIV = 8;

	private static int varTemp = 0;
	private static int tagCont = 0;
	protected static PrintStream out = System.out;

	public static String getTemp(int tipo){
		String salida = "$t" + varTemp++;
		
		Variable var = new Variable(Var.getLevel(), tipo);
		Var.addVar(salida, var);
		
		return salida;
	}

	public static String getTag(){
		return "L" + tagCont++;
	}

	public static void print(String exp){
		out.println("	print " + exp + ";");
	}

	public static String casting(String exp, int tipo){
		String tmp = "";

		// System.out.println("exp: " + exp + " tipo: " + tipo);

		if(tipo == Variable.INTEGER){
			tmp = getTemp(Variable.INTEGER);
			out.println("	" + tmp + " = (int) " + exp + ";");
		}
		else if(tipo == Variable.REAL){
			tmp = getTemp(Variable.REAL);
			out.println("	" + tmp + " = (float) " + exp + ";");
		}
		return tmp;
	}
	
	public static String assignment(String ident, String exp){	
		int tipo = 0;
		int tipo2 = 0;
		boolean flag = false;

		// System.out.println("IDENT: " + ident + "\n" + " EXP: " + exp);

		tipo = checkExp(exp);
		tipo2 = Var.getListVar(ident).get(Var.getListVar(ident).size()-1).getTipo();
		
		if(tipo == 60 || tipo == 70){
			tipo = Var.getListVar(exp).get(Var.getListVar(exp).size()-1).getTipo();
			flag = true;
		}

		if((tipo == 1 && tipo2 == 2) && flag){
			out.println("	" + ident + " = (float)" + exp + ";");
		}
		else if((tipo2 == 1 && tipo == 2) || (tipo2 == 2 && tipo == 1) && !flag){
			Generador.error();
			Generador.halt();
		}
		else{
			out.println("	" + ident + " = " + exp + ";");
		}
		
		return ident;
	}

	public static String assignment(String ident, String index, String exp){
		int tipo = 0;
                int tipo2 = 0;
                boolean flag = false;

                // System.out.println("IDENT: " + ident + "\n" + "INDEX: " + index + "\n" +  " EXP: " + exp);

		dimension(ident, index);

                tipo = checkExp(exp);
                tipo2 = Var.getListVar(ident).get(Var.getListVar(ident).size()-1).getTipo();

                if(tipo == 60 || tipo == 70){
                        tipo = Var.getListVar(exp).get(Var.getListVar(exp).size()-1).getTipo();
                        flag = true;
                }

                if((tipo == 1 && tipo2 == 2) && flag){
                        out.println("   " + ident + " = (float)" + exp + ";");
                }
                else if((tipo2 == 1 && tipo == 2) || (tipo2 == 2 && tipo == 1) && !flag){
                        Generador.error();
                        Generador.halt();
                }
                else{
			String aux = ident + "[" + index + "]";
                        out.println("   " + aux +  " = " + exp + ";");
                }

                return ident;
	}

	public static String arithmetic(String arg1, String arg2, int op){
		int tipo = 0;
		int tipo2 = 0;

		tipo = checkExp(arg1);
		tipo2 = checkExp(arg2);

		// System.out.println("tipo: " + tipo + " " + arg1 + "\ntipo2: " + tipo2 + " " + arg2);

		if(tipo == 60 || tipo == 70){
			tipo = Var.getListVar(arg1).get(Var.getListVar(arg1).size()-1).getTipo();
		}

		if(tipo2 == 60 || tipo2 == 70){
			tipo2 = Var.getListVar(arg2).get(Var.getListVar(arg2).size()-1).getTipo();
		}

		// System.out.println("tipo: " + tipo + " " + arg1 + "\ntipo2: " + tipo2 + " " + arg2);

		if((tipo == 1 && tipo2 == 2) || (tipo == 2 && tipo2 == 1)){
			if(tipo == 1){
				String tmp2 = getTemp(tipo2);
				assignment(tmp2, arg1);
				arg1 = tmp2;
			}
			else{
				String tmp2 = getTemp(tipo);
				assignment(tmp2, arg2);
				arg2 = tmp2;
			}
		}

		String tmp = getTemp(tipo);

		if(tipo == 2 || tipo2 == 2){

			switch(op){
				case MAS:
					out.println("   " + tmp + " = " + arg1 + " +r " + arg2 + ";");
					break;
				case MENOS:
					out.println("	" + tmp + " = " + arg1 + " -r " + arg2 + ";");
					break;
				case MULT:
					out.println("	" + tmp + " = " + arg1 + " *r " + arg2 + ";");
					break;
				case DIV:
					out.println("	" + tmp + " = " + arg1 + " /r " + arg2 + ";");
					break;
				default:
					out.println("	" + tmp + " = " + arg1 + ";");
			}
		}
		else{
			switch(op){
                                case MAS:
                                        out.println("   " + tmp + " = " + arg1 + " + " + arg2 + ";");
                                        break;
                                case MENOS:
                                        out.println("   " + tmp + " = " + arg1 + " - " + arg2 + ";");
                                        break;
                                case MULT:
                                        out.println("   " + tmp + " = " + arg1 + " * " + arg2 + ";");
                                        break;
                                case DIV:
                                        out.println("   " + tmp + " = " + arg1 + " / " + arg2 + ";");
                                        break;
				default:
					out.println("	" + tmp + " = " + arg1 + ";");
                        }
		}
		return tmp;
	}

	public static void dimension(String ident, String index){
		if(isTemp(ident)){
			return;
		}

		if(!Var.getListVar(ident).get(Var.getListVar(ident).size()-1).isArray()){
			return;
		}

		Tag tag = new Tag(getTag(), getTag());
		int size = Var.getListVar(ident).get(Var.getListVar(ident).size()-1).getTam();

		out.println("# Comprobacion de rango");
		out.println("   if (" + index + " < 0) goto " + tag.getV() + ";");
		out.println("   if (" + size + " < " + index + ") goto " + tag.getV() + ";");
		out.println("   if (" + size + " == " + index + ") goto " + tag.getV() + ";");
		goTo(tag.getF());
		label(tag.getV());
		error();
		halt();
		label(tag.getF());
	}

	public static void label(String tag){
		out.println(tag + ":");
	}
	
	public static void goTo(String tag){
		out.println("	goto " + tag + ";");
	}

	public static void error(){
		out.println("	error;");
	}
	public static void halt(){
		out.println("	halt;");
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

	public static boolean isReal(String in){

		Pattern p = Pattern.compile("[-]?[0-9]*\\.[0-9]+[eE]*[+-]?[0-9]*");
		
		return Pattern.matches(p.pattern(), in);
	}

	public static boolean isInteger(String in){

		Pattern p = Pattern.compile("0|[1-9][0-9]*");

		return Pattern.matches(p.pattern(), in);
	}

	public static boolean isIdent(String in){

		Pattern p = Pattern.compile("[_a-zA-Z][_a-zA-Z0-9]*");

		return Pattern.matches(p.pattern(), in);
	}

	public static boolean isTemp(String in){
		Pattern p = Pattern.compile("[$][a-zA-Z][_a-zA-Z0-9]*");

		return Pattern.matches(p.pattern(), in);
	}

	public static boolean isArray(String in){

		Pattern p = Pattern.compile("[_a-zA-Z]+(?:\\[[_a-zA-Z0-9]*\\])+");
	 
		return Pattern.matches(p.pattern(), in);
	}

	public static int checkExp(String in){
		if(isReal(in)){
			return 2;
		}
		else if(isInteger(in)){
			return 1;
		}
		else if(isArray(in)){
			return 3;
		}
		else if(isIdent(in)){
			return 60;
		}
		else if(isTemp(in)){
			return 70;
		}
		else{
			return 0;
		}
	}
}
