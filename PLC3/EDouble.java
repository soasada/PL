public class EDouble extends Exp{

    public final Double double_;

    public EDouble(Double p1){
        double_ = p1;
    }

    public Object generate(){
        return double_;
    }
}

