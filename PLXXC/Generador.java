import java.io.PrintStream;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

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

				System.out.println("IDENT: " + ident + "\n" + " EXP: " + exp);

				if(Var.getLastVar(ident).isArray() && Var.getLastVar(exp).isArray()){
						arrayAssignment(ident, exp);
				}

				tipo = checkExp(exp);
				tipo2 = Var.getLastVar(ident).getTipo();

				if(tipo == 3){
						dimension(exp, exp, 0);
				}

				if(tipo == 60 || tipo == 70){
						tipo = Var.getLastVar(exp).getTipo();
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

		public static String assignmentPlus(String ident, String exp){
                int tipo = 0;
                int tipo2 = 0;
                boolean flag = false;

                System.out.println("IDENT: " + ident + "\n" + " EXP: " + exp);

                if(Var.getLastVar(ident).isArray() && Var.getLastVar(exp).isArray()){
                        arrayAssignment(ident, exp);
                }

                tipo = checkExp(exp);
                tipo2 = Var.getLastVar(ident).getTipo();

                if(tipo == 3){
                        dimension(exp, exp, 0);
                }

                if(tipo == 60 || tipo == 70){
                        tipo = Var.getLastVar(exp).getTipo();
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
                        out.println("   " + ident + " = " + ident + " + " + exp + ";");
                }

                return ident;
		}

		public static String arrayAssignment(String ident, String exp){
				int tipo = Var.getLastVar(ident).getTipo();
				int tipo2 = Var.getLastVar(exp).getTipo();

				if(tipo != tipo2){
						error();
						halt();
				}

				if(Var.getLastVar(ident).getTam() < Var.getLastVar(exp).getTam()){
						error();
						halt();
				}

				String tmp = getTemp(tipo);
				for(int i = 0; i < Var.getLastVar(ident).getTam(); i++){
						out.println("	" + tmp + " = " + exp + "[" + i + "]" + ";");
						out.println("	" + ident + "[" + i + "]" + " = " + tmp + ";");
				}
				return tmp;
		}

		public static String assignment(String ident, ArrayList<String> index, String exp){
				int tipo = 0;
				int tipo2 = 0;
				boolean flag = false;

				// System.out.println("IDENT: " + ident + "\n" + "INDEX: " + index + "\n" +  " EXP: " + exp);

				for(int i = 0; i < index.size(); i++){
					dimension(ident, index.get(i), i);

					System.out.println("HOLAAAAA: " + index.get(i));

					tipo = checkExp(exp);
					tipo2 = Var.getLastVar(ident).getTipo();

					if(tipo == 60 || tipo == 70){
							tipo = Var.getLastVar(exp).getTipo();
							flag = true;
					}

					// System.out.println("tipo: " + tipo + " tipo2: " + tipo2);

					if((tipo == 1 && tipo2 == 2) && flag){
							String tmp = getTemp(Var.getLastVar(ident).getTipo());
							out.println("	" + tmp + " = (float) " + exp + ";");
							out.println("   " + ident + "[" + index.get(i) + "]" + " = " + tmp + ";");
					}
					else if((tipo2 == 1 && tipo == 2) || (tipo2 == 2 && tipo == 1) && !flag){
							String tmp = getTemp(tipo2);
							out.println("	" + tmp + " = (float) " + exp + ";");
							out.println("   " + ident + "[" + index.get(i) + "]" + " = " + tmp + ";");
					}
					else{
							String aux = ident + "[" + index.get(i) + "]";
							out.println("   " + aux +  " = " + exp + ";");
					}

				}

				return ident;
		}

        public static String assignmentPlus(String ident, ArrayList<String> index, String exp){
                int tipo = 0;
                int tipo2 = 0;
                boolean flag = false;

                // System.out.println("IDENT: " + ident + "\n" + "INDEX: " + index + "\n" +  " EXP: " + exp);

				for(int i = 0; i < index.size(); i++){
                	dimension(ident, index.get(i), i);

                	tipo = checkExp(exp);
                	tipo2 = Var.getLastVar(ident).getTipo();

                	if(tipo == 60 || tipo == 70){
						tipo = Var.getLastVar(exp).getTipo();
                        flag = true;
                	}

                	// System.out.println("tipo: " + tipo + " tipo2: " + tipo2);

                	if((tipo == 1 && tipo2 == 2) && flag){
                    	    String tmp = getTemp(Var.getLastVar(ident).getTipo());
                       		out.println("   " + tmp + " = (float) " + exp + ";");
                    	    out.println("   " + ident + "[" + index + "]" + " = " + tmp + ";");
                	}
                	else if((tipo2 == 1 && tipo == 2) || (tipo2 == 2 && tipo == 1) && !flag){
                    	    String tmp = getTemp(tipo2);
                    	    out.println("   " + tmp + " = (float) " + exp + ";");
                    	    out.println("   " + ident + "[" + index + "]" + " = " + tmp + ";");
                	}
                	else{
                    	    String aux = ident + "[" + index + "]";
							String temp = getTemp(tipo2);
							out.println("	" + temp + " = " + aux + ";");
							out.println("	" + temp + " = " + temp + " + " + exp + ";");
                   		    out.println("   " + aux +  " = " + temp + ";");
                	}
				}

                return ident;
        }


		public static String arrayInit(String ident, List<String> exps){
				int tipo = 0;
				int index = 0;

				if(Var.getLastVar(ident).getTam() < exps.size()){
						error();
						halt();
						return "";
				}

				// System.out.println("IDENT: " + ident + "\n" + "EXP: " + exps.get(1));

				int tipo2 = checkExp(exps.get(1));

				if(tipo2 == 60 || tipo2 == 70){
						tipo2 = Var.getLastVar(exps.get(1)).getTipo();
				}

				if(Var.getLastVar(ident).getTipo() != tipo2){
						error();
						halt();
						return "";
				}

				tipo = checkExp(ident);

				if(tipo == 60 || tipo == 70){
						tipo = Var.getLastVar(ident).getTipo();
				}

				String tmp = getTemp(tipo);
				String tmp2 = getTemp(tipo);
				Var.getLastVar(tmp).setIsArray(true);

				for(String iter : exps){
						out.println("   " + tmp + "[" + index + "]" + " = " + iter + ";");
						index++;
				}

				for(int i = 0; i < index; i++){
						out.println("	" + tmp2 + " = " + tmp + "[" + i + "]" + ";");
						out.println("	" + ident + "[" + i + "]" + " = " + tmp2 + ";");
				}

				out.println("	" + ident + " = " + tmp + ";");

				return ident;
		}

		public static String arithmetic(String arg1, String arg2, int op){
				int tipo = 0;
				int tipo2 = 0;

				tipo = checkExp(arg1);
				tipo2 = checkExp(arg2);

				// System.out.println("tipo: " + tipo + " " + arg1 + "\ntipo2: " + tipo2 + " " + arg2);

				if(tipo == 60 || tipo == 70){
						tipo = Var.getLastVar(arg1).getTipo();
				}

				if(tipo2 == 60 || tipo2 == 70){
						tipo2 = Var.getLastVar(arg2).getTipo();
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
										String value = "";
										if(isIdent(arg2) || isTemp(arg2)){
											
										}
										if(isZero(arg2)){
											error();
											halt();
										}
										else{
											out.println("	" + tmp + " = " + arg1 + " /r " + arg2 + ";");
										}
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
										if(isZero(arg2)){
											error();
											halt();
										}
										else{
											out.println("   " + tmp + " = " + arg1 + " / " + arg2 + ";");
										}
										break;
								default:
										out.println("	" + tmp + " = " + arg1 + ";");
						}
				}
				return tmp;
		}

		public static Tag forIn(String e1, String tagFor, Object e2){
				String tmp = getTemp(1);
				int size = 0;
				int tipo = 0, tipo2 = 0;
				String array = "";
				String ident = (e1.indexOf('[') == -1) ? e1 : e1.substring(0, e1.indexOf('['));

				System.out.println("IDENT: " + e1 + " " + e2.toString());

				if(e2 instanceof String){

						if(!Var.exists(e2.toString())){
								error();
								halt();
								return new Tag(getTag(), getTag());
						}

						if(!Var.getLastVar(e2.toString()).isArray()){
								error();
								halt();
								return new Tag(getTag(), getTag());
						}

						array = e2.toString();
						size = Var.getLastVar(array).getTam();
						tipo = Var.getLastVar(ident).getTipo();
						tipo2 = Var.getLastVar(array).getTipo();
				}
				else{
						array = getTemp(Var.getLastVar(ident).getTipo());
						size = ((List<String>) e2).size();

						int i = 0;
						for(String iter : (List<String>) e2){
								out.println("	" + array + "[" + i + "]" + " = " + iter + ";");
								i++;
						}
				}

				if(tipo != tipo2){
						error();
						halt();
				}

				assignment(tmp, "-1");
				label(tagFor);
				assignment(tmp, tmp + " + " + "1");
				Tag m = condition(tmp, Tag.MEQ, Integer.toString(size));
				label(m.getV());

				if(e1.indexOf('[') == -1){
						assignment(e1, array + "[" + tmp + "]");
				}
				else{
						String tmp2 = getTemp(Var.getLastVar(ident).getTipo());
						assignment(tmp2, array + "[" + tmp + "]");
						out.println("	" + e1 + " = " + tmp2 + ";");
				}
				return m;
		}

		public static void printLine(String n){
			out.println(n);
		}

		public static void dimension(String ident, String index, int ind){
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

				if(!Var.getLastVar(ident).isArray()){
						return;
				}

				Tag tag = new Tag(getTag(), getTag());
				int size;
				if(ind == 0){
					size = Var.getLastVar(ident).getTam();
				}
				else{
					size = Var.getLastVar(ident).getTam2();
				}

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

		public static boolean isZero(String in){

     		Pattern p = Pattern.compile("[0]*\\.?[0]*");

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
