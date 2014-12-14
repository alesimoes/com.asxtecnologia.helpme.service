package com.asxtecnologia.helpme.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class AsxNetworkReceiver extends BroadcastReceiver {


public AsxNetworkReceiver (){

}

@Override
public void onReceive(Context context, Intent intent){
        // Inicia conexÃƒÆ’Ã‚Â£o com o servidor.
    
    if(AsxSocket.isConnected==false)
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                 {
                        AsxSocket.Socket = null;
                        AsxSocket s = new AsxSocket(AsxSocket.context);
                 }
               
            }
        }, 10000);
        
    
    }
 }
}
