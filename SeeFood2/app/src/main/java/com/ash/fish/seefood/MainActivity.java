package com.ash.fish.seefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    int i = 0;
    Button myButton;
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
                }


            }
        });
    }
}
