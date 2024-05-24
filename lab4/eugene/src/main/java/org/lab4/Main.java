package org.lab4;


import org.lab4.network.Cable;
import org.lab4.network.Mobile;
import org.lab4.network.Network;
import org.lab4.providers.Provider;
import org.lab4.services.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main
{

    public static void main(String[] args) {


        Menu menu = new Menu();
        if (menu.readInfo()){
            menu.userChoice();
        }




    }







}
