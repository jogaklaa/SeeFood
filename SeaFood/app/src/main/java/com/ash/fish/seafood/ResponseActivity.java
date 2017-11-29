package com.ash.fish.seafood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ResponseActivity extends AppCompatActivity {
    final String url = "http://34.234.229.114:8000/fetch/1";
    String certainty;
    String imageurl;
    ImageView imageView;
    Bitmap bitmap;
    String JSONresponse1;
    TextView certain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        imageView = findViewById(R.id.result);
        certain = findViewById(R.id.certainty);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.d("Response", response.toString());
                    JSONresponse1 = response.toString();
                    Log.d("image", JSONresponse1);
                    try {
                        JSONObject index = response.getJSONObject("0");
                        imageurl = index.getString("photo");
                        certainty = index.getString("certainty");
                        Log.d("image", imageurl);
                        bitmap = getBitmap(imageurl);
                        imageView.setImageBitmap(bitmap);
                        certain.setText(certainty);
                        //uploaded = LoadImageFromWebOperations(imageurl);
                        //SetImage(imageurl, imageView);
                        //imageView.setImageDrawable(uploaded);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "no");
                }
            }
        );

        //Log.v("image", JSONresponse1);
        queue.add(getRequest);
        bitmap = getBitmap(imageurl);
        imageView.setImageBitmap(bitmap);

//        while(true){
//
//        }
    }

    public Bitmap getBitmap(String url) {
        try {
            URL uurrll = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uurrll.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap1 = BitmapFactory.decodeStream(input);
            return bitmap1;
        } catch (Exception e) {
            return null;
        }
    }

    public void SetImage(String url, ImageView i){
        Bitmap bitmap = null;
        try {
            URL urlSource = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlSource.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
           // bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
            ImageView imageView;
            i.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {}
    }


}
