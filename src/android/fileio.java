package com.asxtecnologia.helpme.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by alexandre.simoes on 09/01/14.
 */
public class fileio {
    public void writeToFile(String data, FileOutputStream fl) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fl);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
           // Log.e(TAG, "File write failed: " + e.toString());
        }

    }

    public String readFromFile(InputStream inputStream) {

        String ret = "";

        try {
             //inputStream = openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            //Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
           // Log.e(TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }
}