package network;

public class Service {
    private String name = "";
    private double price = 0;
    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public Double getPrice() {
        return price;
    }
}
