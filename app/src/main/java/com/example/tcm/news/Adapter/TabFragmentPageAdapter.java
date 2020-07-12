package com.example.tcm.news.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.support.v4.app.FragmentManager;

import com.example.tcm.news.R;

import java.util.List;

public class TabFragmentPageAdapter extends FragmentPagerAdapter{
    //存放fragment的集合
    private  FragmentManager mfragmentManager;
    private List<Fragment> mlist;
    public Fragment currentFragment;
    private OnFragmentChangedListener onFragmentChangedListener ;

    /**
    * 定义回调接口,我们监听setPrimaryItem()中的position,就是要切换的fragment
    * */
    public interface OnFragmentChangedListener{

        public void onFragmentChanged(int pos);
    }
    /**
     * 设置监听函数
     * */
    public void setOnFragmentChangedListener (OnFragmentChangedListener onFragmentChangedListener )
    {
        this.onFragmentChangedListener =onFragmentChangedListener ;
    }



    public TabFragmentPageAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.mlist = list;
    }

    @Override
    public void setPrimaryItem(ViewGroup container,int position, Object object){
        this.currentFragment= (Fragment) object;
        onFragmentChangedListener .onFragmentChanged(position ) ;//触发监听，将position传递出去
        super.setPrimaryItem(container, position, object);
    }


    @Override
    public Fragment getItem(int arg0) {
        return mlist.get(arg0);//显示第几个页面
    }

    @Override
    public int getCount() {
        return mlist.size();//有几个页面
    }
}
