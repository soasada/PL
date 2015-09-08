import java.io.PrintStream;
import java.util.regex.Pattern;

public class Generador{

	protected static PrintStream out = System.out;
	private static int contTemp = 0;
	private static int contLabel = 0;

	public static String getTemp(int tipo){
		String temp = "$t" + contTemp++;
		SymbolTable.addVar(temp, new Variable("0", tipo, SymbolTable.getLevel()));
		return temp;
	}

	public static String getLabel(){
		return "L" + contLabel++;
	}

	public static void print(String exp){
		out.println("	print " + exp + ";");
	}

	public static void error(){
		out.println("	error;\n	halt;");
	}

	public static void goTo(String label){
		out.println("	goto " + label + ";");
	}

	public static void label(String label){
		out.println(label + ":");
	}

	public static void assignment(String var, String exp){
		if(exp == null){
		}
		else{
			int tipo = check(var);
			int tipo2 = check(exp);
			
			System.out.println("TIPO: " + tipo + var + " TIPO2: " + tipo2 + exp);

			if(tipo == tipo2){
				out.println("   " + var + " = " + exp + ";");
			}
			else if(tipo == 2 && tipo2 == 1){
				out.println("	" + var + " = (float)" + exp + ";");
			}
			else if(tipo == 1 && tipo2 == 2){
				error();
			}
			else if(tipo2 == -1){
				out.println("	" + var + " = " + exp + ";");
			}
		}
	}

	public static void assignment(String var, String exp, String index){
		if(exp == null){
		}
		else{
			int tipo = check(var);
            int tipo2 = check(exp);

			dimension(var, index);

            System.out.println("TIPO: " + tipo + var + " TIPO2: " + tipo2 + exp);

            if(tipo == tipo2){
                out.println("   " + var + "[" + index + "]" + " = " + exp + ";");
            }
            else if(tipo == 2 && tipo2 == 1){
                out.println("   " + var + "[" + index + "]" + " = (float)" + exp + ";");
            }
            else if(tipo == 1 && tipo2 == 2){
                error();
            }
		}
	}

	public static String suma(String e1, String e2){
		int tipoTemp = 0;

		System.out.println("EXP1: " + e1 + " EXP2: " + e2);

		int tipo = check(e1);
		int tipo2 = check(e2);
		String temp = "";
		String temp2 = "";

		if(tipo == tipo2){
			tipoTemp = tipo;
			temp = getTemp(tipoTemp);
		}
		else if(tipo == 1 && tipo2 == 2){
			tipoTemp = 2;
			temp = getTemp(tipoTemp);
			temp2 = getTemp(tipoTemp);
			out.println("	" + temp + " = (float)" + e1 + ";");
			e1 = temp;
			temp = temp2;
		}

		String r = "";

		if(tipoTemp == 2){
			r = "r";
		}
		out.println("	" + temp + " = " + e1 + " +" + r + " " + e2 + ";");
		
		return temp;
	}

    public static String resta(String e1, String e2){
        int tipoTemp = 0;
        int tipo = check(e1);
        int tipo2 = check(e2);
        String temp = "";
        String temp2 = "";

        if(tipo == tipo2){
            tipoTemp = tipo;
            temp = getTemp(tipoTemp);
        }
        else if(tipo == 1 && tipo2 == 2){
            tipoTemp = 2;
            temp = getTemp(tipoTemp);
            temp2 = getTemp(tipoTemp);
            out.println("   " + temp + " = (float)" + e1 + ";");
            e1 = temp;
            temp = temp2;
        }

        String r = "";

        if(tipoTemp == 2){
            r = "r";
        }
        out.println("   " + temp + " = " + e1 + " -" + r + " " + e2 + ";");

        return temp;
	}

    public static String mul(String e1, String e2){
        int tipoTemp = 0;
        int tipo = check(e1);
        int tipo2 = check(e2);
        String temp = "";
        String temp2 = "";

        if(tipo == tipo2){
            tipoTemp = tipo;
            temp = getTemp(tipoTemp);
        }
        else if(tipo == 1 && tipo2 == 2){
            tipoTemp = 2;
            temp = getTemp(tipoTemp);
            temp2 = getTemp(tipoTemp);
            out.println("   " + temp + " = (float)" + e1 + ";");
            e1 = temp;
            temp = temp2;
        }

        String r = "";

        if(tipoTemp == 2){
            r = "r";
        }
        out.println("   " + temp + " = " + e1 + " *" + r + " " + e2 + ";");

        return temp;
	}

	public static void dimension(String ident, String index){
		if(isArray(ident) && isArray(index)){
			boolean flag = true;
			String aux = "";
			for(int i = 0; i < ident.length() && flag; i++){
				if(ident.charAt(i) == '['){
					flag = false;
				}
				else{
					aux += ident.charAt(i);
				}
			}
			ident = aux;

			aux = "";
			flag = true;

			for(int j = 0; j < index.length() && flag; j++){
				if(index.charAt(j) == '['){
					aux += index.charAt(j+1) + "";
					flag = false;
				}
			}

			index = aux;
		}

		if(isTemp(ident)){
			return;
		}

		if(!SymbolTable.getLastVar(ident).isArray()){
			return;
		}

		Tag tag = new Tag(getLabel(), getLabel());
		int size = SymbolTable.getLastVar(ident).getTam();

		out.println("# Comprobacion de rango");
		out.println("   if (" + index + " < 0) goto " + tag.getV() + ";");
		out.println("   if (" + size + " < " + index + ") goto " + tag.getV() + ";");
		out.println("   if (" + size + " == " + index + ") goto " + tag.getV() + ";");
		goTo(tag.getF());
		label(tag.getV());
		error();
		label(tag.getF());
	}

    public static String div(String e1, String e2){
        int tipoTemp = 0;
        int tipo = check(e1);
        int tipo2 = check(e2);
        String temp = "";
        String temp2 = "";

        if(tipo == tipo2){
            tipoTemp = tipo;
            temp = getTemp(tipoTemp);
        }
        else if(tipo == 1 && tipo2 == 2){
            tipoTemp = 2;
            temp = getTemp(tipoTemp);
            temp2 = getTemp(tipoTemp);
            out.println("   " + temp + " = (float)" + e1 + ";");
            e1 = temp;
            temp = temp2;
        }

        String r = "";

        if(tipoTemp == 2){
            r = "r";
        }
        out.println("   " + temp + " = " + e1 + " /" + r + " " + e2 + ";");

        return temp;
	}

    public static String mod(String e1, String e2){
        int tipoTemp = 0;
        int tipo = check(e1);
        int tipo2 = check(e2);

        if(tipo == tipo2){
            tipoTemp = tipo;
        }

        String temp = getTemp(tipoTemp);

        String r = "";

        if(tipoTemp == 2){
            r = "r";
        }

        out.println("   " + temp + " = " + e1 + " %" + r + " " + e2 + ";");

        return temp;
    }

	public static String casting(String exp, int tipo){
		String temp = getTemp(tipo);

		assignment(temp, exp);
		
		return temp;
	}

	public static Tag condition(String arg1, int cond, String arg2){
		String tagV = getLabel();
		String tagF = getLabel();

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

	public static boolean isZero(String in){
		Pattern p = Pattern.compile("[0]*\\.?[0]*");

		return Pattern.matches(p.pattern(), in);
	}

	private static int check(String raw){
		if(isInteger(raw)){
			return 1;
		}
		if(isReal(raw)){
			return 2;
		}
		if(isIdent(raw)){
			return SymbolTable.getLastVar(raw).getTipo();
		}
		if(isTemp(raw)){
			return SymbolTable.getLastVar(raw).getTipo();
		}
		return -1;
	}
}
