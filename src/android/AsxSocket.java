package com.asxtecnologia.helpme.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.handshake.ServerHandshake;

import android.content.Context;
import android.widget.Toast;

import com.asxtecnologia.helpme.*;
import com.asxtecnologia.helpme.service.AsxWebSocketClient.AsxSocketCallback;

public  class AsxSocket implements AsxSocketCallback {
	
	public static Context context;
	public static AsxWebSocketClient Socket; 
	public static URI serverURI;
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
		 if(this.Socket==null)
		 {
		   	AsxSocket.context = context;
			 this.Socket = new AsxWebSocketClient(serverURI, this);
			 this.Socket.connect();
			 //this.Socket = this;
		     if(this.Socket.isConnecting())
		     {
		    	 this.Socket.send("Envio de mensagem");
		     }
		 }
	     
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onOpen() {
		// TODO Auto-generated method stub
		// Toast.makeText(AsxSocket.context,"Conectado ao servidor",
	   //           Toast.LENGTH_LONG).show(); 
		
		AsxSocket.Socket.send( "{\"MessageType\":\"Register\",\"Token\":\""+Token.Token+"\"}");
	}
	
	@Override
	public void onMessage(String s) {
		// TODO Auto-generated method stub
		// Toast.makeText(AsxSocket.context,"Mensagem:"+s,
	    //          Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
		//		 Toast.makeText(AsxSocket.context,"Conexão fechada",
		//	              Toast.LENGTH_LONG).show();
		
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
