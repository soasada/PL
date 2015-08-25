import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Var{

	private static Map<String, List<Integer>> variables = new HashMap<String, List<Integer>>();
	private static int nivel = 0;

	public static void addVar(String name, Integer n){
		if(exists(name)){
			variables.get(name).add(n);
		}
		else{
			ArrayList<Integer> levels = new ArrayList<Integer>();
			levels.add(n);
			variables.put(name, levels);
		}
	}

	public static void upLevel(){
		nivel++;
	}

	public static String getVar(String name){
		if(variables.get(name).size() > 1){
			return name + "_" + nivel;
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

		for(Iterator<Map.Entry<String, List<Integer>>> it = variables.entrySet().iterator(); it.hasNext(); ) {

			Map.Entry<String, List<Integer>> entry = it.next();
			List<Integer> levels = entry.getValue();
		      
		      	int ult = levels.get(levels.size()-1);
		      
		      	if(ult == level) {
		        	entry.getValue().remove(levels.indexOf(ult));
		        
		        	if(entry.getValue().size() == 0)
		        		it.remove();
		        
		      	}
		      
		}
	}

	public static boolean exists(String name){
		return variables.containsKey(name);
	}

}
