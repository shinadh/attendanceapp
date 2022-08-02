package com.example.facerecognitionbasedattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class studenthome extends AppCompatActivity {
Button b8,b9,b10,b11,b14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studenthome);
        b8=findViewById(R.id.button8);
        b9=findViewById(R.id.button9);
        b10=findViewById(R.id.button10);
        b11=findViewById(R.id.button11);
        b14=findViewById(R.id.button14);

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ViewAttendance.class);
                startActivity(i);

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),viewmarks.class);
                startActivity(i);

            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),addfeedback.class);
                startActivity(i);

            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),performanceprediction.class);
                startActivity(i);

            }
        });
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
        });

    }
}