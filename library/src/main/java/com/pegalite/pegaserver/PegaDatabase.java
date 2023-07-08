package com.pegalite.pegaserver;

public class PegaDatabase {

    private static PegaDatabase pegaDatabase;

    public static PegaDatabase getInstance() {
//        PegaServerApp app = new PegaServerApp();
//        if (app == null) {
//            throw new RuntimeException("You must call PegaServerApp.initialize() first.");
//        }
        return new PegaDatabase();
    }


    public PegaDatabaseReference getReference() {
        return new PegaDatabaseReference("");
    }

    public PegaDatabaseReference getReference(String path) {
        return new PegaDatabaseReference(path);
    }
}
