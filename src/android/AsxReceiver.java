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

public AsxReceiver (){

}

@Override
public void onReceive(Context context, Intent intent)
{
        //if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
    _context = context;
        {    
            //Log.e("In on receive", "In Method:  ACTION_SCREEN_OFF");
            if (countPowerOff >= 2)
            {
                countPowerOff=0;
                //Toast.makeText(context, "Seu pedido de ajuda serÃƒÆ’Ã‚Â¡ enviado em 5 segundos.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, com.asxtecnologia.helpme.service.AlertActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
            countPowerOff++;
            
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                     {
                         countPowerOff=0;
                     }
                   
                }
            }, 15000);
        }
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