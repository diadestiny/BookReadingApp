package com.example.bottomtabber.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.bottomtabber.Control.MyFragmentPagerAdapter;
import com.example.bottomtabber.Fragment.BookSheet_Nine;
import com.example.bottomtabber.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zia.easybookmodule.bean.Book;

import java.util.ArrayList;
import java.util.List;




public class Main extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView txt_channel;
    private TextView txt_message;
    private TextView txt_better;
    private TextView txt_setting;
    public  ViewPager viewPager;
    private MyFragmentPagerAdapter mAdapter;

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    public static List<Book> lists = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sp1 = getSharedPreferences("BOOK_LIST", Activity.MODE_PRIVATE);
        String listJson = sp1.getString("KEY_BOOK_DATA","");
        if(!listJson.equals("")){
            Gson gson = new Gson();
           lists = gson.fromJson(listJson,new TypeToken<List<Book>>(){}.getType());
        }else {
            lists = new ArrayList<>();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        initUI();
    }

    private void initUI() {
        txt_channel = findViewById(R.id.txt_channel);
        txt_better  = findViewById(R.id.txt_better);
        txt_message = findViewById(R.id.txt_message);
        txt_setting = findViewById(R.id.txt_setting);
        viewPager   = findViewById(R.id.ly_content);

        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        txt_channel.setOnClickListener(this);
        txt_better.setOnClickListener(this);
        txt_message.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }
    //重置所有文本的选中状态
    private void setSelected(){
        txt_channel.setSelected(false);
        txt_better.setSelected(false);
        txt_message.setSelected(false);
        txt_setting.setSelected(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_list_item:
                 new MaterialDialog.Builder(this)
                    .title("排列方式")
                    .items(new String[]{"按入架时间(旧-新)","按入架时间(新-旧)","按阅读次数(大-小)"})
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            PagerAdapter f = viewPager.getAdapter();
                            if(f!=null){
                                BookSheet_Nine bookSheet_nine= (BookSheet_Nine)f.instantiateItem(viewPager,PAGE_THREE);
                                switch (which){
                                    case 0:
                                        bookSheet_nine.initData(true,0);
                                        bookSheet_nine.initView();
                                        break;
                                    case 1:
                                        bookSheet_nine.initData(true,2);
                                        bookSheet_nine.initView();
                                        break;
                                    case 2:
                                        bookSheet_nine.initData(true,3);
                                        bookSheet_nine.initView();
                                        break;
                                }
                            }
                            return true;
                        }
                    })
                    .show();
                break;
            case R.id.id_chat:
                new MaterialDialog.Builder(this)
                        .title("服务器ip设置")
                        //限制输入的长度
                        .inputRangeRes(0, 40, R.color.colorPrimary)
                        //限制输入类型
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入您的服务器ip", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                Intent intent1 =new Intent(Main.this,ChatActivity.class);
                                intent1.putExtra("ip_address",input.toString());
                                startActivity(intent1);
                            }
                        })
                        .canceledOnTouchOutside(false)
                        .positiveText("确定")
                        .show();
                break;
            case R.id.id_tensor_lite:
                Intent intent2 = new Intent(Main.this, Model_Identification.class);
                startActivity(intent2);
                break;
            case R.id.id_fly_keDa:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_channel:
                setSelected();
                txt_channel.setSelected(true);
                viewPager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.txt_better:
                setSelected();
                txt_better.setSelected(true);
                viewPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.txt_message:
                setSelected();
                txt_message.setSelected(true);
                viewPager.setCurrentItem(PAGE_THREE);
            break;
            case R.id.txt_setting:
                setSelected();
                txt_setting.setSelected(true);
                viewPager.setCurrentItem(PAGE_FOUR);
                break;
        }

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {
        if(i==2){
            int currentItemPosition = viewPager.getCurrentItem();
            switch (currentItemPosition){
                case PAGE_ONE:
                    setSelected();
                    txt_channel.setSelected(true);
                    break;
                case PAGE_TWO:
                    setSelected();
                    txt_better.setSelected(true);
                    break;
                case PAGE_THREE:
                    setSelected();
                    txt_message.setSelected(true);
                    break;
                case PAGE_FOUR:
                    setSelected();
                    txt_setting.setSelected(true);
                    break;

            }
        }
    }


}
