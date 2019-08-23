package com.example.bottomtabber.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.bottomtabber.Fragment.BookCity;
import com.example.bottomtabber.R;
import com.zia.easybookmodule.bean.Book;
import com.zia.easybookmodule.bean.Catalog;
import com.zia.easybookmodule.engine.EasyBook;
import com.zia.easybookmodule.rx.Subscriber;

import java.util.List;

import androidx.annotation.NonNull;

public class ShowBookInternet extends AppCompatActivity {
    private TextView textView;
    private TextView textView1;
    private Catalog catalog;
    private Book book;
    private String content;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_internet);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView = findViewById(R.id.textInternet);
        textView1 = findViewById(R.id.textTop);
        textView1.setText(getIntent().getStringExtra("chapter"));
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("内容正在全力加载中");
        progressDialog.setMessage("客观请稍等一下下~~");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        book = BookCity.book;
        catalog = ShowBookChapter.catalog;
        init();

    }

    private void init() {
        EasyBook.getContent(book,catalog)
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onFinish(@NonNull List<String> list) {
                        for(String s:list){
                            content = content + s +"\n";
                        }
                        content = content.replaceAll("\\n","\n");
                        content = content.substring(4);
                        progressDialog.hide();
                        textView.setText(content);
                    }
                    @Override
                    public void onError(@NonNull Exception e) {

                    }

                    @Override
                    public void onMessage(@NonNull String message) {

                    }

                    @Override
                    public void onProgress(int progress) {

                    }
                });
    }


}
