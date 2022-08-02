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

public class addfeedback extends AppCompatActivity {
EditText ed;
Button b3;
String fb="";
    String url="";
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfeedback);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ed=findViewById(R.id.editTextTextPersonName2);
        b3=findViewById(R.id.button3);

        url="http://"+sh.getString("ip","")+":5000/addfeedback";


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fb=ed.getText().toString();

                if (fb.equalsIgnoreCase("")) {
                    ed.setError("Enter Your feedback");
                }
                 else {


                        RequestQueue queue = Volley.newRequestQueue(addfeedback.this);

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.

                                try {
                                    JSONObject jo = new JSONObject(response);
                                    String status = jo.getString("task");

                                    Toast.makeText(addfeedback.this, status, Toast.LENGTH_SHORT).show();

                                    if (status.equalsIgnoreCase("success")) {


                                        Toast.makeText(addfeedback.this, "success", Toast.LENGTH_SHORT).show();

                                        Intent
                                                in = new Intent(getApplicationContext(), parenthome.class);
                                        startActivity(in);
//


                                    } else {
                                        Toast.makeText(addfeedback.this, "Failed", Toast.LENGTH_SHORT).show();

                                    }


                                } catch (Exception e) {
                                    Log.d("=========", e.toString());
                                    Toast.makeText(addfeedback.this, "" + e, Toast.LENGTH_SHORT).show();

                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(addfeedback.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("feedback", fb);
                                params.put("lid", sh.getString("lid", ""));

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