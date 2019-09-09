package com.example.bottomtabber.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bottomtabber.Data.Book;
import com.example.bottomtabber.Fragment.BookSheet_Nine;
import com.example.bottomtabber.R;
import com.example.bottomtabber.Util.SearchView;

import org.litepal.LitePal;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;


public class search_show extends AppCompatActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_show);
        initView();
    }

    private void initView() {
        searchView = findViewById(R.id.search_view);
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                if(!LitePal.where("user_name=? and name =?", Login.loginUser.getUsername(),string).find(Book.class).isEmpty()){
                    Book b =LitePal.where("user_name=? and name=?", Login.loginUser.getUsername(),string)
                            .find(Book.class).get(0);
                    b.setSum(b.getSum()+1);
                    b.save();
                    if (b.getPath().equals("noPath")){
                        BookSheet_Nine.putbook = Main.lists.get(b.getListNum());
                        Intent intent = new Intent(getApplicationContext(), ShowBookChapter.class);
                        intent.putExtra("flagClassNum",2);
                        startActivity(intent);
                    }else{
                        Intent i = new Intent(getApplicationContext(), ShowBook.class);
                        i.putExtra("filepath",b.getPath());
                        startActivity(i);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "该书没有被添加到书架中", Toast.LENGTH_SHORT).show();
                }
            }
        });
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }
}
