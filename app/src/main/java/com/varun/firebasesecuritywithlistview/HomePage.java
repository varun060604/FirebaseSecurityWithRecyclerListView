package com.varun.firebasesecuritywithlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    Button SendData,Retrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        SendData = findViewById(R.id.SendData);
        Retrieve = findViewById(R.id.RetrieveData);

        SendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HomePage.this, SendData.class);
                startActivity(intent1);
            }
        });

        Retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HomePage.this, Retrieve.class);
                startActivity(intent1);
            }
        });

    }
}