package com.example.bottomtabber;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button loginbtn;
    private TextView newUser;
    private EditText etAccount;
    private EditText etPassword;
    private CheckBox checkBox;
    private CheckBox checkEye;
    private String SP_ACCOUNT="sp_account";
    private String SP_PASSWD="sp_password";
    private String SP_IS_REMEMBER="sp_is_remember";
    private SharedPreferences sharedPreferences=null;
    private boolean misChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        setContentView(R.layout.activity_login);
        initUI();
        initData();

    }
    private void initData(){
        if(sharedPreferences==null){
            sharedPreferences = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        misChecked = sharedPreferences.getBoolean(SP_IS_REMEMBER,false);
        String account = sharedPreferences.getString(SP_ACCOUNT,"");
        String password = sharedPreferences.getString(SP_PASSWD,"");
        etAccount.setText(account);
        etPassword.setText(password);
        checkBox.setChecked(misChecked);

    }
    private void initUI(){
        newUser = findViewById(R.id.newuser);
        loginbtn = findViewById(R.id.bt_login);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        checkBox = findViewById(R.id.checkbox);
        checkEye = findViewById(R.id.checkeye);
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keepData();
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keepData();
            }
        });
        loginbtn.setOnClickListener(this);
        newUser.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);
        checkEye.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                login();
                break;
            case R.id.newuser:
                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent2);
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId()==R.id.checkbox){
            misChecked = isChecked;
            keepData();
        }
        if(buttonView.getId()==R.id.checkeye){
            if (isChecked) {
                //CheckBox选中，显示明文
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                checkEye.setBackgroundResource(R.mipmap.unhide_pwd_image);
            } else {
                //CheckBox取消选中，显示暗文
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                checkEye.setBackgroundResource(R.mipmap.hide_pwd_image);
            }
            //光标移至最末端
            etPassword.setSelection(etPassword.getText().toString().length());

        }
    }
    private void keepData(){
        if(sharedPreferences==null) {
            sharedPreferences = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(misChecked){
            editor.putString(SP_ACCOUNT,etAccount.getText().toString());
            editor.putString(SP_PASSWD,etPassword.getText().toString());
            editor.putBoolean(SP_IS_REMEMBER,misChecked);
        }
        else{
            editor.putString(SP_PASSWD,"");
            editor.putBoolean(SP_IS_REMEMBER,misChecked);
        }
        editor.apply();
    }
    private void login(){
        SQLiteDatabase db = LitePal.getDatabase();
        List<User> allUsers = LitePal.findAll(User.class);
        User loginUser =new User(etAccount.getText().toString(),etPassword.getText().toString());
        if(loginUser.getUsername().equals("")||loginUser.getPassword().equals("")){
            Toast.makeText(this,"账号或者密码不能为空",Toast.LENGTH_SHORT).show();
        }
        else if(allUsers.contains(loginUser)){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"账号不存在或者密码错误",Toast.LENGTH_SHORT).show();
        }

    }
}
