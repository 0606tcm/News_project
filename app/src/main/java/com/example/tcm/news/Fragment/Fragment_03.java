package com.example.tcm.news.Fragment;


import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Notification;
import android.support.v4.app.NotificationCompat;

import android.widget.Button;
import android.widget.TextView;


import com.example.tcm.news.Activity.musics;
import com.example.tcm.news.Activity.notificat;
import com.example.tcm.news.R;

/**

 *
 */
public class Fragment_03 extends Fragment {
    private Button btn_music;
    public onControlSend2Activity controlSend2Activity;
    final String TAG="dsds";






    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
        View view =inflater.inflate(R.layout.page_03,container,false);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle saveInstanceState)
    {
        super.onActivityCreated(saveInstanceState);
        btn_music = (Button)getActivity().findViewById(R.id.btn_music);
        btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_music:
                        Log.i(TAG,"到这里没错");
                        Intent intent = new Intent(getActivity(), musics.class);
                        startActivity(intent);

                    break;
                }
            }
        });

    }



    //接口
    public interface  onControlSend2Activity
    {
        public void receiveDataFromControl_03(String content,int whichFragment);

    }
    //接口监听
    public void setOnControlSend2ActivityListener(onControlSend2Activity on){
        this.controlSend2Activity =on;
    }



    public  Fragment_03() {
        // Required empty public constructor
    }




}