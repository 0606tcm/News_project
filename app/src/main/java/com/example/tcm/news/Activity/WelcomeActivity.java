package com.example.tcm.news.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tcm.news.R;
import com.example.tcm.news.Utils.ApplicationUtil;
import com.example.tcm.news.Utils.SharedPreUtil;

/**
 * Created by tcm on 2020/6/12.
 *功能1:实现handler机制展示欢迎界面,3秒后实现跳转,跳转至登录或者注册界面
 *功能2:在注册和登录界面实现界面跳转
 *功能3:使用SharedPreUtil工具类,进行用户登录状态存储
 *
 */

public class WelcomeActivity extends AppCompatActivity{
    private Button btn_jump;
    final Handler handler= new Handler(){

        @Override
        public  void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what == 1){
                //判断用户是否登录
                boolean userIsLogin  =(boolean) SharedPreUtil.getParam(WelcomeActivity.this,SharedPreUtil.IS_LOGIN,false);
                if(userIsLogin){
                    Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(WelcomeActivity.this,LoginOrRegisterActivity.class);
                }
                    finish();
            }else if(msg.what == 0){
                thread.interrupt();
            }}
    };
    //子线程,等待3秒
    final Message message = new Message();
    final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                message.what = 1;
                handler.sendMessage(message);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    });


    @Override
    protected void onCreate(Bundle saveInstanceState){

       super.onCreate(saveInstanceState);
       setContentView(R.layout.activity_welcome);
       //启动线程
       thread.start();
        btn_jump =(Button)findViewById(R.id.btn_jump);
        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.what = 0;
                handler.sendMessage(message);
                //判断用户是否登录
                boolean userIsLogin =(boolean) SharedPreUtil.getParam(WelcomeActivity.this,SharedPreUtil.IS_LOGIN
                ,false);
                if(userIsLogin){
                    Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    //隐式Intent
                    Intent intent =new  Intent("android.intent.action.CALL");
                    startActivity(intent);
                }
                finish();
            }
        });
        ApplicationUtil.getInstance().addActivity(this);
    }

}
