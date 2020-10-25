package com.example.dell.push;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


public class Background extends AsyncTask<String,Void,String> {
    Context context;

    public Background(Context context){
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://192.168.1.8/fcmtest/fcm_insert1.php";
        Log.d("TAG","After URL");

        String method = params[0];
        if (method.equals("Token")){
            String Token = params[1];

//            Log.d("TAG",Token);


            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                Log.d("TAG0","Open Connet");

                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                Log.d("TAG","BufferWrite");

                String product = URLEncoder.encode("fcm_token","UTF-8")+"="+URLEncoder.encode(Token,"UTF-8");

                Log.d("TAG","Data parameter set");

                bufferedWriter.write(product);
                bufferedWriter.flush();
                bufferedWriter.close();
                Log.d("TAG","Buffer Close");

                os.close();
//get response from server
                // Read data sent from server
                InputStream input = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                input.close();
                httpURLConnection.disconnect();
                // Pass data to onPostExecute method
                return result.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Toast.makeText(context, "Thanks You"+result, Toast.LENGTH_SHORT).show();
    }
}