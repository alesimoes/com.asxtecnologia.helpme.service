package com.asxtecnologia.helpme.service;

import java.nio.channels.NotYetConnectedException;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by alexandre.simoes on 06/01/14.
 */
public class tracker {
    public static Double Latitude=53.558;
    public static Double Longitude=9.927;
    private static Double lastLatitude=53.558;
    private static Double lastLongitude=9.927;
    public static Location myLocation;  
    private static Boolean paused=false;
   
    public tracker()
    {

    }

    public void IniciarServico( LocationManager lcm )
    {
        paused = false;
         LocationManager locationManager = lcm;

         LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                Atualizar(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,  5000L, 10F, locationListener);
     
        
    }

    public static void Pause()
    {
        paused = true;
     
    }

    public static void Resume()
    {
        paused = false;
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  5000L, 10F, locationListener);
    }


    private void Atualizar(Location location)
    {
        //if(!paused)
        {
            myLocation = location;
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();
            // Atualiza no arquivo texto os dados da localizaÃ§Ã£o.
            //Maps.SetMyLocation(location);
        }
        // Verifica se Ã© para atualizar o servidor.
        //Token.SetGeo(, geo)
        AtualizaServer();
    }


    private void AtualizaServer()
    {
        if((lastLatitude - Latitude>0.01 || lastLatitude - Latitude<-0.01)
                || (lastLongitude - Longitude>0.01 || lastLongitude - Longitude<-0.01))
        {
            //new geoLocationService().execute(Security.Key,Longitude.toString(),Latitude.toString());
            lastLatitude = Latitude;
            lastLongitude = Longitude;
                try{
                    try {
                        if(AsxSocket.Socket.isOpen())
                        {
                            AsxSocket.Socket.send( "{\"MessageType\":\"GPS\",\"Token\":\""+Token.Token+"\",\"Latitude\":"+ Latitude +",\"Longitude\":"+Longitude+"}");
                        }
                    } catch (NotYetConnectedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }finally
                {
                }
         }        
    }
}