package com.pegalite.pegaserver;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pegalite.pegaserver.databinding.ActivityMainBinding;
import com.pegalite.pegaserver.listeners.PegaEventSuccessListener;
import com.pegalite.pegaserver.tasks.PegaTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PegaServerApp.initialize("E3U1RG7X3AJlIWRud8OEvR3cTv9alUpvxcdGMmqBtt7kjJXTad3JqUIlw1pb9jFDvWAxIoJiq2IZYipmz/gUIak9//DmYBs4W4UxAe5QlLo=", this);


        PegaDatabaseReference databaseReference = PegaDatabase.getInstance().getReference();

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            jsonObject.put("name", "name");
            jsonObject.put("emnail", "oka");
            array.put("234234");
            array.put(121);
            array.put(jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Model model = new Model();
        model.setEmail("email@gmail.com");
        model.setName("Shail");
        model.setPass("pass@123");

        Model model2 = new Model();
        model2.setEmail("email@");
        model2.setName("Shail");
        model2.setPass("pass@123");
        model.setModel(model2);
        try {
            model.setObject(new JSONObject().put("data", "data"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> passMap = new HashMap<>();
        Map<String, Object> daysMap = new HashMap<>();
        daysMap.put("1", 0);
        daysMap.put("2", 0);
        daysMap.put("3", 0);
        daysMap.put("4", 0);
        daysMap.put("5", 0);
        daysMap.put("6", 0);
        daysMap.put("7", 0);
        passMap.put("pass", "pass");
        passMap.put("days", daysMap);

        List<Model> list = new ArrayList<>();
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);


        databaseReference.child("conv").child("saquib").push().push().push().push().setValue(list).addOnSuccessListener(new PegaEventSuccessListener<Void>() {
            @Override
            public void onSuccess(PegaTask<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "Success to set", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Failed to set", Toast.LENGTH_SHORT).show();
//                }
            }
        }).addOnFailureListener(exception -> {
            exception.printStackTrace();
            Toast.makeText(this, "M " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "Success to set", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to set", Toast.LENGTH_SHORT).show();
            }
        });


        binding.send.setOnClickListener(v -> {
            String msg = binding.data.getText().toString();
            String path = binding.path.getText().toString();

            if (!msg.isEmpty() && !path.isEmpty()) {

            }
        });
//        databaseReference.child("asdfasd").setValue("fdgfgdfgdf").addOnSuccessListener(task -> Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show());

//
//        PegaDatabaseReference databaseReference1 = databaseReference.child("user").child("sahil").child("name");
//
//        databaseReference1.addChildEventListener(this,new PegaSnapshotEventListener() {
//            @Override
//            public void onDataChange(PegaSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onFailed() {
//
//            }
//        });
//
//        binding.send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference1.addListenerForSingleValueEvent(new PegaSnapshotEventListener() {
//                    @Override
//                    public void onDataChange(PegaSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onFailed() {
//
//                    }
//                });
//            }
//        });

    }

}


//        PegaServerApp pegaServerApp = new PegaServerApp();
//        try {
//            pegaServerApp.connect(this);
//
//            binding.send.setOnClickListener(v -> {
//                String path = binding.path.getText().toString();
//                String data = binding.data.getText().toString();
//
//                if (!path.isEmpty() && !data.isEmpty()) {
//                    pegaServerApp.send(path, data);
//                }
//
//
//            });
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

//    Socket socket;
//
//    public void connect(Context context) throws URISyntaxException {
//        socket = IO.socket("http://192.168.1.6:3000");
//
//        socket.on(Socket.EVENT_CONNECT, args -> {
//            System.out.println("Connected");
//            ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show());
//            socket.emit("user", "saquib");
//        });
//
//        socket.on(Socket.EVENT_DISCONNECT, args -> {
//            System.out.println("Disconnected");
//            ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show());
//
//        });
//
//        socket.on("data", args -> {
//            ((Activity) context).runOnUiThread(() -> Toast.makeText(context, "Data " + args[0], Toast.LENGTH_SHORT).show());
//
//        });
//
//        socket.connect();
//    }
//
//    public void send(String path, String msg) {
//        socket.emit("setData", path, msg);
//
//    }