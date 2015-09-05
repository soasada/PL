package absyn;

public class EInt extends Exp{

	public final Integer integer_;
	
	public EInt(Integer p1){
		integer_ = p1;
	}

	public Object generate(){
		return integer_;
	}
}
