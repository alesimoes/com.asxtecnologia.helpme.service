package com.asxtecnologia.helpme.service;

import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;

public class AsxNetworkReceiver extends BroadcastReceiver {


public AsxNetworkReceiver (){

}

@Override
public void onReceive(Context context, Intent intent){
       
    ConnectivityManager cm =
               (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
       NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
       boolean isConnected = activeNetwork != null &&
                             activeNetwork.isConnectedOrConnecting();
    if(isConnected)   
    {
        tracker.Resume();
        

        Random r = new Random();
        int time = r.nextInt(60000) + 5000;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
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
        }, time);
        
        
    }
    else
    {
        tracker.Pause();
    }
    
    
 }
}
