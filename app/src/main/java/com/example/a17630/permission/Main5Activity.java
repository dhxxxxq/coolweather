package com.example.a17630.permission;

import android.Manifest;
import android.content.ContentProviderClient;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Main5Activity extends AppCompatActivity implements View.OnClickListener {
    
    private Button play,pause,stop;
    private MediaPlayer mediaPlayer=new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        play=findViewById(R.id.play);
        pause=findViewById(R.id.pause);
        stop=findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(Main5Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Main5Activity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            initMediaPlayer();//初始化MediaPlayer
        }
        
    }

    public void onRequestPermissionsResult(int requestCode,String[] permission,int[] grantResults){

        switch (requestCode){

            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    initMediaPlayer();
                }
                else {
                    Toast.makeText(Main5Activity.this,"权限拒绝将无法使用程序",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }

    }
    private void initMediaPlayer() {
        try{
            File file=new File(Environment.getExternalStorageDirectory(),"Music");
            mediaPlayer.setDataSource(file.getPath());//获取指定文件的路径
            mediaPlayer.prepare();//让MediaPlayer进入准备状态

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }

            break;
            case R.id.pause:

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }

                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }

                break;
                default:
                    break;
        }
        
    }
    protected void onDestroy() {

        super.onDestroy();

        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
