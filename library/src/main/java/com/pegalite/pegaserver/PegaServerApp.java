package com.pegalite.pegaserver;

import android.content.Context;

import io.socket.client.Socket;

public class PegaServerApp {

    private static Socket socket;

    private static PegaServerApp pegaServerApp;


    public static void initialize(String apiKey, Context context) {
        if (apiKey.isEmpty()) {
            throw new RuntimeException("Api Key Cannon be Empty");
        }
        new PegaServerConfig(apiKey, context);
    }


}
