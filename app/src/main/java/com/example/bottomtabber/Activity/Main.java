package com.example.bottomtabber.Activity;

import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.bottomtabber.Control.MyFragmentPagerAdapter;
import com.example.bottomtabber.Fragment.BookSheet_Nine;
import com.example.bottomtabber.R;



public class Main extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView txt_channel;
    private TextView txt_message;
    private TextView txt_better;
    private TextView txt_setting;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter mAdapter;

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        initUI();
    }

    private void initUI() {
        txt_channel = findViewById(R.id.txt_channel);
        txt_message = findViewById(R.id.txt_message);
        txt_better  = findViewById(R.id.txt_better);
        txt_setting = findViewById(R.id.txt_setting);
        viewPager   = findViewById(R.id.ly_content);

        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        txt_channel.setOnClickListener(this);
        txt_message.setOnClickListener(this);
        txt_better.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }
    //重置所有文本的选中状态
    private void setSelected(){
        txt_channel.setSelected(false);
        txt_message.setSelected(false);
        txt_better.setSelected(false);
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
                    .items(new String[]{"按书籍名称(A-Z)","按入架时间(新-旧)","按阅读次数(大-小)"})
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            PagerAdapter f = viewPager.getAdapter();
                            BookSheet_Nine bookSheet_nine= (BookSheet_Nine)f.instantiateItem(viewPager,PAGE_TWO);
                            switch (which){
                                case 0:
                                    bookSheet_nine.initData(true,1);
                                    bookSheet_nine.initView();
                                    break;
                                case 1:
                                    bookSheet_nine.initData(true,2);
                                    bookSheet_nine.initView();
                                    break;
                            }

                            return true;
                        }
                    })
                    .show();


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
            case R.id.txt_message:
                setSelected();
                txt_message.setSelected(true);
                viewPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.txt_better:
                setSelected();
                txt_better.setSelected(true);
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
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

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
                    txt_message.setSelected(true);
                    break;
                case PAGE_THREE:
                    setSelected();
                    txt_better.setSelected(true);
                    break;
                case PAGE_FOUR:
                    setSelected();
                    txt_setting.setSelected(true);
                    break;

            }
        }
    }
}