package com.pegalite.pegaserver;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.UUID;

import io.socket.client.IO;
import io.socket.client.Socket;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PegaServerConfig {
    private final String apiKey;
    private final Context context;
    private static WeakReference<PegaServerConfig> pegaServerConfig;

    private String serverAddress, userKey, packageName, deviceId;
    private Retrofit retrofit;
    private ApiInterfaces apiInterfaces;
    private Socket socket;
    private JSONObject defaultRequestObject;

    public PegaServerConfig(String apiKey, Context context) {
        this.apiKey = apiKey;
        this.context = context;
        initServerConfig();
    }

    public static PegaServerConfig getInstance() {
        if (pegaServerConfig == null) {
            throw new RuntimeException("You must call PegaServerApp.initialize() first.");
        }
        return pegaServerConfig.get();
    }

    private void initServerConfig() {
        packageName = context.getPackageName();
        try {
            JSONObject serverObject = new JSONObject(EncryptionUtils.decrypt(apiKey));
            serverAddress = serverObject.getString("serverAddress");
            userKey = serverObject.getString("userKey");

            initRetrofit();

            initSocket();

            initDefaultRequestObject();

            pegaServerConfig = new WeakReference<>(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDefaultRequestObject() throws JSONException {
        defaultRequestObject = new JSONObject();
        defaultRequestObject.put("deviceId", deviceId);
        defaultRequestObject.put("signature", "sadfasdfsdf");
        defaultRequestObject.put("appPackage", packageName);
        defaultRequestObject.put("user", userKey);
    }

    private void initSocket() throws URISyntaxException {
        IO.Options options = new IO.Options();
        deviceId = UUID.randomUUID().toString();
        options.query = "deviceId=" + deviceId;
        socket = IO.socket(serverAddress, options);
        socket.connect();
        socket.on(Socket.EVENT_CONNECT, args -> {
            ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show());
            System.out.println("Connected");
        });
        socket.on(Socket.EVENT_DISCONNECT, args -> {
            ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show());
            System.out.println("Disconnected");
        });
    }

    public Socket getSocket() {
        return socket;
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl(serverAddress).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterfaces = retrofit.create(ApiInterfaces.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public ApiInterfaces getApiInterfaces() {
        return apiInterfaces;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public JSONObject getDefaultRequestObject(String actionType, String path) {
        JSONObject tempObj;
        try {
            tempObj = new JSONObject(defaultRequestObject.toString());
            tempObj.put("actionType", actionType);
            tempObj.put("path", path);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return tempObj;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getPackageName() {
        return packageName;
    }
}
