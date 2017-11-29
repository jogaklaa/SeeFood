package com.ash.fish.seafood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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
import java.net.MalformedURLException;
import java.net.URL;

public class ResponseActivity extends AppCompatActivity {
    final String url = "http://34.234.229.114:8000/fetch/1";
    double negative;
    double certainty;
    String imageurl;
    Drawable uploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        final ImageView imageView = findViewById(R.id.imageView);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response", response.toString());
                    try {
                        JSONObject zero = response.getJSONObject("0");
                        imageurl = zero.getString("photo");
                        Log.d("image", imageurl);
                        uploaded = LoadImageFromWebOperations(imageurl);
                        SetImage(imageurl, imageView);
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


        queue.add(getRequest);

    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public void SetImage(String url, ImageView i){
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
            ImageView imageView;
            i.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
