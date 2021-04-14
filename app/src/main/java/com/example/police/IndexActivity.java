package com.example.police;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class IndexActivity extends AppCompatActivity {
    private int userId;
    private String userName;
    TextView showUser;//展示用户名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("user","--进入用户界面");
//        setContentView(R.id.videoView);
        setContentView(R.layout.activity_index);
        init();
    }

    private void init(){
        // 读取用户名
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        userId = sp.getInt("loginUserId",0);
        userName = sp.getString("loginUserName","-");

        showUser = findViewById(R.id.showUser);
        showUser.setText(userName);
    }

//    如果要读取数据：
//    SharedPreferences pref = getSharedPreferences(“data”,MODE_PRIVATE);
//    String name = pref.getString(“name”,”“);//第二个参数为默认值
//    int age = pref.getInt(“age”,0);//第二个参数为默认值
//    boolean married = pref.getBoolean(“married”,false);//第二个参数为默认值

}