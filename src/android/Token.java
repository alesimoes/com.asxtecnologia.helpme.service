package com.asxtecnologia.helpme.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by alsim_000 on 21/05/14.
 */
public class Token {
    public static String File="helpme.token";
    public static String Token="";
    public static String FileGeo="helpme.geo";
    public static String Geo="";

    public static void GetToken( FileInputStream flinput)
    {
        try{
            fileio fl = new fileio();
            //FileInputStream flinput;
            String str = fl.readFromFile(flinput);
            Token = str;
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void SetToken (FileOutputStream floutput,  String token)
    {
        // Salva o login no arquivo.
        fileio fl = new fileio();
        fl.writeToFile(token,floutput);
        Token = token;
    }
    
    public static void GetGeo( FileInputStream flinput)
    {
        try{
            fileio fl = new fileio();
            //FileInputStream flinput;
            String str = fl.readFromFile(flinput);
            Geo = str;
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void SetGeo (FileOutputStream floutput,  String geo)
    {
        // Salva o login no arquivo.
        fileio fl = new fileio();
        fl.writeToFile(geo,floutput);
        Geo = geo;
    }
}