package org.lab4.providers;

import org.lab4.network.Network;
import org.lab4.services.Service;

import java.util.List;

public class Provider
{

    private Network networkType;
    private String name;
    private List<Service> serviceList;

    public Provider(String name, Network networkType, List<Service> serviceList)
    {
        this.name = name;
        this.networkType = networkType;
        this.serviceList = serviceList;
    }

    public Network getNetworkType() {
        return networkType;
    }

    public String getName() {
        return name;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public Service getServiceByKey(String serviceName)
    {
        for (var i : serviceList){
            if (i.getName().equalsIgnoreCase(serviceName)){
                return i;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "networkType=" + networkType +
                ", name='" + name + '\'' +
                ", serviceList=" + serviceList +
                '}';
    }
}
