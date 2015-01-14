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
      
    
    static Handler handler;
    
     static AsxSocket socket;
     
      /** Called when the activity is first created. */
    @Override
    public void onCreate() {
        super.onCreate();
        //IniciaServicos();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       
         //int start = super.onStartCommand(intent, flags, startId);
         IniciaServicos();
         // tenta abrir o arquivo com o token.
        
        return START_STICKY;
    }
    
    private void IniciaServicos()
    {
         try {
             Token.GetToken(openFileInput(Token.File));
         } catch (FileNotFoundException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
          
          if(Token.Token!="")
          {
             socket = new AsxSocket(getApplicationContext());    

          
              final tracker tk = new tracker();
              tk.IniciarServico((LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE));       
              AsxReceiver rc = new AsxReceiver();
              registerReceiver(rc, new IntentFilter(Intent.ACTION_SCREEN_ON));    
              
              IntentFilter intentFilter = new IntentFilter();
              intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
              AsxNetworkReceiver net =  new AsxNetworkReceiver();
              registerReceiver(net,intentFilter);

              // Runable ping.
              handler = new Handler();

              Runnable r = new Runnable() {
                  public void run() {
                      try{
                      AsxSocket.Socket.send("{\"MessageType\":\"Ping\",\"Id\":\""+AsxSocket.Id+"\"}");
                      AsxSocket.context = getBaseContext();
                      AsxSocket.Pings++;
                      if(AsxSocket.Pings>2)
                      {
                          
                          AsxSocket.Socket.close();
                      }
                       
                  }
                      catch(Exception e)
                  {
                        try{
                          AsxSocket.Reconnect();
                        }catch(Exception excption)
                        {
                          
                        }
                  }
                      handler.postDelayed(this, 60000);
                      
                  }
              };
              handler.postDelayed(r, 60000);
          }
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