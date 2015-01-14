package com.asxtecnologia.helpme.service;

import java.util.Random;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;

public class AsxNetworkReceiver extends BroadcastReceiver {

    private Context context;

public AsxNetworkReceiver (){

}

private boolean isMyServiceRunning(Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
             if (serviceClass.getName().equals(service.service.getClassName())) {
                    //manager.killBackgroundProcesses(service.clientPackage);
                
                    return true;
                }
        }
    return false;
    }
    
@Override
public void onReceive(final Context context, Intent intent){
       
    this.context = context;
    ConnectivityManager cm =
               (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
       NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
       boolean isConnected = activeNetwork != null &&
                             activeNetwork.isConnectedOrConnecting();
       
       // Verifica se o serviço foi iniciado.
       Intent serviceIntent = new Intent(context, com.asxtecnologia.helpme.service.StartService.class);
        if(!isMyServiceRunning(com.asxtecnologia.helpme.service.StartService.class))
        {
        context.startService(serviceIntent);        
        }
    
       
        AsxSocket.context = context;
        
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
                         try{
                             AsxSocket.Reconnect();
                         }
                         catch(Exception ex)
                         {
                             //try{
                                //if(AsxSocket.Socket==null)
                                //{
                                //  AsxSocket s = new AsxSocket(context);                                   
                                //}
                            //}catch(Exception exc)
                            //{
                            
                            //}
                                
                    }
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
