package com.example.facerecognitionbasedattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EdgeEffect;
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

public class ViewAttendance extends AppCompatActivity {
    ArrayList<String> studentname,twd,tpd,per;
    EditText ed13,ed14;
    ListView lv;
        String url="";
        SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);


        lv=findViewById(R.id.lv1);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url="http://"+sh.getString("ip","")+":5000/viewattendance";

        RequestQueue queue = Volley.newRequestQueue(ViewAttendance.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    studentname= new ArrayList<>();
                    twd= new ArrayList<>();
                    tpd= new ArrayList<>();
                    per= new ArrayList<>();



                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        studentname.add(jo.getString("first_name")+jo.getString("last_name"));
                        twd.add(jo.getString("twd"));
                        tpd.add(jo.getString("tpd"));
                        per.add(jo.getString("p"));




                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lv.setAdapter(new custom3(ViewAttendance.this,studentname,twd,tpd,per));



                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ViewAttendance.this, "err"+error, Toast.LENGTH_SHORT).show();
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