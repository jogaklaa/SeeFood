package com.ash.fish.seefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    int i = 0;
    Button myButton;
    ImageButton foodButton;

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
            }//end onClick
        });

        foodButton=(ImageButton)findViewById(R.id.imageButton);
        foodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Hello!!!!!!!!!");
            }
        });
    }//end onCreate


}
