package com.example.tcm.news.Activity;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tcm.news.R;

/**
 * Created by tcm on 2020/6/18.
 */

public class notificat extends AppCompatActivity implements View.OnClickListener {
    NotificationManager manager;
    private Button btn_1,btn_2;


    protected void onCreate(Bundle saveInatanceState){
        super.onCreate(saveInatanceState);
        setContentView(R.layout.item_1);

        btn_1 =(Button)findViewById(R.id.btn_1);
        btn_2 =(Button)findViewById(R.id.btn_2);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        //获得通知服务

        manager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


    }


    @Override
    public  void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                sendNormalNotification();
                break;
            case R.id.btn_2:
                manager.cancelAll();
                break;

        }
    }
    public void sendNormalNotification(){
        if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){//在这里判断系统版本是否大于8.0,大于创建一个Chann
            //构建通知渠道对象,每个通知都需要衣服于一个通知渠道,三个参数分别表示渠道的ID,渠道名称(用于可以根据名称选择是否允许弹出通知),通知的优先级
            NotificationChannel notificationChannel = new NotificationChannel("channel","channelname",NotificationManager.IMPORTANCE_HIGH);
            //通知管理器管理渠道,创建通知渠道
            manager.createNotificationChannel(notificationChannel);

        }
        Intent intent = new Intent(this,MainActivity.class);//构建Intent对象,用于活动条装
        PendingIntent pi = PendingIntent.getActivity(this,0, intent,0);
        Notification notification = new NotificationCompat.Builder(this,"channel")//创建通知对象,第二个参数表述通知渠道的id,用于将当前的通知和渠道进行绑定
                .setContentTitle("悟空")
                .setContentText("一起去找七龙珠吧")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.item5)

                .setContentIntent(pi)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.setting)))
                .build();
        manager.notify(1,notification);

    }

    }



