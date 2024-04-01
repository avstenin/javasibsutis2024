package networks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Network {
    protected Map<Operator, List<Service>> operators;
    protected Map<Service, List<Operator>> services;

    public Network(){
        operators = new HashMap<>();
        services = new HashMap<>();
    }
}
