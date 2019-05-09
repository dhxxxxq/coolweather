package com.example.a17630.permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

public class Main6Activity extends AppCompatActivity implements View.OnClickListener {

    private Button play,pause,replay;
    private VideoView videoView;
    private MediaPlayer mediaPlayer=new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        play=findViewById(R.id.play);
        pause=findViewById(R.id.pause);
        replay=findViewById(R.id.replay);
        videoView=findViewById(R.id.videoView);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(Main6Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(Main6Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:

                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.replay:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;
                default:
                    break;
        }


    }
    public void onRequestPermissionsResult(int requestCode,String[] permission,int[] grantResult){
        switch (requestCode){
            case 1:
                if(grantResult.length>0 && grantResult[0]==PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
                }
                break;
                default:
                    break;
        }
    }

    private void initVideoPath() {
        File file=new File(Environment.getExternalStorageDirectory(),"movie.mp4");
        videoView.setVideoPath(file.getPath());
    }
    protected void onDestroy() {

        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }
}
