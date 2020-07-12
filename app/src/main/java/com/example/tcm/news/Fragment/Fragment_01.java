package com.example.tcm.news.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcm.news.Activity.MainActivity;
import com.example.tcm.news.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_01 extends Fragment implements  View.OnClickListener{
    private Button f01;
    public onControlSend2Activity controlSend2Activity;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
        View view =inflater.inflate(R.layout.page_01,container,false);
        f01 =(Button)view.findViewById(R.id.f01);
        f01.setOnClickListener(this);
        return view;
    }
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

    }


    //接口
    public interface  onControlSend2Activity
    {
        public void receiveDataFromControl(String content,int whichFragment);

    }
    //接口监听
    public void setOnControlSend2ActivityListener(onControlSend2Activity on){
        this.controlSend2Activity =on;
    }

    @Override
    public void onClick(View v){

        if(v.getId() == R.id.f01)
        {
            String content ="我来自Fragment_01";
            this.controlSend2Activity.receiveDataFromControl(content,0);


        }

    }
    }