package com.asxtecnologia.helpme.service;

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
        // Inicia conexÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â£o com o servidor.
    
    if(AsxSocket.isConnected==false)
    {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
         
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                              activeNetwork.isConnectedOrConnecting();
        if(isConnected && AsxSocket.Socket.isConnecting()==false && AsxSocket.Socket.isOpen()==false)
        {
         AsxSocket.Socket = null;
         AsxSocket s = new AsxSocket(AsxSocket.context);
        }
        
    
    }
 }
}
