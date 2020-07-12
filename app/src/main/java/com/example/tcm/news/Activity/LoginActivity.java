package com.example.tcm.news.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tcm.news.R;

import com.example.tcm.news.Utils.AlbumUtil;
import com.example.tcm.news.Utils.ApplicationUtil;
import com.example.tcm.news.Utils.SharedPreUtil;
import com.example.tcm.news.Utils.TimeCount;

import java.io.File;
import java.io.FileInputStream;
/**
 * Created by tcm on 2020/6/12.
 */
public class LoginActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private Button check_user;
    private EditText username,userpassword;
    private ImageView login_head;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new MyDatabaseHelper(this,"UserDB.db",null,1);
        check_user = (Button)findViewById(R.id.check_user);
        username = (EditText)findViewById(R.id.login_username);
        userpassword = (EditText)findViewById(R.id.login_password);
        login_head = (ImageView)findViewById(R.id.login_head);
//登录界面是显示头像,若
        try{
            String path = getCacheDir().getPath();
            String fileName ="user_head";
            File file = new File(path,fileName);
            if(file.exists()){
                //从文件中读取
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                //使用AlumUtil将图像转换成圆形
                Bitmap round = AlbumUtil.toRoundBitmap(bitmap);
                login_head.setImageBitmap(round);
            }else{
                login_head.setImageBitmap((BitmapFactory.decodeResource(getResources(),R.drawable.head)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        check_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String username_str = username.getText().toString();
                String userpassword_str =userpassword.getText().toString();
                Cursor cursor =db.rawQuery("select * from User where name='"+username_str+"'",null);
                if(cursor.getCount() == 0){
                    Toast.makeText(LoginActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                }else{
                    if(cursor.moveToFirst()){
                        String userpassword_db = cursor.getString(cursor.getColumnIndex("password"));
                        if(userpassword_str.equals(userpassword_db)){
                            //存储用户信息,是否登录
                            SharedPreUtil.setParam(LoginActivity.this,SharedPreUtil.IS_LOGIN,true);
                            //存储用户信息,登录名
                            SharedPreUtil.setParam(LoginActivity.this,SharedPreUtil.LOGIN_DATA,username_str);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            //设置登录时间
                            TimeCount.getInstance().setTime(System.currentTimeMillis());
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"密码错误,请重新输入",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                cursor.close();
                db.close();
            }
        });
        ApplicationUtil.getInstance().addActivity(this);
    }
}