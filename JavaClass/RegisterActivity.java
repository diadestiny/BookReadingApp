package com.example.bottomtabber;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

public class RegisterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Button registerbtn;
    private EditText etAccount;
    private EditText etPassword1;
    private EditText etPassword2;
    private CheckBox checkEye1;
    private CheckBox checkEye2;
    private SQLiteDatabase db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        setContentView(R.layout.activity_register);
        initUI();

    }

    private void initUI() {
        registerbtn = findViewById(R.id.bt_register);
        etAccount = findViewById(R.id.et_account);
        etPassword1 = findViewById(R.id.et_password1);
        etPassword2 = findViewById(R.id.et_password2);
        checkEye1 = findViewById(R.id.checkeye1);
        checkEye2 = findViewById(R.id.checkeye2);
        checkEye1.setOnCheckedChangeListener(this);
        checkEye2.setOnCheckedChangeListener(this);
        registerbtn.setOnClickListener(this);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkeye1:
                if (isChecked) {
                    //CheckBox选中，显示明文
                    etPassword1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    checkEye1.setBackgroundResource(R.mipmap.unhide_pwd_image);
                } else {
                    //CheckBox取消选中，显示暗文
                    etPassword1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    checkEye1.setBackgroundResource(R.mipmap.hide_pwd_image);
                }
                //光标移至最末端
                etPassword1.setSelection(etPassword1.getText().toString().length());
                break;
            case R.id.checkeye2:
                if (isChecked) {
                    //CheckBox选中，显示明文
                    etPassword2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    checkEye2.setBackgroundResource(R.mipmap.unhide_pwd_image);
                } else {
                    //CheckBox取消选中，显示暗文
                    etPassword2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    checkEye2.setBackgroundResource(R.mipmap.hide_pwd_image);
                }
                //光标移至最末端
                etPassword2.setSelection(etPassword2.getText().toString().length());
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_register:
                saveRegisterData();
                break;

        }
    }

    private void saveRegisterData() {
        Boolean flag=false;
        if(etPassword1.getText().toString().equals(etPassword2.getText().toString())) {
            db = LitePal.getDatabase();
            List<User> allUsers = LitePal.findAll(User.class);
            User registerUser = new User(etAccount.getText().toString(),etPassword2.getText().toString());
            if(registerUser.getUsername().equals("")||registerUser.getPassword().equals("")){
                Toast.makeText(this,"账号或者密码不能为空",Toast.LENGTH_SHORT).show();
            }
            else{
                for(User u:allUsers){
                    if(u.getUsername().equals(registerUser.getUsername())){
                        Toast.makeText(this,"此账号已存在",Toast.LENGTH_SHORT).show();
                        flag=true;
                        break;
                    }
                }
                if(!flag){
                    Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                    registerUser.save();
                }
            }

        }
        else{
            Toast.makeText(this,"两次密码输入不一致，请重新输入",Toast.LENGTH_SHORT).show();
        }

    }
}
