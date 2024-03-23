package networks;

import java.util.List;

public class Operator {
    private String name;
    private List<Service> services;

    public List<Service> getServices() {
        return services;
    }
    public String getName(String name) {
        return name;
    }
}
