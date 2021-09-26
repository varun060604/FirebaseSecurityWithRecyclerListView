package com.varun.firebasesecuritywithlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendData extends AppCompatActivity {

    EditText name,contact,city;
    Button send;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        name = findViewById(R.id.sName);
        contact = findViewById(R.id.sContact);
        city = findViewById(R.id.sCity);

        send = findViewById(R.id.sendDetails);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");

        send.setOnClickListener(view -> {
            insertData();
        });
    }

    private void insertData() {

        String sName = name.getText().toString();
        String sContact = contact.getText().toString();
        String sCity = city.getText().toString();

        if (sName.isEmpty() || sContact.isEmpty() || sCity.isEmpty()){
            Toast.makeText(SendData.this, "Enter Details", Toast.LENGTH_SHORT).show();
        }
        else {
            Students students = new Students(sName, sContact, sCity);
            databaseReference.push().setValue(students);
            Toast.makeText(SendData.this, "Data Inserted", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SendData.this,MainActivity.class));
        }
    }
}