package com.example.bottomtabber.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bottomtabber.Fragment.BookCity;
import com.example.bottomtabber.Fragment.BookSheet_Nine;
import com.example.bottomtabber.R;
import com.zia.easybookmodule.bean.Book;
import com.zia.easybookmodule.bean.Catalog;
import com.zia.easybookmodule.engine.EasyBook;
import com.zia.easybookmodule.rx.Subscriber;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class ShowBookChapter extends AppCompatActivity {

    private ListView listView;
    private Book book;
    public static Book tempBook;
    public static Catalog catalog;
    private List<String> strings =new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_chapter);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listView = findViewById(R.id.listViewShow);
        if(getIntent().getIntExtra("flagClassNum",0)==2){
            book = BookSheet_Nine.putbook;
        }else{
            book = BookCity.book;
        }
        tempBook=book;
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("目录正在全力加载中");
        progressDialog.setMessage("客官请稍等一下下~~");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        init();
    }

    private void init() {
        EasyBook.getCatalog(book)
                .subscribe(new Subscriber<List<Catalog>>() {
                    @Override
                    public void onFinish(final @NonNull List<Catalog> catalogs) {
                        for(Catalog catalog:catalogs){
                            strings.add(catalog.getChapterName());
                        }
                        progressDialog.hide();
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(ShowBookChapter.this, android.R.layout.simple_expandable_list_item_1, strings);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               catalog =  catalogs.get(position);
                               Intent intent = new Intent(ShowBookChapter.this,ShowBookInternet.class);
                                intent.putExtra("chapter",catalog.getChapterName());
                                startActivity(intent);
                            }
                          });
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
