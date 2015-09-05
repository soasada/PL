import java.util.Stack;
import java.util.ArrayList;

public class Var{

	private static Stack<String> pila = new Stack<String>();
	private static int maxPila = 0;
	private static ArrayList<Variable> variables = new ArrayList<Variable>();

	public static void push(String n){
		pila.push(n);
		if(pila.size() > maxPila){
			maxPila = pila.size();
		}
	}

	public static void add(String nombre, String valor, int tipo){		
		variables.add(new Variable(nombre, valor, tipo));
	}

	public static int getStackTam(){
		return maxPila;
	}

	public static String peek(){
		return pila.peek();
	}

	public static int getVarTam(){
		return variables.size();
	}

	public static String pop(){
		return pila.pop();
	}
	
	public static ArrayList<Variable> getVars(){
		return variables;
	}

	public static Variable getVar(int index){
		return variables.get(index);
	}

	public static void modificar(int index, Variable var){
		variables.set(index, var);
	}

	public static String getNombre(int pos){
		return variables.get(pos).getNombre();
	}

	public static String indexOf(String nombre){
		int salida = 0;
		for(int i = 0; i < variables.size(); i++){
			if(getNombre(i).equals(nombre)){
				salida = i;
			}
		}

		return Integer.toString(salida);
	}

	public static Stack<String> getPila(){
		return pila;
	}
}
