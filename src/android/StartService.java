package com.asxtecnologia.helpme.service;



import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.Time;
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
         // Se jÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¡ tivr efetuado login starta o processo.
         if(Token.Token!="")
         {
             // Inicia conexÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â£o com o servidor.
            final AsxSocket socket = new AsxSocket(getApplicationContext());    

         
             // ServiÃƒÆ’Ã†â€™Ãƒâ€ Ã¢â‚¬â„¢ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â§o de rastreamento.
             final tracker tk = new tracker();
             tk.IniciarServico((LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE));       
            
             //Toast.makeText(getApplicationContext(), "Iniciado.",
             //        Toast.LENGTH_LONG).show();
             
             // Inicia o broadcast de pedido de ajuda instantaneo.
             AsxReceiver rc = new AsxReceiver();
             registerReceiver(rc, new IntentFilter(Intent.ACTION_SCREEN_ON));    
             
             IntentFilter intentFilter = new IntentFilter();
             intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
             AsxNetworkReceiver net =  new AsxNetworkReceiver();
             registerReceiver(net,intentFilter);

             // Runable ping.
             final Handler handler = new Handler();

             final Runnable r = new Runnable() {
                 public void run() {
                     try{
                     AsxSocket.Socket.send("{\"MessageType\":\"Ping\",\"Id\":\""+AsxSocket.Id+"\"}");  
                      
                 }
                     catch(Exception e)
                 {
                     AsxSocket.Reconnect();
                 }
                     handler.postDelayed(this, 60000);
                     
                 }
             };
             handler.postDelayed(r, 60000);
         }
        return start;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
       
    }
    
    @SuppressLint("NewApi")
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        super.onTaskRemoved(rootIntent);
        
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}