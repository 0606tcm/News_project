package com.example.tcm.news.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcm.news.R;

/**
 * Created by tcm on 2020/6/23.
 */

public class user_zh extends AppCompatActivity implements View.OnClickListener {
    public MyDatabaseHelper dbHelper;
    private EditText edit_user,edit_password,updata_password,user_1,pw_1;
    private  Button new_add,new_updata,new_delete;
    private TextView contacts_view;
    final String Tag ="123";
    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.user_zh);
        edit_user =(EditText)findViewById(R.id.edit_uesr);
        edit_password =(EditText)findViewById(R.id.edit_password);
        updata_password =(EditText)findViewById(R.id.updata_password);
        contacts_view =(TextView)findViewById(R.id.contacts_view);
        new_delete =(Button)findViewById(R.id.new_delete);
        user_1 = (EditText) findViewById(R.id.user_1);
        pw_1 =(EditText)findViewById(R.id.pw_1);
        new_add = (Button)findViewById(R.id.new_add);
        new_updata=(Button)findViewById(R.id.new_updata);
        Button updateData =(Button)findViewById(R.id.updata);

        updateData.setOnClickListener(this);
        new_delete.setOnClickListener(this);
        new_add.setOnClickListener(this);
        new_updata.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        dbHelper.getWritableDatabase();
        switch (v.getId()){
            //修改账号密码,实现修改数据操作
            case R.id.updata:
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String username1 = edit_user.getText().toString();//获取输入的账号
                String password1 = edit_password.getText().toString();//获取输入的密码
                String updata_password1 = updata_password.getText().toString();
                //使用cursor进行遍历,搜索目标账号
                Cursor cursor = db.rawQuery("select *from User where name="+username1+"",null);
                if(cursor.getCount() ==0){
                    Toast.makeText(user_zh.this,"用户信息错误,请重新输入", Toast.LENGTH_SHORT).show();
                }else{
                    if(cursor.moveToFirst()){
                       //搜索目标账号的密码
                        String userpassword_db = cursor.getString(cursor.getColumnIndex("password"));
                       //对比目标账号密码和输入密码是否一致
                        if(password1.equals(userpassword_db)){
                            ContentValues values = new ContentValues();
                            values.put("password",updata_password1);
                            //更新name=username1的数据,将其password改为updata_password
                            db.update("User",values,"name=?",new String[]{username1});
                            Toast.makeText(user_zh.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(user_zh.this,"密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                cursor.close();//关闭游标,关闭数据库
                db.close();
                break;
            case R.id.flash:
                SQLiteDatabase db1 =  dbHelper.getWritableDatabase();
                Cursor cursor1 = db1.query("User",null,null,null,null,null,null);
                StringBuilder content = new StringBuilder();
                content.append("user"+"\t\t\t"+"passsword"+"\n");
                if(cursor1.moveToFirst()){
                    do{
                        String u1=cursor1.getString(cursor1.getColumnIndex("user"));
                        String p1=cursor1.getString(cursor1.getColumnIndex("password"));
                    }while(cursor1.moveToNext());
                }
                cursor1.close();
                contacts_view.setText(content.toString());
                db1.close();
                break;

                //数据表的删除
            case R.id.new_delete:
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                String user_2 =user_1.getText().toString();
                //删除表User中,name=user_2的账号
                db2.delete("User","name=",new String[]{user_2});
                db2.close();
                break;

            case R.id.new_updata:
                SQLiteDatabase db3 = dbHelper.getWritableDatabase();
                String user_3 = user_1.getText().toString();
                String pw_3 = pw_1.getText().toString();
                ContentValues values = new ContentValues();
                values.put("password",pw_3);
                db3.update("User",values,"name=?",new String[]{user_3});
                db3.close();
                break;
                //数据表的增加
            case R.id.new_add:
                SQLiteDatabase db4 =dbHelper.getWritableDatabase();
                String user_4 = user_1.getText().toString();//获取输入editText的账号
                String pw_4 = pw_1.getText().toString();//获取输入editText的密码
                ContentValues values2 = new ContentValues();
                values2.put("name",user_4);//将账号,密码分别对应name和password存到values
                values2.put("password",pw_4);
                db4.insert("User",null,values2);//拆入到数据表中
                db4.close();//关闭数据表
                break;
        }
    }



}
