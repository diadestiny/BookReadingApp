package com.example.bottomtabber.Activity;


import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomtabber.Control.FileOpenRecyclerAdapter;
import com.example.bottomtabber.Fragment.BookSheet_Nine;
import com.example.bottomtabber.R;
import com.example.bottomtabber.Util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddBook extends AppCompatActivity {

    private ArrayList<File> list = new ArrayList<>() ;
    private RecyclerView recyclerView;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_file_open);
        bar = findViewById(R.id.progress_bar);
        bar.setVisibility(View.VISIBLE);
        init();
    }

    private void initView(){
        bar.setVisibility(View.GONE);
        TextView text =findViewById(R.id.text);
        text.setText("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //返回按钮
        recyclerView = findViewById(R.id.open_file_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);

        FileOpenRecyclerAdapter adapter = new FileOpenRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void init(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();//增加部分
                    if(BookSheet_Nine.putFlag==0){
                        list = (ArrayList<File>) FileUtil.getSuffixFile(new ArrayList<File>(), String.valueOf(Environment.getExternalStorageDirectory()), ".txt");

                        Toast.makeText(getApplicationContext(),"智能扫描到"+list.size()+"个txt文本(已过滤小文本)",Toast.LENGTH_SHORT).show();
                        BookSheet_Nine.putFlag=1;
                        BookSheet_Nine.putBooks=(ArrayList<File>) list.clone();
                    }
                    else{
                        list = BookSheet_Nine.putBooks;
                    }
                   AddBook.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initView();
                        }
                   });
                    Looper.loop();
                }
        }).start();
    }
}
