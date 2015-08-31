import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Var{

	private static Map<String, List<Variable>> variables = new HashMap<String, List<Variable>>();
	private static int nivel = 0;

	public static void addVar(String name, Variable n){
		if(exists(name)){
			variables.get(name).add(n);
		}
		else{
			ArrayList<Variable> levels = new ArrayList<Variable>();
			levels.add(n);
			variables.put(name, levels);
		}
	}

	public static void upLevel(){
		nivel++;
	}

	public static String getVar(String name){
		int n = variables.get(name).size();
                if(n > 1){
                        return name + "_" + variables.get(name).get(n-1).getNivel();
                }
                else{
                        return name;
                }
	}

	public static void downLevel(){
		if(nivel > 0)
			nivel--;
	}

	public static int getLevel(){
		return nivel;
	}

	public static void clear(Integer level){

		for(Iterator<Map.Entry<String, List<Variable>>> it = variables.entrySet().iterator(); it.hasNext(); ) {

			Map.Entry<String, List<Variable>> entry = it.next();
			List<Variable> levels = entry.getValue();
		      
		      	Variable ult = levels.get(levels.size()-1);
		      
		      	if(ult.getNivel() == level) {
		        	entry.getValue().remove(levels.indexOf(ult));
		        
		        	if(entry.getValue().size() == 0)
		        		it.remove();
		      	}
		      
		}
	}

	public static boolean exists(String name){
		return variables.containsKey(name);
	}

	public static List<Variable> getListVar(String name){

		String salida = "";
		boolean flag = true;

		for(int i = 0; i < name.length() && flag; i++){
			if(name.charAt(i) == '_'){
				flag = false;
			}
			else{
				salida += name.charAt(i);
			}
		}
		return variables.get(salida);
	}

	public static Variable getLastVar(String name){
		return getListVar(name).get(getListVar(name).size()-1);
	}

}
