package com.asxtecnologia.helpme.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class AsxReceiver extends BroadcastReceiver {
private static int countPowerOff = 0;

public  AsxCheckService (){

}

@Override
public void onReceive(Context context, Intent intent)
{
        //if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
    _context = context;
                       
        // Verifica se o serviÃ§o foi iniciado.
        Intent serviceIntent = new Intent(context, com.asxtecnologia.helpme.service.StartService.class);
         if(!isMyServiceRunning(com.asxtecnologia.helpme.service.StartService.class))
         {
            context.startService(serviceIntent);        
         }else{
     
                 if(AsxSocket.context==null)
                 {
                     AsxSocket.Reconnect();
                 }
                 AsxSocket.context = context;
           }
 }


Context _context;
private boolean isMyServiceRunning(Class<?> serviceClass) {
   
    ActivityManager manager = (ActivityManager) _context.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
             if (serviceClass.getName().equals(service.service.getClassName())) {
                    //manager.killBackgroundProcesses(service.clientPackage);
                
                    return true;
                }
        }
    return false;
    }

}