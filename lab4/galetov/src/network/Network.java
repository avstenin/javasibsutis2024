package network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class Network {
    protected Map<Operator, List<Service>> operators;
    protected List<String> services;
    public Network(){
        operators = new HashMap<>();
        services = new ArrayList<>();
    }

    public List<String> getServices() {
        return services;
    }

    public List<String> getOperators() {
        List<String> operators = new ArrayList<>();
        for (Operator operator : this.operators.keySet()) {
            operators.add(operator.getName());
        }
        return operators;
    }

    public Map<Operator, Double> getServicePrices(String serviceName) {
        Map<Operator, Double> prices = new HashMap<>();
        for(Operator operator : operators.keySet()){
            for(Service service : operators.get(operator)) {
                if(service.getName().equals(serviceName)){
                    prices.put(operator, service.getPrice());
                }
            }
        }
        return prices;
    }

    public List<Service> getServices(String operatorName) {
        List<Service> services = new ArrayList<>();

        for (Operator operator : this.operators.keySet()) {
            if(operator.getName().equals(operatorName)) {
                services = this.operators.get(operator);
            }
        }
        return services;
    }

    public void ReadDataFromCsv(){
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("lab4/galetov/resources/Operators.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Operator currentOperator = null;
        for (List<String> item : records) {
            if (!item.get(0).isEmpty()){
                currentOperator = new Operator(item.get(0));
                operators.put(currentOperator, new ArrayList<>());
                continue;
            }
            operators.get(currentOperator).add(new Service(item.get(1), Integer.parseInt(item.get(2))));
            if(!services.contains(item.get(1))){
                services.add(item.get(1));
            }
        }
    }
}
