package com.asxtecnologia.helpme.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

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

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  5000L, 10F, locationListener);
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
            // Atualiza no arquivo texto os dados da localização.
            //Maps.SetMyLocation(location);
        }
        // Verifica se é para atualizar o servidor.
        AtualizaServer();
    }


    private void AtualizaServer()
    {
        if((lastLatitude - Latitude>0.00000050 || lastLatitude - Latitude<-0.00000050)
                &&(lastLongitude - Longitude>0.00000050 || lastLongitude - Longitude<-0.00000050))
        {
            //new geoLocationService().execute(Security.Key,Longitude.toString(),Latitude.toString());
            lastLatitude = Latitude;
            lastLongitude = Longitude;
        }
    }
}