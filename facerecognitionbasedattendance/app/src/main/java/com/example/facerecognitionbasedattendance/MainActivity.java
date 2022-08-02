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

public class MainActivity extends AppCompatActivity {
EditText ed1,pw1;
String uname,passwd;
Button b,b2;
    String url="";
    String ip="";
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1=findViewById(R.id.editTextTextPersonName);
        pw1=findViewById(R.id.editTextTextPassword);
        b=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sh.getString("ip","");

        url="http://"+sh.getString("ip","")+":5000/logincode";


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uname=ed1.getText().toString();
                passwd=pw1.getText().toString();

                if (uname.equalsIgnoreCase("")) {
                    ed1.setError("Enter Your uname");
                } else if (passwd.equalsIgnoreCase("")) {
                    pw1.setError("Enter Your passwd");
                }
                else {


                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.

                            try {
                                JSONObject jo = new JSONObject(response);
                                String status = jo.getString("task");
                                if (!status.equalsIgnoreCase("invalid")) {

                                    String type = jo.getString("type");

                                    Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
                                    if (type.equalsIgnoreCase("parent")) {
                                        String lid = jo.getString("id");

                                        SharedPreferences.Editor edt = sh.edit();
                                        edt.putString("lid", lid);
                                        edt.commit();
                                        Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(getApplicationContext(), parenthome.class);
                                        startActivity(in);
                                    } else if (type.equals("student")) {
                                        String lid = jo.getString("id");
                                        SharedPreferences.Editor edt = sh.edit();
                                        edt.putString("lid", lid);
                                        edt.commit();
                                        Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(getApplicationContext(), studenthome.class);
                                        startActivity(in);

                                    }
                                }

                                else {
                                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                                }




                            } catch (Exception e) {
                                Log.d("=========", e.toString());
                                Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("uname", uname);
                            params.put("password", passwd);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }





            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),parentregistration.class);
                startActivity(i);


            }


        });

    }
}