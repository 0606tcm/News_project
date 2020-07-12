package com.example.tcm.news.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tcm.news.Activity.notificat;
import com.example.tcm.news.Activity.user_zh;
import com.example.tcm.news.R;
import com.example.tcm.news.Utils.FileCacheUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_04 extends Fragment implements View.OnClickListener{

    private Button f04,f05,f06;
    public onControlSend2Activity controlSend2Activity;
    public static List<Activity>activityList = new LinkedList<>();
    final String Tag="a";
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
        View view =inflater.inflate(R.layout.page_04,container,false);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        f05 =(Button) getActivity().findViewById(R.id.f05);
        f04 =(Button) getActivity().findViewById(R.id.f04);
        f06 = (Button) getActivity().findViewById(R.id.f06);
        f04.setOnClickListener(this);
        f05.setOnClickListener(this);
        f06.setOnClickListener(this);
  }
  @Override
  public void onClick(View v){
        switch(v.getId()){
            case R.id.f04:
                Intent intent = new Intent(getActivity(),notificat.class);
                startActivity(intent);
                break;
            case R.id.f05:
                exit();
                break;
            case R.id.f06:
                Intent intent1 = new Intent(getActivity(),user_zh.class);
                Log.i(Tag,"dsdsad");
                startActivity(intent1);


                break;
        }
  }
    //接口
    public interface  onControlSend2Activity
    {
        public void receiveDataFromControl_04(String content,int whichFragment);
    }
    //接口监听
    public void setOnControlSend2ActivityListener(onControlSend2Activity on){
        this.controlSend2Activity =on;
    }
    public Fragment_04() {
        // Required empty public constructor
    }
    //结束进程
     public void exit(){
        for(Activity act:activityList)
        {

            act.finish();
        }
        System.exit(0);
    }
    //清除缓存





}