package com.example.bottomtabber.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bottomtabber.Data.Book;
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
                    Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
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
