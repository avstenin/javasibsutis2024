package org.lab4;

import org.lab4.network.Cable;
import org.lab4.network.Mobile;
import org.lab4.network.Network;
import org.lab4.providers.Provider;
import org.lab4.services.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Menu
{
    private Scanner scanner;

    private List<Provider> providersList;

    public Menu(){
        scanner =  new Scanner(System.in);
        providersList = new ArrayList<>();
        System.out.println("Hello! " + System.getProperty("user.name"));
    }

    public void userChoice()
    {
        while (true){
            printContext();
            switch (scanner.nextLine())
            {
                case "1": getProvidersByService();  break;
                case "2" : getServiceByProvider();break;//printByRegime(false); break;
                case "3" : printAllTable(); break;
                case "4" :  System.out.println("Bye! " + System.getProperty("user.name")); return;
            }
        }
    }

    public void getProvidersByService()
    {
        System.out.println("Какая услуга вас заинтересовала?");
        String key = scanner.nextLine();
        Map<Provider, Integer> map = new HashMap<>();
        for (var i : providersList)
        {
            var find = i.getServiceByKey(key);
            if (find != null){
                map.put(i, find.getCoast());
            }
        }

        if (map.isEmpty()){
            System.out.println("Такой услуги не существует!");
            return;
        }
        printUpTable();
        map.entrySet().stream().sorted(Map.Entry.comparingByValue()).
                forEach(x-> System.out.printf("%-14s | %-10s | %-15s | %-10d\n", x.getKey().getNetworkType().getClass().getSimpleName(),
                        x.getKey().getName(),
                        key.toUpperCase(),
                        x.getValue()));
        printLine();
    }

    private void getServiceByProvider()
    {
        System.out.println("Какой оператор вас заинтересовал?");
        String key = scanner.nextLine();
        List<Service> lst = new ArrayList<>();
        for (var i : providersList){
            if (i.getName().equalsIgnoreCase(key)){
                lst.addAll(i.getServiceList());
            }
        }
        if (lst.isEmpty()){
            System.out.println("Такого оператора не существует!");
            return;
        }
        lst.sort(Comparator.comparingInt(Service::getCoast));
        printUpTable();
        boolean f = false;
        for (var provider : providersList)
        {
            for (var service : lst)
            {
                var simpleNetworkTypeName = provider.getNetworkType().getClass().getSimpleName();
                if (provider.getServiceList().contains(service) && simpleNetworkTypeName.equalsIgnoreCase("mobile")){
                    System.out.printf("%-14s | %-10s | %-15s | %-10d\n", simpleNetworkTypeName, provider.getName(), service.getName(), service.getCoast());
                }
                else if (provider.getServiceList().contains(service) && simpleNetworkTypeName.equalsIgnoreCase("cable")){
                    if (!f) { printLine(); f = true; }
                    System.out.printf("%-14s | %-10s | %-15s | %-10d\n", simpleNetworkTypeName, provider.getName(), service.getName(), service.getCoast());
                }
            }

        }
        printLine();


    }




    private void printContext(){
        System.out.println("1. Получение списка операторов, предоставляющих услугу из списка");
        System.out.println("2. Поиск доступных услуг по провайдеру");
        System.out.println("3. Вывести всё");
        System.out.println("4. Выход");
    }






    private void printAllTable(String networkType){
        for (Provider provider : providersList)
        {
            if (provider.getNetworkType().getClass().getSimpleName().equalsIgnoreCase(networkType)) {
                for (var service : provider.getServiceList()) {
                    System.out.printf("%-14s | %-10s | %-15s | %-10d\n", networkType, provider.getName(), service.getName(), service.getCoast());
                }
            }
        }
    }




    private void printUpTable(){
        System.out.printf("%-10s | %-10s | %-15s | %-10s\n", "Тип соединения", "Провайдер", "Услуга", "Стоимость");
        printLine();
    }

    private void printLine(){
        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        System.out.println();
    }



    public void printAllTable(){
        printUpTable();
        printAllTable("Cable");
        printAllTable("Mobile");
        printLine();
    }




    public boolean readInfo(){

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("info.csv"))))){
            while (bufferedReader.ready()){
                var lines = new ArrayList<String>();
                for (int i = 0; i < 4; i++) {
                    lines.add(bufferedReader.readLine());
                }
                var provider = parseProvider(lines);
                if (provider == null){
                    return false;
                }
                providersList.add(provider);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public Provider parseProvider(List<String> lines){

        try
        {

            String name = lines.get(0).strip();

            Network type = lines.get(1).split(" ")[1].equals("mobile") ? new Mobile() : new Cable();

            var services = new ArrayList<Service>();

            var parseServiceArray = lines.get(2).split(":")[1].split(", ");

            for (var i : parseServiceArray)
            {
                var nameAndCoast = i.split(" ");
                services.add(new Service(nameAndCoast[0], Integer.parseInt(nameAndCoast[1])));
            }

            return new Provider(name, type, services);
        }

        catch (Exception e){
            System.out.println("Ошибка. Пожалуйста, исправьте .csv файл!");
        }



        return null;
    }
}
