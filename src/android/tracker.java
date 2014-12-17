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
    public static Double Latitude=0.00;
    public static Double Longitude=0.00;
    private static Double lastLatitude=0.00;
    private static Double lastLongitude=0.00;
    public static Location myLocation;  
    public static Location newLocation;
    private static Boolean paused=false;
   
    public tracker()
    {

    }

    private static final int TWO_MINUTES = 1000 * 60 * 5;

    /** Determines whether one Location reading is better than the current Location fix
      * @param location  The new Location that you want to evaluate
      * @param currentBestLocation  The current Location fix, to which you want to compare the new one
      */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
        // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
          return provider2 == null;
        }
        return provider1.equals(provider2);
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

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000*5, 50, locationListener);
     
        
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
            newLocation = location;
           
            // Atualiza no arquivo texto os dados da localizaÃƒÂ§ÃƒÂ£o.
            //Maps.SetMyLocation(location);
        }
        // Verifica se ÃƒÂ© para atualizar o servidor.
        //Token.SetGeo(, geo)
        if(isBetterLocation(newLocation, myLocation))
        {
             Latitude = location.getLatitude();
             Longitude = location.getLongitude();
             AtualizaServer();
            
        }
        myLocation = location;
    }


    private void AtualizaServer()
    {
        if((lastLatitude - Latitude>0.0001 || lastLatitude - Latitude < -0.0001)
                || (lastLongitude - Longitude>0.0001 || lastLongitude - Longitude <-0.0001))
        {
            //new geoLocationService().execute(Security.Key,Longitude.toString(),Latitude.toString());
            lastLatitude = Latitude;
            lastLongitude = Longitude;
                try{
                    try {
                        //if(AsxSocket.Socket.isOpen())
                        //{
                            AsxSocket.Socket.send( "{\"MessageType\":\"GPS\",\"Token\":\""+Token.Token+"\",\"Latitude\":"+ Latitude +",\"Longitude\":"+Longitude+"}");
                        //}
                        //else{
                       // 	if(AsxSocket.Socket!=null)
                       // 	{
                       // 		AsxSocket.Reconnect();
                       // 	}                        			
                        //}
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        try{
                             AsxSocket.Socket.send("{\"MessageType\":\"Ping\",\"Id\":\""+AsxSocket.Id+"\"}");  
                             AsxSocket.Pings++;
                             if(AsxSocket.Pings>2)
                             {
                                 
                                 AsxSocket.Socket.close();
                             }
                              
                         }
                             catch(Exception e)
                         {
                             AsxSocket.Reconnect();
                         }
                    }
                }finally
                {
                }
         }        
    }
}