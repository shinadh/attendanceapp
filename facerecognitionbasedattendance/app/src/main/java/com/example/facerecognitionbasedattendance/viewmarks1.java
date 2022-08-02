package com.example.facerecognitionbasedattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewmarks1 extends AppCompatActivity {

    ArrayList<String> subject,marks;
    EditText ed17,ed18;
    ListView lv;
    String url="";
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmarks);
        lv=findViewById(R.id.lv1);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url="http://"+sh.getString("ip","")+":5000/viewmarkparent";

        RequestQueue queue = Volley.newRequestQueue(viewmarks1.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    subject= new ArrayList<>();
                    marks= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        subject.add(jo.getString("subject"));
                        marks.add(jo.getString("mark"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lv.setAdapter(new custom2(viewmarks1.this,subject,marks));



                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewmarks1.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);
    }
}