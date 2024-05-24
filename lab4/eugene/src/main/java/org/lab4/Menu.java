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
        sortByCost();
        while (true){
            printContext();
            switch (scanner.nextLine())
            {
                case "1": printByRegime(true); break;
                case "2" : printByRegime(false); break;
                case "3" : printAllTable(); break;
                case "4" :  System.out.println("Bye! " + System.getProperty("user.name")); return;
            }
        }
    }


    private void printContext(){
        System.out.println("1. Получение списка операторов, предоставляющих услугу из списка");
        System.out.println("2. Поиск доступных услуг по провайдеру");
        System.out.println("3. Вывести всё");
        System.out.println("4. Выход");
    }

    private List<Provider> getValueInOperatorList(String value)
    {
        List<Provider> checkList = new ArrayList<>();
        for (var provider : providersList) {
            if (provider.getName().equalsIgnoreCase(value)) checkList.add(provider);
        }
        return checkList;
    }

    private List<Service> getValueInServiceList(String value)
    {
        List<Service> checkList = new ArrayList<>();
        for (var provider : providersList) {
            for(var i : provider.getServiceList())
            {
                if (i.getName().equalsIgnoreCase(value)) {
                    checkList.add(i);
                }
            }
        }
        return checkList;
    }


    private void printProviderTable(List<Provider> checkList)
    {
        for (var provider : checkList)
        {
            for (var service : provider.getServiceList()) {
                System.out.printf("%-14s | %-10s | %-15s | %-10d\n", provider.getNetworkType().getClass().getSimpleName(), provider.getName(), service.getName(), service.getCoast());
            }
            System.out.println();
        }
    }



    private void printServiceTable(List<Service> checkList) {
        checkList.sort(Comparator.comparingInt(Service::getCoast));
        Map<Provider, List<Service>> providerServiceMap = new HashMap<>();
        for (var provider : providersList) {
            providerServiceMap.put(provider, new ArrayList<>());
            for (var service : checkList) {
                if (provider.getServiceList().contains(service)) {
                    providerServiceMap.get(provider).add(service);
                }
            }
        }
        for (var entry : providerServiceMap.entrySet()) {
            var provider = entry.getKey();
            var services = entry.getValue();
            for (var service : services) {
                System.out.printf("%-14s | %-10s | %-15s | %-10d\n", provider.getNetworkType().getClass().getSimpleName(), provider.getName(), service.getName(), service.getCoast());
            }
            //System.out.println();
        }
    }

    public void printByRegime(boolean regime)
    {
        System.out.println(regime ? ("Какая услуга вас заинтересовала?") : ("Какой провайдер вас заинтересовал?"));

        var choice = scanner.nextLine();


        var checkList = !regime ? getValueInOperatorList(choice) : getValueInServiceList(choice);

        if (checkList.isEmpty()){
            System.out.println( regime ? ("Такой услуги не существует!") : ("Такого провайдера не существует!"));
            return;
        }

        printUpTable();
        if (regime) {
            printServiceTable((List<Service>) checkList);
        } else {
            printProviderTable((List<Provider>) checkList);
        }
        printLine();
    }

    private void sortByCost(){
        for (Provider provider : providersList)
        {
            provider.getServiceList().sort(Comparator.comparingInt(Service::getCoast));
        }
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
