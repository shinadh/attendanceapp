package com.example.facerecognitionbasedattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class parentregistration extends AppCompatActivity {
    EditText ed3,ed8,ed5,ed6,ed7,pw;
    String fname,lname,admno,contno,uname,passwd;
    Button b5;
        String url="";
        SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentregistration);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed3=findViewById(R.id.editTextTextPersonName3);
        ed8=findViewById(R.id.editTextTextPersonName8);
        ed5=findViewById(R.id.editTextTextPersonName5);
        ed6=findViewById(R.id.editTextTextPersonName6);
        ed7=findViewById(R.id.editTextTextPersonName7);
        pw=findViewById(R.id.editTextTextPassword3);
        b5=findViewById(R.id.button5);

        url="http://"+sh.getString("ip","")+":5000/addparent";

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                fname=ed3.getText().toString();
                lname=ed8.getText().toString();
                admno=ed5.getText().toString();
                contno=ed6.getText().toString();
                uname=ed7.getText().toString();
                passwd=pw.getText().toString();

                if (fname.equalsIgnoreCase("")) {
                    ed3.setError("Enter Your First Name");
                }
                else if(!fname.matches("^[a-z A-Z]*$"))
                {
                    ed3.setError("Only characters allowed");
                }
                else if (lname.equalsIgnoreCase("")) {
                    ed8.setError("Enter Your Last Name");
                }
                else if(!lname.matches("^[a-zA-Z]*$"))
                {
                    ed8.setError("Only characters allowed");
                }
                else if (admno.equalsIgnoreCase("")) {
                    ed5.setError("Enter Your admission no");
                }
                else if(!admno.matches("^[a-zA-Z0-9]*$"))
                {
                    ed5.setError("characters allowed");
                }
                else if (contno.equalsIgnoreCase("")) {
                    ed6.setError("Enter Your contact no");
                }
                else if(contno.length()!=10)
                {
                    ed6.setError("Invalid phoneno");
                }
                else if (uname.equalsIgnoreCase("")) {
                    ed7.setError("Enter your username");
                }
                else if(!uname.matches("^[a-zA-Z0-9]*$"))
                {
                    ed7.setError("characters allowed");
                }
                else if (passwd.equalsIgnoreCase("")) {
                    pw.setError("Enter Your Password");}
                else {


                    RequestQueue queue = Volley.newRequestQueue(parentregistration.this);

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.

                            try {
                                JSONObject jo = new JSONObject(response);

                                String result = jo.getString("task");


                                if (result.equalsIgnoreCase("success")) {

                                    Toast.makeText(parentregistration.this, "registration success", Toast.LENGTH_SHORT).show();

                                    Intent
                                            in = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(in);
//                                    Intent i = new Intent(getApplicationContext(), LocationService.class);
//                                    startService(i);


                                } else {
                                    Toast.makeText(parentregistration.this, "registration failed", Toast.LENGTH_SHORT).show();

                                }


                            } catch (Exception e) {
                                Log.d("=========", e.toString());
                                Toast.makeText(parentregistration.this, "" + e, Toast.LENGTH_SHORT).show();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(parentregistration.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("fname", fname);
                            params.put("lname", lname);
                            params.put("admno", admno);
                            params.put("contactno", contno);
                            params.put("username", uname);
                            params.put("password", passwd);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }





            }
        });

    }
}