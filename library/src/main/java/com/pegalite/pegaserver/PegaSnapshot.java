package com.pegalite.pegaserver;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PegaSnapshot {

    private EliteShot eliteShot;
    private JSONObject data;
    private String key;
    private boolean exists = true;
    private String root = "";
    private Object rawResponse;
    private PegaDatabaseReference databaseReference;

    public PegaSnapshot(EliteShot eliteShot) {
        this.eliteShot = eliteShot;
        data = eliteShot.getData();
        key = eliteShot.getKey();
        exists = eliteShot.isExists();
        root = eliteShot.getRoot();
        rawResponse = eliteShot.getRawResponse();
        databaseReference = eliteShot.getDatabaseReference();
    }

    public PegaSnapshot(String child, EliteShot eliteShot) {
        this.eliteShot = eliteShot;
        data = eliteShot.getData();
        key = eliteShot.getKey();
        exists = eliteShot.isExists();
        rawResponse = eliteShot.getRawResponse();
        databaseReference = eliteShot.getDatabaseReference();

        root = eliteShot.getRoot();
        if (root != null && !root.isEmpty()) {
            root = eliteShot.getRoot() + "/" + child;
        }
    }

    private List<PegaSnapshot> children;

    public List<PegaSnapshot> getChildren() {
        if (children == null) {
            return setChildren();
        }
        return children;
    }

    public <T> T getValue(Class<T> cls) {
        if (eliteShot.getRawResponse() instanceof JSONObject) {
            Gson gson = new Gson();
            return gson.fromJson(rawResponse.toString(), cls);
        } else {
            return (T) rawResponse;
        }
    }

    public Object getValue() {
        return rawResponse;
    }

    private List<PegaSnapshot> setChildren() {
        if (root.isEmpty()) {
            if (data == null || data.length() == 0) {
                return new ArrayList<>();
            }

        }
        return children;
    }

    public int getChildrenCount() {
        if (data == null) {
            return 0;
        }
        return data.length();
    }

    public PegaSnapshot child(String child) {
        if (child.contains("/")) {
            throw new RuntimeException("Child cannot contain /");
        }
        if (root.isEmpty()) {
            return new PegaSnapshot(child, eliteShot);
        }
        return new PegaSnapshot(root + "/" + child, eliteShot);
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getKey() {
        return key;
    }

    public boolean isExists() {
        return exists;
    }


    public PegaDatabaseReference getRef() {
        return databaseReference;
    }


}
