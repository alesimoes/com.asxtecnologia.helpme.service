package com.asxtecnologia.helpme.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.asxtecnologia.helpme.*;
import com.asxtecnologia.helpme.service.AsxWebSocketClient.AsxSocketCallback;

@SuppressLint("NewApi")
public  class AsxSocket implements AsxSocketCallback {
	
	public static Context context;
	public static AsxWebSocketClient Socket; 
	public static URI serverURI;
	public static Boolean isConnected = true;
	public AsxSocket( Context context) 
	{
		  serverURI = null;
			try {
				serverURI = new URI("ws://helpmeapp.azurewebsites.net/Connection");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			
		//super(serverURI);
		 if(AsxSocket.Socket==null)
		 {
		   	 AsxSocket.context = context;
			 AsxSocket.Socket = new AsxWebSocketClient(serverURI, this);
			 AsxSocket.Socket.connect();
			 //this.Socket = this;
		     if(AsxSocket.Socket.isConnecting())
		     {
		    	 AsxSocket.Socket.send("Envio de mensagem");
		     }
		 }
	     
		// TODO Auto-generated constructor stub
	}
	
	public static void Reconnect()
	{		
		
    	if(AsxSocket.isConnected==false)
 	    {   if(AsxSocket.Socket.isConnecting()==false && AsxSocket.Socket.isOpen()==false)
 	        {
 	        	 AsxSocket.Socket = new AsxWebSocketClient(serverURI, new AsxSocket(AsxSocket.context));
 	    		 AsxSocket.Socket.connect();
 	    		 //this.Socket = this;
 	    	   
 	        }
 	    }
          
	}
	
	@Override
	public void onOpen() {
		// Envia o pedido de registro
		AsxSocket.Socket.send( "{\"MessageType\":\"Register\",\"Token\":\""+Token.Token+"\"}");
		isConnected=true;
	}
	
	@Override
	public void onMessage(String s) {
		// TODO Auto-generated method stub
		 JSONObject obj = null;
		 int msgType=0;
		 JSONObject tk = null;
		 String name="";		 
		 
		try 
		{
			 obj = (JSONObject) new JSONTokener(s).nextValue();
			 msgType= obj.getInt("type");
			 tk = obj.getJSONObject("track");
			 name = tk.getString("Name");
			 
			 switch(msgType)
			 {
			 	case 1:
				 break;
			 	case 2:
			 		//Define sound URI
			 		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			 		
			 		Intent intent = new Intent(AsxSocket.context, com.asxtecnologia.helpme.service.CordovaApp.class);
			 		PendingIntent pIntent = PendingIntent.getActivity(AsxSocket.context, 0, intent, 0);
					// build notification
					// the addAction re-use the same intent to keep the example short
					Notification n  = new Notification.Builder(AsxSocket.context)
					        .setContentTitle("HelpMe")
					        .setStyle(new Notification.BigTextStyle().bigText(name+ " está bem. O pedido de ajuda foi desativado."))
					        .setSmallIcon(R.drawable.icon)
					        .setContentIntent(pIntent)
					        .setAutoCancel(true)
					        .setSound(soundUri).build();
					    
					  
					NotificationManager notificationManager = 
					  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					
					notificationManager.notify(0, n); 
					 break;
			 	case 3:
			 		//Define sound URI
			 		soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			 		
			 		intent = new Intent(AsxSocket.context, com.asxtecnologia.helpme.service.CordovaApp.class);
			 		pIntent = PendingIntent.getActivity(AsxSocket.context, 0, intent, 0);
					// build notification
					// the addAction re-use the same intent to keep the example short
					n  = new Notification.Builder(AsxSocket.context)
					        .setContentTitle("HelpMe")
					        .setStyle(new Notification.BigTextStyle().bigText(name+ " está precisando de sua ajuda. "))
					        .setSmallIcon(R.drawable.icon)
					        .setContentIntent(pIntent)
					        .setAutoCancel(true)
					        .setSound(soundUri).build();
					    
					  
					notificationManager = 
					  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					
					notificationManager.notify(0, n); 
					
					 Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
				     vibrator.vibrate(1000);
				     
				     Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				     Ringtone r = RingtoneManager.getRingtone(context, notification);
				     r.play();
				     
				     vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
				     vibrator.vibrate(1000);
				     
				     notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				     r = RingtoneManager.getRingtone(context, notification);
				     r.play();
				     
				     vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
				     vibrator.vibrate(1500);
				     
				     

					 break;
			 }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onClosed() {
		isConnected = false;
		AsxSocket.Reconnect();	
	}
	
	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		 //Toast.makeText(AsxSocket.context,ex.getMessage(),
	    //          Toast.LENGTH_LONG).show();
		 //AsxSocket.context = context;
		 //this.Socket = new AsxWebSocketClient(serverURI, this);
		 //this.Socket.connect();
		 //this.Socket = this;
	     //if(this.Socket.isConnecting())
	     //{
	     //	 this.Socket.send("Envio de mensagem");
	      // }
		
	}
}
