package com.example.police;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.police.models.*;
//import com.example.police.ui.home.HomeFragment;
import com.example.police.utils.*;


public class MainActivity extends AppCompatActivity {
    private Button btn_login;//登录按钮
    private static String userName;
    private String password;
    private int userId = 0;
    private String spPsw;//获取的用户名，密码，加密密码
    private EditText user_input, password_input;//编辑框
    private static PeopleInfo people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //透明状态栏          
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        init();
    }

    private void init() {
        btn_login = findViewById(R.id.btn_login);
        user_input = findViewById(R.id.user_input);
        password_input = findViewById(R.id.password_input);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始登录，获取用户名和密码 getText().toString().trim();
                // TextUtils.isEmpty
//                PeopleInfo people = null;
                userName = user_input.getText().toString().trim();
                password = password_input.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("MainActivity", "userName:" + userName + "-->>password:" + password);
                //对当前用户输入的密码进行MD5加密再进行比对判断, MD5Utils.md5( ); psw 进行加密判断是否一致
                String md5Psw = MD5Util.crypt(password);
                new Thread(runnable).start();
                Log.i("MainActivity--->people--->>>", "" + people);
                // 展开结果集数据库
                if (people == null) {
//                    spPsw = people.getPwd();
//                    userId = people.getId();
                    spPsw = MD5Util.crypt(password);
//                    System.out.println("id:" + people.getId());
                } else {
                    System.out.println("没有数据");
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在数据库中一致
                if (md5Psw.equals(spPsw)) {
                    //一致登录成功
                    Log.i("登录","登录成功");
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    saveLoginStatus(true, userName, userId);
                    //登录成功后关闭此页面进入主页
                    Intent data = new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin", true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK, data);
                    //销毁登录界面
//                    MainActivity.this.finish();
                    //跳转到主界面，登录成功的状态传递到 MainActivity 中
                    startActivity(new Intent(MainActivity.this, IndexActivity.class));
                    Log.w("跳转","跳转主页");
//                    return;
                } else if ((spPsw != null && !TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw))) {
                    Toast.makeText(MainActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(MainActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
//            Log.i("MainActivity", "查询数据库");
//            try {
//                DbUtility dbUtility = new DbUtility();
//            } catch (ClassNotFoundException | SQLException e) {
//                e.printStackTrace();
//            }
//            try {
//                people = DbUtility.getPeopleInfoByUserName(userName);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//            DbUtility.release();
        }
    };

    /**
     * 从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userName) {
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName, "");
    }

    /**
     * 保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status, String userName,int userId) {
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //存入登录状态时用户Id
        editor.putInt("loginUserId",userId);
        //提交修改
        editor.apply();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName = data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)) {
                //设置用户名到 et_user_name 控件
                user_input.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                user_input.setSelection(userName.length());
            }
        }

    }
}
