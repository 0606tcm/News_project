package com.example.tcm.news.Activity;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcm.news.Adapter.TabFragmentPageAdapter;
import com.example.tcm.news.Fragment.Fragment_01;
import com.example.tcm.news.Fragment.Fragment_02;
import com.example.tcm.news.Fragment.Fragment_03;
import com.example.tcm.news.Fragment.Fragment_04;
import com.example.tcm.news.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        Fragment_01.onControlSend2Activity,
        Fragment_02.onControlSend2Activity,
        Fragment_03.onControlSend2Activity,
        Fragment_04.onControlSend2Activity,
        TabFragmentPageAdapter .OnFragmentChangedListener
{
    //通知管理器,用于管理Notigfication对象
//    NotificationManager manager;
    private ViewPager myViewPAger;
    private TabFragmentPageAdapter adapter;
    private List<Fragment> list;
    //菜单按钮
    private Button item_button;
    //底部菜单4个LinearLayout
    private LinearLayout ll_home;
    private LinearLayout ll_friend;
    private LinearLayout ll_address;
    private LinearLayout ll_setting;
    //底部菜单4个ImagView
    private ImageView iv_home;
    private ImageView iv_address;
    private ImageView iv_friend;
    private ImageView iv_setting;
    //底部菜单4个菜单标题
    private TextView tv_home;
    private TextView tv_address;
    private TextView tv_friend;
    private TextView tv_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       initView();
    }
    private void initView() {
        //底部菜单4个LinearLayout
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_friend = (LinearLayout) findViewById(R.id.ll_friend);
        ll_setting = (LinearLayout) findViewById(R.id.ll_setting);
        //底部菜单4个ImageView
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_address = (ImageView) findViewById(R.id.iv_address);
        iv_friend = (ImageView) findViewById(R.id.iv_friend);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);
        //底部菜单四个菜单标题
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_friend = (TextView) findViewById(R.id.tv_friend);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        //中间内容区域ViewPager
//        this.viewpager = (ViewPager)findViewById(R.id.vp_content); -02
        myViewPAger = (ViewPager) findViewById(R.id.vp_content);
        //绑定点击事件
        //setOnOageChangedListener方法过时了,需要使用addOnPageChangedListener代替
        myViewPAger.addOnPageChangeListener(new MyPagerChangeListener());
        list = new ArrayList<>();
        list.add(new Fragment_01());
        list.add(new Fragment_02());
        list.add(new Fragment_03());
        list.add(new Fragment_04());
        adapter = new TabFragmentPageAdapter(getSupportFragmentManager(), list);
        myViewPAger.setAdapter(adapter);
        myViewPAger.setCurrentItem(0);//初始化显示第一个页面
        adapter.setOnFragmentChangedListener(this);

    }
//设置一个ViewPage的监听事件,当左右滑动ViewPager时菜单栏被选中状态跟着改变

    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    tv_home.setTextColor(Color.GREEN  );
                    tv_friend.setTextColor(Color.BLACK );
                    tv_address.setTextColor(Color.BLACK );
                    tv_setting.setTextColor(Color.BLACK );
                    iv_home.setImageResource(R.drawable.qq);
                    iv_address.setImageResource(R.drawable.notice);
                    iv_friend.setImageResource(R.drawable.notice);
                    iv_setting.setImageResource(R.drawable.notice);
                    break;
                case 1:
                    tv_home.setTextColor(Color.BLACK  );
                    tv_friend.setTextColor(Color.BLACK );
                    tv_address.setTextColor(Color.GREEN );
                    tv_setting.setTextColor(Color.BLACK );
                    iv_home.setImageResource(R.drawable.notice);
                    iv_address.setImageResource(R.drawable.wechat);
                    iv_friend.setImageResource(R.drawable.notice);
                    iv_setting.setImageResource(R.drawable.notice);
                    break;
                case 2:
                    tv_home.setTextColor(Color.BLACK  );
                    tv_friend.setTextColor(Color.GREEN );
                    tv_address.setTextColor(Color.BLACK );
                    tv_setting.setTextColor(Color.BLACK );
                    iv_home.setImageResource(R.drawable.notice);
                    iv_address.setImageResource(R.drawable.notice);
                    iv_friend.setImageResource(R.drawable.weibo);
                    iv_setting.setImageResource(R.drawable.notice);
                case 3:
                    tv_home.setTextColor(Color.BLACK  );
                    tv_friend.setTextColor(Color.BLACK );
                    tv_address.setTextColor(Color.BLACK );
                    tv_setting.setTextColor(Color.GREEN );
                    iv_home.setImageResource(R.drawable.notice);
                    iv_address.setImageResource(R.drawable.notice);
                    iv_friend.setImageResource(R.drawable.notice);
                    iv_setting.setImageResource(R.drawable.setting);


            }

        }

    }

    //监听当前的Fragment,并且初始化
    @Override
    public void onFragmentChanged(int pos) {
        switch (pos) {
            case 0:
                Fragment_01 fc1 = (Fragment_01) ((TabFragmentPageAdapter) myViewPAger.getAdapter()).currentFragment;
                if (fc1 == null) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "未获取到当前的Fragment" + String.valueOf(pos), Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                } else {
                    fc1.setOnControlSend2ActivityListener(this);}
                break;
            case 1:
                Fragment_02 fc2 = (Fragment_02) ((TabFragmentPageAdapter) myViewPAger.getAdapter()).currentFragment;
                if (fc2 == null) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "未获取到当前的Fragment" + String.valueOf(pos), Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                } else {
                    fc2.setOnControlSend2ActivityListener(this);}
                break;
            case 2:
                Fragment_03 fc3 = (Fragment_03) ((TabFragmentPageAdapter) myViewPAger.getAdapter()).currentFragment;
                if (fc3 == null) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "未获取到当前的Fragment" + String.valueOf(pos), Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                } else {
                    fc3.setOnControlSend2ActivityListener(this);}
                break;
            case 3:
                Fragment_04 fc4 = (Fragment_04) ((TabFragmentPageAdapter) myViewPAger.getAdapter()).currentFragment;
                if (fc4 == null) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "未获取到当前的Fragment" + String.valueOf(pos), Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                } else {
                    fc4.setOnControlSend2ActivityListener(this);}
                break;}}
    //监听来自Fragment_01数据
    @Override
    public void receiveDataFromControl(String content, int whichFragment) {
        Toast toast1 = Toast.makeText(getApplicationContext(),
                content, Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }

    //监听来自Fragment_02数据
    @Override
    public void receiveDataFromControl_02(String content, int whichFragment) {
        Toast toast1 = Toast.makeText(getApplicationContext(),
                content, Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }

    //监听来自Fragment_03数据
    @Override
    public void receiveDataFromControl_03(String content, int whichFragment) {
        Toast toast1 = Toast.makeText(getApplicationContext(),
                content, Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }

    //监听来自Fragment_04数据
    @Override
    public void receiveDataFromControl_04(String content, int whichFragment) {
        Toast toast1 = Toast.makeText(getApplicationContext(),
                content, Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }



}
