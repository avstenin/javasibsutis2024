package org.lab4.services;

public class Service
{
    private final String NAME;

    private final int COAST;

    public Service(String name, int coast) {
        this.NAME = name;
        this.COAST = coast;
    }

    public String getName() {
        return NAME;
    }

    public int getCoast() {
        return COAST;
    }

    @Override
    public String toString() {
        return "Service{" +
                "NAME='" + NAME + '\'' +
                ", COAST=" + COAST +
                '}';
    }
}
