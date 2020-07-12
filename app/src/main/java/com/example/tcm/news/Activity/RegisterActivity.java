package com.example.tcm.news.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcm.news.R;
import com.example.tcm.news.Utils.AlbumUtil;
import com.example.tcm.news.Utils.ApplicationUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by tcm on 2020/6/12.
 * 功能:给用户提供注册,用户输入图像,账号,密码
 * 主要控件有三个editText,一个ImageView,一个CheckBox,一个TextView
 */

public class RegisterActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private TextView save_user;
    private ImageView shangchuan_head;
    private EditText username,userpassword,repassword;
    private CheckBox checkBox;

    private static final int CHOSSE_PHOTO = 1;

    //实现按钮的点击事件
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper =new MyDatabaseHelper(this,"UserDB.db",null,1);
        save_user = (TextView) findViewById(R.id.save_user);
        shangchuan_head = (ImageView)findViewById(R.id.shangchuan_head);
        username = (EditText)findViewById(R.id.register_username);
        userpassword = (EditText)findViewById(R.id.register_password);
        repassword = (EditText)findViewById(R.id.register_repassword);
        checkBox = (CheckBox)findViewById(R.id.checkbox_tiaokuan);
        shangchuan_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(RegisterActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(RegisterActivity.this,
                          new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
            }
        });
//用户勾选同意条款后,实现用户账号注册,
        save_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String username_str = username.getText().toString();
                    String userpassword_str = userpassword.getText().toString();
                    String repassword_str = repassword.getText().toString();
                    if(userpassword_str.equals(repassword_str)){
                        ContentValues values = new ContentValues();
                        //组装数据
                        values.put("name",username_str);
                        values.put("password",userpassword_str);
                        db.insert("User",null,values);
                        Toast.makeText(RegisterActivity.this,"name:"+username_str,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,"两次密码输入不正确,请重新输入",Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }else{

                    Toast.makeText(RegisterActivity.this,"请勾选News使用条款",Toast.LENGTH_SHORT);
                }
            }
        });
        ApplicationUtil.getInstance().addActivity(this);
    }

    //系统限权的获取
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permission,int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    //打开相册,选择图片
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOSSE_PHOTO);
    }

    @RequiresApi (api = Build.VERSION_CODES.KITKAT)

    //得到响应结果

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case CHOSSE_PHOTO:
                if(resultCode == -1){
                    //调用头像设置(存储)函数
                    String imgPath = AlbumUtil.handleImageOnKitKat(this,data);
                    setHead(imgPath);
                }
                break;
            default:
                break;
        }
    }

    //账号信息(账号密码,和头像)的存储
    private  void setHead(String imgPath){
        if(imgPath != null){
            //从文件中读取
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            //使用AlumTtil的toRoundBitmap将bitmap转化为圆形头像
            Bitmap round = AlbumUtil.toRoundBitmap(bitmap);
            try{
                String path =getCacheDir().getPath();
                File file = new File(path ,"user_head");
                round.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            shangchuan_head.setImageBitmap(round);

        }else{
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();


        }

    }


}

