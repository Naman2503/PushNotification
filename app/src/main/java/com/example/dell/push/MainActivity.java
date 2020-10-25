package com.example.dell.push;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;

import static com.android.volley.VolleyLog.TAG;

public class MainActivity extends AppCompatActivity {

    Button button;


    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v) {
                onTokenRefresh();
                SendToken();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void SendToken(){
        if (isOnline()){
            String method = "Token";
            Log.d("TAG","Connection");

            Log.d("TAG"," BG start");
            Background background = new Background(MainActivity.this);
            background.execute(method,token);

            Log.d("TAG","BG end");
        }else {
            Toast.makeText(this, "Not Connected to Network", Toast.LENGTH_LONG).show();
        }
    }
    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }
    public void onTokenRefresh() {
        token = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(this, "tok-----"+token, Toast.LENGTH_SHORT).show();
        Log.d(TAG,"QWE"+token);
    }
}