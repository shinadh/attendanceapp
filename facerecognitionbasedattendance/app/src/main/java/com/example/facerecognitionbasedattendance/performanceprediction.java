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

public class performanceprediction extends AppCompatActivity {
    ArrayList<String> attendance,mark,avgs,grade,sub;
    ListView lv;
        String url="";
        SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performanceprediction);

        lv=findViewById(R.id.LV1);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url="http://"+sh.getString("ip","")+":5000/performanceprediction";

        RequestQueue queue = Volley.newRequestQueue(performanceprediction.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    attendance= new ArrayList<>();
                    mark= new ArrayList<>();
                    avgs= new ArrayList<>();
                    grade= new ArrayList<>();
                    sub= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        attendance.add(jo.getString("attendance"));
                        mark.add(jo.getString("mark"));
                        avgs.add(jo.getString("avgs"));
                        sub.add(jo.getString("subject"));



                        float avg=Float.parseFloat(jo.getString("avgs"))/2;
                        if(avg>90)
                        {
                            grade.add("A");
                        }
                        else if(avg>80)
                        {
                            grade.add("B");

                        }else if(avg>70)
                        {
                            grade.add("C");

                        }
                        else if(avg>60)
                        {
                            grade.add("D");

                        }
                        else if(avg>=50)
                        {
                            grade.add("E");

                        }
                        else
                        {
                            grade.add("F");
                        }


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lv.setAdapter(new custom2(performanceprediction.this,sub,grade));



                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(performanceprediction.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("studid",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);



    }
}