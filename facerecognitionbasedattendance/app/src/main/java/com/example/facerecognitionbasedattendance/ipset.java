package com.example.facerecognitionbasedattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ipset extends AppCompatActivity {
EditText ed;
Button b1;
String ip="";
SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);

        ed=findViewById(R.id.editTextTextPersonName4);
        b1=findViewById(R.id.button18);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip=ed.getText().toString();

                if (ip.equalsIgnoreCase("")) {
                    ed.setError("Enter Your IP address");
                }
                else
                {
                    SharedPreferences.Editor ed=sp.edit();
                    ed.putString("ip",ip);
                    ed.commit();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }


            }

        });

    }
}