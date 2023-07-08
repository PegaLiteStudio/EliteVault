package com.pegalite.pegaserver;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.pegalite.pegaserver.listeners.InternalPegaEventListener;
import com.pegalite.pegaserver.listeners.PegaResponseManagerListener;
import com.pegalite.pegaserver.tasks.PegaTask;
import com.pegalite.pegaserver.tasks.PegaValueTasks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EliteQuery {
    public static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int PUSH_ID_LENGTH = 20;
    public static String ACTION_READ = "read";
    public static String ACTION_WRITE = "write";
    public static String ACTION_UPLOAD = "upload";

    private String root;

    private PegaDatabaseReference currentDatabaseReference;

    public EliteQuery(String root) {
        this.root = root;
    }

    public PegaValueTasks<Void> remove() {
        return new PegaValueTasks<>(new InternalPegaEventListener<Void>() {
            @Override
            public void getListener(InternalPegaEventListener<Void> listener) {
                root = getRoot();
                removeValue(listener);
                super.getListener(listener);
            }
        });
    }

    private void removeValue(InternalPegaEventListener<Void> listener) {
        PegaServerConfig pegaServerConfig = PegaServerConfig.getInstance();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), String.valueOf(pegaServerConfig.getDefaultRequestObject(ACTION_WRITE, root)));

        Call<ResponseBody> requestCall = pegaServerConfig.getApiInterfaces().removeValue(requestBody);

        requestCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        responseManager(response.body().string(), new PegaResponseManagerListener() {
                            @Override
                            public void onSuccess(PegaSnapshot snapshot) {
                                listener.onSuccess(new PegaTask<>(true, null, null));
                            }

                            @Override
                            public void onFailure(PegaDatabaseException exception) {
                                listener.onFailure(exception);
                            }
                        });

                    }
                } catch (IOException e) {
                    listener.onFailure(new PegaDatabaseException("Unable to handle this [PLACE 345]", e));
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                listener.onFailure(new PegaDatabaseException("Error While Processing the Request [PLACE 903]", t));
            }
        });
    }

    public PegaValueTasks<PegaSnapshot> get() {
        return new PegaValueTasks<PegaSnapshot>(new InternalPegaEventListener<PegaSnapshot>() {
            @Override
            public void getListener(InternalPegaEventListener<PegaSnapshot> listener) {
                root = getRoot();
                createServerSnapshot(false, new PegaSnapshotEventListener() {
                    @Override
                    public void onDataChange(PegaSnapshot snapshot) {
                        listener.onSuccess(new PegaTask<>(true, snapshot, null));
                    }

                    @Override
                    public void onFailure(PegaDatabaseException exception) {
                        listener.onFailure(exception);
                    }
                });
            }
        });
    }

    public PegaValueTasks<Void> setDataValue(Object o) {
        return new PegaValueTasks<>(new InternalPegaEventListener<Void>() {
            @Override
            public void getListener(InternalPegaEventListener<Void> listener) {
                root = getRoot();
                pushValue(o, listener);
            }
        });
    }

    private void pushValue(Object o, InternalPegaEventListener<Void> listener) {
        PegaServerConfig pegaServerConfig = PegaServerConfig.getInstance();

        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), String.valueOf(pegaServerConfig.getDefaultRequestObject(ACTION_WRITE, root).put("data", o)));

            Call<ResponseBody> requestCall = pegaServerConfig.getApiInterfaces().setValue(requestBody);

            requestCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    try {
                        if (response.body() != null) {
                            responseManager(response.body().string(), new PegaResponseManagerListener() {
                                @Override
                                public void onSuccess(PegaSnapshot snapshot) {
                                    listener.onSuccess(new PegaTask<>(true));
                                }

                                @Override
                                public void onFailure(PegaDatabaseException exception) {
                                    listener.onFailure(exception);
                                }
                            });

                        }
                    } catch (IOException e) {
                        listener.onFailure(new PegaDatabaseException("Unable to handle this [PLACE 344]", e));
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    listener.onFailure(new PegaDatabaseException("Error While Processing the Request [PLACE 902]", t));
                }
            });
        } catch (JSONException e) {
            listener.onFailure(new PegaDatabaseException("Unable to handle this [PLACE 343]", e));
        }
    }


    public void createServerSnapshot(boolean isRealtime, PegaSnapshotEventListener listener) {
        root = getRoot();
        PegaServerConfig pegaServerConfig = PegaServerConfig.getInstance();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), String.valueOf(pegaServerConfig.getDefaultRequestObject(ACTION_READ, root)));

        Call<ResponseBody> requestCall = pegaServerConfig.getApiInterfaces().addListenerForSingleValueEvent(requestBody);
        if (isRealtime) {
            requestCall = pegaServerConfig.getApiInterfaces().addChildEventListener(requestBody);
        }

        requestCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {

                        responseManager(response.body().string(), new PegaResponseManagerListener() {
                            @Override
                            public void onSuccess(PegaSnapshot snapshot) {
                                if (isRealtime) {
                                    setUpRealtime(pegaServerConfig, listener);
                                }
                            }

                            @Override
                            public void onFailure(PegaDatabaseException exception) {
                                listener.onFailure(exception);
                            }
                        });


                    }
                } catch (IOException e) {
                    listener.onFailure(new PegaDatabaseException("Unable to handle this [PLACE 342]", e));

                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                listener.onFailure(new PegaDatabaseException("Error While Processing the Request [PLACE 901]", t));
            }
        });

    }

    private void setUpRealtime(PegaServerConfig pegaServerConfig, PegaSnapshotEventListener listener) {
        pegaServerConfig.getSocket().on(pegaServerConfig.getUserKey() + "/apps/" + pegaServerConfig.getPackageName() + ":" + root, args -> {
            /* We are using handler here to prevent crash from Non UI Thread operation */
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> responseManager(args[0].toString(), new PegaResponseManagerListener() {
                @Override
                public void onSuccess(PegaSnapshot snapshot) {
                    listener.onDataChange(snapshot);
                }

                @Override
                public void onFailure(PegaDatabaseException exception) {
                    listener.onFailure(exception);
                }
            }));
        });
    }

    /**
     * This function manages the responses
     */
    public void responseManager(String response, PegaResponseManagerListener listener) {
        try {
            JSONObject responseObject = new JSONObject(response);
            if (!responseObject.has("status")) {
                listener.onFailure(new PegaDatabaseException("Unexpected Response From Server [L-701]"));
                return;
            }

            String status = responseObject.getString("status");
            if (!status.equals("success") && !status.equals("error")) {
                listener.onFailure(new PegaDatabaseException("Unexpected Response From Server [L-702]"));
                return;
            }

            if (status.equals("success")) {
                listener.onSuccess(getSnapShot(responseObject));
                return;
            }

            if (responseObject.has("error")) {
                listener.onFailure(new PegaDatabaseException(responseObject.getString("error") + " [ERROR_CODE = " + responseObject.getString("errorCode") + "]"));
                return;
            }
            listener.onFailure(new PegaDatabaseException("Unknown Error Occurred [L-703]"));
        } catch (JSONException e) {
            listener.onFailure(new PegaDatabaseException(e.getMessage() + " [L-801]", e));
        }
    }

    private PegaSnapshot getSnapShot(JSONObject response) throws JSONException {
        if (response.has("data")) {
//            PegaSnapshot pegaSnapshot = new PegaSnapshot();
//            if (response.get("data") instanceof String && response.getString("data").equals(":no[::]data:")) {
//                pegaSnapshot.setExists(false);
//            } else {
//                pegaSnapshot.setRawResponse(response.get("data"));
//            }
//            if (response.has("key")) {
//                pegaSnapshot.setKey(response.getString("key"));
//            }
//            pegaSnapshot.setDatabaseReference(currentDatabaseReference);
            return null;
        }
        return null;
    }


    private String getRoot() {
        if (root == null || root.isEmpty()) {
            root = "/";
        }
        return root;
    }


    public String generatePushID(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return "-" + sb;
    }

    public String getKey() {
        if (root != null && !root.isEmpty()) {
            return root.substring(root.lastIndexOf("/") + 1);
        }
        return root;
    }

    public void setCurrentDatabaseReference(PegaDatabaseReference currentDatabaseReference) {
        this.currentDatabaseReference = currentDatabaseReference;
    }

    public boolean canDirectlyParsable(Object o) {
        return isPlainValue(o) || o instanceof JSONObject;
    }

    public boolean isPlainValue(Object value) {
        return value instanceof String || value instanceof Integer || value instanceof Double || value instanceof Boolean || value instanceof Float || value instanceof Long || value instanceof Short || value instanceof Byte || value instanceof Character;
    }

    public JSONObject convertToJSONObject(Object value) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (value instanceof HashMap) {
                HashMap<?, ?> hashMap = (HashMap<?, ?>) value;
                for (Object key : hashMap.keySet()) {
                    Object mapValue = hashMap.get(key);
                    if (isPlainValue(mapValue)) {
                        jsonObject.put(key.toString(), mapValue);
                    } else {
                        jsonObject.put(key.toString(), convertToJSONObject(mapValue));
                    }
                }
            } else if (value instanceof ArrayList) {
                ArrayList<?> arrayList = (ArrayList<?>) value;
                for (int i = 0; i < arrayList.size(); i++) {
                    jsonObject.put(String.valueOf(i), convertToJSONObject(arrayList.get(i)));
                }
            } else {
                for (java.lang.reflect.Method method : value.getClass().getMethods()) {
                    if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
                        String fieldName = method.getName().substring(3);
                        fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                        Object fieldValue = method.invoke(value);
                        if (canDirectlyParsable(fieldValue)) {
                            jsonObject.put(fieldName, fieldValue);
                        } else {
                            jsonObject.put(fieldName, convertToJSONObject(fieldValue));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
