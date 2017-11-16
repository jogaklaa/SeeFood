package com.ash.fish.seefood;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    int i = 0;
    Button myButton;
    ImageButton foodButton;

    //Input data:
    //Bitmap bitmap = myView.getBitmap();
    Bitmap bitmap = Bitmap.createBitmap(300, 200,Bitmap.Config.ARGB_8888);
    //Static Stuff
    String attachmentName = "bitmap";
    String attachmentFileName = "bitmap.bmp";
    String crlf = "\r\n";
    String twoHyphens = "--";
    String boundary =  "*****";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton  = (Button)findViewById(R.id.colorBtn);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] words = {"One", "Two", "Three", "Four"};
                if(i < words.length){
                    myButton.setText(words[i]);
                    i++;
                }//end if
                createNewProfile(view); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }//end onClick
        });

        foodButton=(ImageButton)findViewById(R.id.imageButton);
        foodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Hello!!!!!!!!!");
            }
        });

    }//end onCreate method --------------------------------



    public void createNewProfile(View view){
        new Post().execute("34.234.229.114:8000");
    }

    private class Post extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL("34.234.229.114:8000");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                ContentValues values = new ContentValues();
                values.put("email", "abc@xyz.com");
                values.put("password", 123);
                values.put("name","ABC");
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(values));
                writer.flush();
                writer.close();
                os.close();
                //response = conn.getResponseCode();
                conn.connect();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i("Result",String.valueOf(e));
            }
            return null;
        }

        private String getQuery(ContentValues values) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, Object> entry : values.valueSet())
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
            }
            Log.i("Result",result.toString());
//            Log.i("Result",result.toString() +" "+ String.valueOf(response));

            return result.toString();
        }
    }

//    public void sendMessage(View view) {
//        //setup the request:
//        HttpURLConnection httpUrlConnection = null;
//        URL url = null;
//        try {
//            url = new URL("34.234.229.114:8000");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            httpUrlConnection = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        httpUrlConnection.setUseCaches(false);
//        httpUrlConnection.setDoOutput(true);
//
//        try {
//            httpUrlConnection.setRequestMethod("POST");
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        }
//        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
//        httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
//        httpUrlConnection.setRequestProperty(
//                "Content-Type", "multipart/form-data;boundary=" + this.boundary);
//
//        //Start content wrapper:
//        DataOutputStream request = null;
//        try {
//            request = new DataOutputStream(
//                    httpUrlConnection.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            request.writeBytes(this.twoHyphens + this.boundary + this.crlf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            request.writeBytes("Content-Disposition: form-data; name=\"" +
//                    this.attachmentName + "\";filename=food.jpeg\"" +
//                    this.attachmentFileName + "\"" + this.crlf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            request.writeBytes(this.crlf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        //Convert Bitmap to ByteBuffer:
//        //I want to send only 8 bit black & white bitmaps
//        byte[] pixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
//        for (int i = 0; i < bitmap.getWidth(); ++i) {
//            for (int j = 0; j < bitmap.getHeight(); ++j) {
//                //we're interested only in the MSB of the first byte,
//                //since the other 3 bytes are identical for B&W images
//                pixels[i + j] = (byte) ((bitmap.getPixel(i, j) & 0x80) >> 7);
//            }
//        }
//        try {
//            request.write(pixels);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //End content wrapper
//        try {
//            request.writeBytes(this.crlf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            request.writeBytes(this.twoHyphens + this.boundary +
//                    this.twoHyphens + this.crlf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Flush output buffer:
//        try {
//            request.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            request.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Get response:
//        InputStream responseStream = null;
//        try {
//            responseStream = new
//                    BufferedInputStream(httpUrlConnection.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BufferedReader responseStreamReader =
//                new BufferedReader(new InputStreamReader(responseStream));
//        String line = "";
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            while ((line = responseStreamReader.readLine()) != null) {
//                stringBuilder.append(line).append("\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            responseStreamReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String response = stringBuilder.toString();
//
//        //Close response stream:
//        try {
//            responseStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //Close the connection:
//        httpUrlConnection.disconnect();
//    }//end sendMessage method --------------------------------

}//end MainActivity class---------------------------------------------------------------------------
