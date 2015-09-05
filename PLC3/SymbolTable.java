import java.util.HashMap;
import java.util.Map;

public class SymbolTable{

	private Map<String, Variable> symbolTable;

	public SymbolTable(){
		symbolTable = new HashMap<String, Variable>();
	}

	public void add(String nombre, Variable var){
		symbolTable.put(nombre, var);
	}
}
