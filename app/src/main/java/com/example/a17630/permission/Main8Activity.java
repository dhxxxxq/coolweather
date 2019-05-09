package com.example.a17630.permission;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main8Activity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        button=findViewById(R.id.button8);
        textView=findViewById(R.id.textView);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://www.baidu.com").build();
                    Response response = client.newCall(request).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);

                }catch (IOException e){
                    e.printStackTrace();
                }

            }

            private void showResponse(final String responseData) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseData);
                    }
                });
            }
        }).start();
    }
}
