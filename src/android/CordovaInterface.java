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

import com.asxtecnologia.helpme.R;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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
	           createShortcutIcon();
	            return true;
	        }
	        
	        if (action.equals("Close")) {
		           this.close( callbackContext);
		            return true;
		        }
	        return false;
	    }

	    
	    /*
	     * Obtem as coordenadas do GPS.*/
	    private void gps( CallbackContext callbackContext) {
	        String message = "{\"Latitude\":"+tracker.Latitude+",\"Longitude\":"+tracker.Longitude+"}";
	    	callbackContext.success(message);
	    }

	    /*
	     * Obtem as coordenadas do GPS.*/
	    private void close( CallbackContext callbackContext) {
	       cordova.getActivity().finish();
	    }

	    
	    /*
	     * Inicia o serviÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â§o.
	     * 
	     **/
	     private void start( CallbackContext callbackContext) {
	        //String message = "{\"Latitude\":"+tracker.Latitude+",\"Longitude\":"+tracker.Longitude+"}";
	    	//callbackContext.success(message);
	    	
	    	Intent serviceIntent = new Intent(cordova.getActivity().getApplicationContext(), com.asxtecnologia.helpme.service.StartService.class);
        	if(!isMyServiceRunning(com.asxtecnologia.helpme.service.StartService.class))
        	{
        		cordova.getActivity().startService(serviceIntent);
			}
	    }

	     /*
	      * Atualiza o token no arquivo de tokens.
	      * */
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
	    

	    private boolean isMyServiceRunning(Class<?> serviceClass) {
	    ActivityManager manager = (ActivityManager) this.cordova.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
		    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
		    	 if (serviceClass.getName().equals(service.service.getClassName())) {
		            	manager.killBackgroundProcesses(service.clientPackage);
		                return false;
		            }
		    }
	    return false;
		}
	    
	    final static public String PREFS_NAME = "PREFS_NAME";
	    final static private String PREF_KEY_SHORTCUT_ADDED = "PREF_KEY_SHORTCUT_ADDED";


	    // Creates shortcut on Android widget screen
	    private void createShortcutIcon(){

	        // Checking if ShortCut was already added
	        SharedPreferences sharedPreferences = cordova.getActivity().getPreferences(cordova.getActivity().MODE_PRIVATE);
	        boolean shortCutWasAlreadyAdded = sharedPreferences.getBoolean(PREF_KEY_SHORTCUT_ADDED, false);
	        if (shortCutWasAlreadyAdded) return;

	        Intent shortcutIntent = new Intent(cordova.getActivity().getApplicationContext(), com.asxtecnologia.helpme.CordovaApp.class);
	        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	        Intent addIntent = new Intent();
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "HelpMe");
	        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(cordova.getActivity().getApplicationContext(), R.drawable.icon));
	        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
	        cordova.getActivity().getApplicationContext().sendBroadcast(addIntent);

	        // Remembering that ShortCut was already added
	        SharedPreferences.Editor editor = sharedPreferences.edit();
	        editor.putBoolean(PREF_KEY_SHORTCUT_ADDED, true);
	        editor.commit();
	    }

	}

