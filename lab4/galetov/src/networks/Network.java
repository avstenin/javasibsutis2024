package networks;

import java.util.ArrayList;
import java.util.List;

public abstract class Network {
    protected List<Operator> operators;

    public Network(){
        operators = new ArrayList<>();
    }

    public List<Operator> getOperators(){
        return operators;
    }

    public void addOperator(Operator operator){
        operators.add(operator);
    }
}
