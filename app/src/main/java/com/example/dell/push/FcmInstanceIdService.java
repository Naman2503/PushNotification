package com.example.dell.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by root on 29/10/17.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG,"QWE"+recent_token);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.ECM_PREF), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.ECM_PREF),recent_token);
        editor.apply();


    }
}