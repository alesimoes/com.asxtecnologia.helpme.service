package com.asxtecnologia.helpme.service;

import java.io.FileNotFoundException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Intent;

	/**
	 * This class echoes a string called from JavaScript.
	 */
public class CordovaInterface extends CordovaPlugin {
	    @Override
	    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	        if (action.equals("Token")) {
	            String message = args.getString(0);
	            this.token(message, callbackContext);
	            return true;
	        }
	        if (action.equals("GPS")) {
	           this.gps( callbackContext);
	            return true;
	        }

	        if (action.equals("Start")) {
	           this.start( callbackContext);
	            return true;
	        }
	        return false;
	    }

	    private void gps( CallbackContext callbackContext) {
	        String message = "{\"Latitude\":"+tracker.Latitude+",\"Longitude\":"+tracker.Longitude+"}";
	    	callbackContext.success(message);
	    }

	     private void start( CallbackContext callbackContext) {
	        //String message = "{\"Latitude\":"+tracker.Latitude+",\"Longitude\":"+tracker.Longitude+"}";
	    	//callbackContext.success(message);
	    	// Inicia o serviço 

	    	Intent serviceIntent = new Intent();
			serviceIntent.setAction("com.asxtecnologia.helpme.service.StartService");
		    cordova.getActivity().startService(serviceIntent);
	    }
	    
	    private void token(String message, CallbackContext callbackContext) {
	        if (message != null && message.length() > 0) {
	        	// Salva o token no arquivo de tokens.
	        	try {
					Token.SetToken(cordova.getActivity().openFileOutput(Token.File,cordova.getActivity().getApplicationContext().MODE_PRIVATE),message);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	//callbackContext.success(message);
	        } else {
	           // callbackContext.error("Expected one non-empty string argument.");
	        }
	    }
	}

