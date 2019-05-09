package com.example.a17630.permission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main9Activity extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        button=findViewById(R.id.button9);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url("http://www.baidu.com").build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseXMLWithPull(responseData);

                }catch (IOException e){
                    e.printStackTrace();
                }

            }

            private void parseXMLWithPull(String responseData) {

                try{
                    JSONArray jsonArray=new JSONArray(responseData);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String id=jsonObject.getString("id");
                        String name=jsonObject.getString("name");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
