package com.example.a17630.permission;

import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener {

    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private ImageView picture;
    private Uri imageUri;
    private Button takePhoto,chooseFromAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        takePhoto=findViewById(R.id.take_picture);
        picture=findViewById(R.id.picture);
        takePhoto.setOnClickListener(this);
        chooseFromAlbum=findViewById(R.id.choose_from_album);
        chooseFromAlbum.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.take_picture:

        //创建File对象，用于存储拍照后的照片
        File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
        //getExternalCacheDir()得到缓存目录
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }

            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(Main4Activity.this,"com.example.cameraalbumtest.fileprovider",outputImage);

        }else {
            imageUri=Uri.fromFile(outputImage);

        }
        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
        break;
            case R.id.choose_from_album:
                if(ContextCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager
                        .PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Main4Activity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
                break;
                default:
                    break;
    }
    }

    private void openAlbum() {
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        switch (requestCode){
            case TAKE_PHOTO:
                try{

                    //将拍摄的照片显示出来，从资源中获取图片
                    Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    picture.setImageBitmap(bitmap);

                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    //判断手机的版型号
                    if(Build.VERSION.SDK_INT>=19){
                        //版型号4.4以上用这个方法处理
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
            default:
                break;
        }

    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){

            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.privider.media.documents".equals(uri.getAuthority())){

                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID +"=" +id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);

            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){

                Uri contentUri= ContentUris.withAppendedId(Uri.parse("conetent://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);

            }else if("content".equalsIgnoreCase(uri.getScheme())){
                //如果是content类型的URI则用普通方法处理
                imagePath=getImagePath(uri,null);

            }else if("file".equalsIgnoreCase(uri.getScheme())){
                //如果是file类型的URI，则直接获取图片路径即可
                imagePath=uri.getPath();

            }
            displayImage(imagePath);//根据图片路径显示图片

        }
    }

    private void displayImage(String imagePath) {
        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(Main4Activity.this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {

        String path=null;
        //通过URI与selection；来获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void handleImageBeforeKitKat(Intent data) {
        //获取图片的真实照片

        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);

    }

    public void onRequestPermissionsResult(int requestCode,String[] permission,int[] grantResults){

        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    openAlbum();
                }
                else {
                    Toast.makeText(Main4Activity.this,"you denied the permissions",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }

    }
}
