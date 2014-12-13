package com.asxtecnologia.helpme.service;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.exceptions.InvalidFrameException;
import org.java_websocket.framing.FrameBuilder;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

public class AsxWebSocketClient extends WebSocketClient {

  interface AsxSocketCallback{
    void onMessage(String s);
    void onClosed();
    void onOpen();  
    void onError(Exception ex);
  }
  
  private FrameBuilder frameBuilder;
  private AsxSocketCallback callback;

  private static final Map<String, String> draftMap = new HashMap<String, String>();
  static {
    draftMap.put("draft10", "org.java_websocket.drafts.Draft_10");
    draftMap.put("draft17", "org.java_websocket.drafts.Draft_17");
    draftMap.put("draft75", "org.java_websocket.drafts.Draft_75");
    draftMap.put("draft76", "org.java_websocket.drafts.Draft_76");
  }
  
  private static final Map<READYSTATE, Integer> stateMap = new HashMap<READYSTATE, Integer>();
  static {
    stateMap.put(READYSTATE.CONNECTING, 0);
    stateMap.put(READYSTATE.OPEN, 1);
    stateMap.put(READYSTATE.CLOSING, 2);
    stateMap.put(READYSTATE.CLOSED, 3);
    stateMap.put(READYSTATE.NOT_YET_CONNECTED, 3);
  }

  public AsxWebSocketClient(URI serverURI, AsxSocketCallback call) {
    super(serverURI, new Draft_17());
    this.frameBuilder = new FramedataImpl1();
    this.callback = call;
    
    if (serverURI.getScheme().equals("wss")) {
      try {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        final TrustManager[] tm = null;
        sslContext.init(null, tm, null);
        SSLSocketFactory factory = sslContext.getSocketFactory();
        this.setSocket(factory.createSocket());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }  
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    this.send("{\"MessageType\":\"Ping\"}");  
    this.callback.onOpen();
  }

  @Override
  public void onMessage(String message) {
   // sendResult(message, "message", PluginResult.Status.OK);
   this.callback.onMessage(message);
   
  }
  
  @Override
  public void onMessage(ByteBuffer bytes) {
    JSONArray jsonArr = Utils.byteArrayToJSONArray(bytes.array());
      
  }

  @Override
  public void onFragment(Framedata frame) {
    try {
      this.frameBuilder.append(frame);
      
      if (frame.isFin()) {
        ByteBuffer bytes = this.frameBuilder.getPayloadData();

        if (this.frameBuilder.getOpcode() == Framedata.Opcode.BINARY) {
          this.onMessage(bytes);
        } 
        else {
          this.onMessage(new String(bytes.array(), "UTF-8"));
        }

        this.frameBuilder.getPayloadData().clear();
      }
    } 
    catch (Exception e) {} 
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
   // sendResult("", "close", PluginResult.Status.OK);
  this.callback.onClosed();
  }

  @Override
  public void onError(Exception ex) {
    //sendResult(ex.getMessage(), "error", PluginResult.Status.ERROR);
    this.callback.onError(ex);
  }

  @Override
  public String getResourceDescriptor() {
    return "*";
  }
}
