package com.asxtecnologia.helpme.service;



import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
         
         // tenta abrir o arquivo com o token.
         try {
            Token.GetToken(openFileInput(Token.File));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         // Se jÃƒÂ¡ tivr efetuado login starta o processo.
         if(Token.Token!="")
         {
             // Inicia conexÃƒÂ£o com o servidor.
            final AsxSocket socket = new AsxSocket(getApplicationContext());    

         
             // ServiÃƒÂ§o de rastreamento.
             final tracker tk = new tracker();
             tk.IniciarServico((LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE));       
            
             //Toast.makeText(getApplicationContext(), "Iniciado.",
             //        Toast.LENGTH_LONG).show();
             
             // Inicia o broadcast de pedido de ajuda instantaneo.
             AsxReceiver rc = new AsxReceiver();
             registerReceiver(rc, new IntentFilter(Intent.ACTION_SCREEN_OFF));           
         }
        return start;
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