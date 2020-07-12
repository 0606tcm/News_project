package com.example.tcm.news.Activity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tcm.news.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by tcm on 2020/6/19.
 */
public class musics extends AppCompatActivity implements View.OnClickListener {
        //创建一个MediaPlayer对象
        MediaPlayer mediaPlayer = new MediaPlayer();
        int[] musicArrays=new int[]{R.raw.actor,R.raw.mo,R.raw.stupid};//数组用于存放音乐源文件id
        String[] Name=new String[]{"薛之谦 - 演员","那英 - 默","Tone Damli - Stupid"};//音乐名字
        static int index=0;//音乐索引
        TextView musicName;
        int currentPosition;//用于记录音乐当前位置
        int duration;//音乐时长
        TextView timeProgress;//用于显示音乐更新时间
        TextView allTime;//显示音乐总时长
        Handler mHandler;//网上搜索对于知识
        SeekBar seekBar;//进度条,可以拖动
        float speed=1.0f;//倍数
        EditText editSpeed;//输入倍数值控件
        @SuppressLint("HandlerLeak")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_music);
                musicName = (TextView) findViewById(R.id.musicName);
                timeProgress = (TextView) findViewById(R.id.timeProgress);
                allTime = (TextView) findViewById(R.id.allTime);
                seekBar = (SeekBar) findViewById(R.id.seekBar);
                Button preMusic = (Button) findViewById(R.id.pre);
                Button play = (Button) findViewById(R.id.play);
                Button pause = (Button) findViewById(R.id.pause);
                Button reset = (Button) findViewById(R.id.reset);
                Button nextMusic = (Button) findViewById(R.id.next);
                Button choiceSpeed = (Button) findViewById(R.id.speed);
                editSpeed = (EditText) findViewById(R.id.editSpeed);
                play.setOnClickListener(this);
                pause.setOnClickListener(this);
                reset.setOnClickListener(this);
                preMusic.setOnClickListener(this);
                nextMusic.setOnClickListener(this);
                choiceSpeed.setOnClickListener(this);
                if (ContextCompat.checkSelfPermission(musics.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(musics.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                } else {
                        initMediaPlayer(index);//初始化MediaPlayer

                }
                //进度条的拖拽
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {//停止拖动
                                int progress = seekBar.getProgress();
                                mediaPlayer.seekTo(progress);//定位音乐位置
                        }
                });

                Timer timer = new Timer();//定时器
                timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                                //(1)使用handler发送消息
                                Message message = new Message();
                                message.what = 0;
                                mHandler.sendMessage(message);
                        }
                }, 0, 1000);//每隔一秒使用handler发送一下消息,也就是每隔一秒执行一次,一值重复执行
                //(2)使用handler处理接收到的消息
                mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                                if (msg.what == 0) {
                                        updateProgress();//更新进度条进度和音乐播放的时间值
                                        /**
                                         *在这里写我们需要一直重复执行的代码
                                         *
                                         * **/

                                }

                        }

                };
        }
        //初始化媒体播放器对象
        private void initMediaPlayer(int index){
                try{
                        Uri uri =Uri.parse("android.resource://"+getPackageName()+"/"+musicArrays[index]);
                        mediaPlayer.setDataSource(musics.this,uri);//指定音频文件的路径
                        //File file =new File(Environment.getExternalStorageDirectory(),"music.mp3");
                        // mediaPlayer.setDataSource(file.getPath());//指定音频文件的路径
                        mediaPlayer.prepare();//让MedaiPlayer进入准备状态
                        setPlaySpeed(speed);//设定播放速度
                }catch(Exception e){
                        e.printStackTrace();
                }
        }
        //授予操作限权
        @Override
        public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
                switch (requestCode){
                        case 1:
                                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                                        initMediaPlayer(index);
                                }else{
                                        Toast.makeText(this,"拒绝限权讲无法使用程序",Toast.LENGTH_SHORT).show();
                                        finish();
                                }
                                break;
                        default:
                }

        }
        //设置按钮点击事件,暂停,播放,上一首,下一首,选择倍速
        @Override
        public void onClick(View v){
                switch(v.getId()){
                        case R.id.pre://上一首
                                mediaPlayer.pause();
                                mediaPlayer.reset();
                                if(index==0){
                                        index=2;
                                }else{
                                        index=index-1;
                                }
                                initMediaPlayer(index);
                                musicName.setText(Name[index]);
                                mediaPlayer.start();//开始播放
                                break;
                        case R.id.play:
                                if(!mediaPlayer.isPlaying()){
                                        musicName.setText(Name[index]);
                                        mediaPlayer.start();//开始播放
                                }
                                break;
                        case R.id.pause:
                                if(mediaPlayer.isPlaying()){
                                        mediaPlayer.pause();//暂停播放
                                }
                                break;
                        case R.id.reset:
                                if(mediaPlayer.isPlaying()){
                                        mediaPlayer.reset();//初始化
                                        initMediaPlayer(index);
                                }
                                break;
                        case R.id.next:
                                mediaPlayer.pause();
                                mediaPlayer.reset();
                                if(index==2){
                                        index=0;
                                }else{
                                        index=index+1;
                                }
                                initMediaPlayer(index);
                                musicName.setText(Name[index]);
                                mediaPlayer.start();
                                break;
                        case R.id.speed://设定音乐倍数
                                String tempSpeed =editSpeed.getText().toString();
                                //添加未输入倍速时,应该有的倍数,不输入则为一,防止报错
                                if(tempSpeed.equals("")){
                                        tempSpeed="1";
                                        speed = Float.parseFloat(tempSpeed);
                                        setPlaySpeed(speed);

                                }else {
                                        speed = Float.parseFloat(tempSpeed);
                                        setPlaySpeed(speed);
                                }
                        default:
                                break;
                }

        }
        //activity活动销毁
        @Override
        protected  void onDestroy(){
                super.onDestroy();
                if(mediaPlayer !=null){

                        mediaPlayer.stop();
                        mediaPlayer.release();
                }

        }
        //更新进度条和时间
        public void updateProgress(){
                duration=mediaPlayer.getDuration();
                currentPosition=mediaPlayer.getCurrentPosition();
                timeProgress.setText(new SimpleDateFormat("mm:ss").format(new Date(currentPosition)));
                allTime.setText(new SimpleDateFormat("mm:ss").format(new Date(duration)));
                seekBar.setMax(duration);
                seekBar.setProgress(currentPosition);

        }
        //播放倍速函数
        public boolean setPlaySpeed(float speed){

                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                        try{
                                PlaybackParams params =mediaPlayer.getPlaybackParams();
                                params.setSpeed(speed);

                                mediaPlayer.setPlaybackParams(params);
                                return true;
                        }catch (Exception e){
                                return false;
                        }
                }
                return false;
        }

}

