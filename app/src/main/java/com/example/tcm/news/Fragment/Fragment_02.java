package com.example.tcm.news.Fragment;


import android.Manifest;
import android.app.NotificationManager;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tcm.news.Activity.contentprovider;
import com.example.tcm.news.R;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容提供的getContentResolver有两种使用情况,一种在Activityheservice种使用,一种在没有activity种使用
 *
 *
 * A simple {@link Fragment} subclass.
 */
public class Fragment_02 extends Fragment implements  View.OnClickListener{
    private Button f02;
    public  onControlSend2Activity controlSend2Activity;
    private Context context;
    private contentprovider contentprovider;
    List<String> contactsList1 = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
        View view =inflater.inflate(R.layout.page_02,container,false);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        f02 =(Button)getActivity().findViewById(R.id.f02);
        f02.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.f02:
//                contentprovider.readContacts();



        }

    }



        //接口
    public interface  onControlSend2Activity
    {
        public void receiveDataFromControl_02(String content,int whichFragment);

    }
    //接口监听
    public void setOnControlSend2ActivityListener(onControlSend2Activity on){
        this.controlSend2Activity =on;
    }








    public  Fragment_02() {
        // Required empty public constructor
    }




}