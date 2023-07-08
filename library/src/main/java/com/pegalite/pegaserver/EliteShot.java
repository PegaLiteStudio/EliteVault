package com.pegalite.pegaserver;

import org.json.JSONObject;

public class EliteShot {

    private JSONObject data;
    private String key;
    private boolean exists = true;
    private String root = "";
    private Object rawResponse;
    private PegaDatabaseReference databaseReference;


    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Object getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(Object rawResponse) {
        if (rawResponse instanceof JSONObject) {
            data = (JSONObject) rawResponse;
        }
        this.rawResponse = rawResponse;
    }

    public PegaDatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(PegaDatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }
}
