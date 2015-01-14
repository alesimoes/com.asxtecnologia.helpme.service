package com.asxtecnologia.helpme.service;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;



public class AsxCheckService extends BroadcastReceiver 
{

Context _context;
private static int countPowerOff = 0;
public  AsxCheckService (){
}

@Override
public void onReceive(Context context, Intent intent)
{
        //if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
    _context = context;
    
      Toast.makeText(context, "Verificando se o serviço está iniciado.", Toast.LENGTH_SHORT).show();
              
        // Verifica se o serviÃ§o foi iniciado.
         Intent serviceIntent = new Intent(context, com.asxtecnologia.helpme.service.StartService.class);
         if(!isMyServiceRunning(com.asxtecnologia.helpme.service.StartService.class))
         {
            context.startService(serviceIntent);     
            Toast.makeText(context, "Serviço iniciado com sucesso.", Toast.LENGTH_SHORT).show();
          
         }else
         {
             Toast.makeText(context, "Serviço já estava em execução.", Toast.LENGTH_SHORT).show();
            try{
     
                 if(AsxSocket.context==null)
                 {
                     AsxSocket.Reconnect();
                 }
                 AsxSocket.context = context;
             }catch (Exception ex)
             {
                ;
             }
           }
 }


private boolean isMyServiceRunning(Class<?> serviceClass) {
   try{
    ActivityManager manager = (ActivityManager) _context.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
             if (serviceClass.getName().equals(service.service.getClassName())) {
                    //manager.killBackgroundProcesses(service.clientPackage);
                
                    return true;
                }
        }
   
 }catch (Exception ex)
        {
            return false;
            }

}