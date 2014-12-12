package com.asxtecnologia.helpme.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;



/**
 * Created by alsim_000 on 26/01/14.
 */
public class gpsPersistance {

    public static Double Latitude;
    public static Double Longitude;
    public static boolean Authenticated =false;

    public static void GetLocation( FileInputStream flinput)
    {
            try{
            fileio fl = new fileio();
            //FileInputStream flinput;
            String str = fl.readFromFile(flinput);
            String[] sp = str.split(":");
            Latitude = Double.parseDouble(sp[0]);
            Longitude = Double.parseDouble(sp[1]);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void SetLocation (FileOutputStream floutput,  Double latitude, Double longitude)
    {
    	
      
        
        // Salva o login no arquivo.
        fileio fl = new fileio();
        fl.writeToFile(longitude+":"+longitude,floutput);
        Latitude = latitude;
        Longitude = longitude;
    }
}