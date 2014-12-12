package com.asxtecnologia.helpme.service;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class StartService extends Service{  
      
      /** Called when the activity is first created. */
    @Override
    public void onCreate() {
        super.onCreate();
        //Log.d("TAG", "Service created.");
       // Toast.makeText(getApplicationContext(), "Service has ben created.",
       //         Toast.LENGTH_LONG).show();
      
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       
    	 int start = super.onStartCommand(intent, flags, startId);
         
    	 // Serviço de rastreamento.
         final tracker tk = new tracker();
   		 tk.IniciarServico((LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE));
 		
        
 		 //intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
         Toast.makeText(getApplicationContext(), "Gps Iniciado.",
                 Toast.LENGTH_SHORT).show();      
         
         
         // Serviço de conexão.
         final AsxSocket socket = new AsxSocket(getApplicationContext());
 	 
 		Toast.makeText(getApplicationContext(), "Conexão iniciada.",
 	                Toast.LENGTH_SHORT).show(); 
        return startId;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
       
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}