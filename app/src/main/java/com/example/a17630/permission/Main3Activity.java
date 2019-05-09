package com.example.a17630.permission;




import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.bmob.v3.helper.NotificationCompat;
public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    private Button sendNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        sendNotice=findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);

        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
       // Notification.Builder builder=new Notification.Builder(getApplicationContext());
       // builder.setContenTitle("this is content title");
        //Notification notification=new NotificationCompat.Builder(this,"default")
               // .setContenTitle("this is content title")
               // .setContentText("This is content text")
              //  .setWhen(System.currentTimeMillis())
              //  .setSmallIcon(R.mipmap.ic_launcher)
              //  .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
             //   .setContentIntent(pi)
             //   .build();
       // manager.notify(1,notification);

    }
}
