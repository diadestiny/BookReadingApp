package com.example.bottomtabber.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bottomtabber.Data.Book;
import com.example.bottomtabber.R;
import com.example.bottomtabber.Util.FileUtil;

import org.litepal.LitePal;

import java.io.File;


public class ShowBook extends AppCompatActivity {

    private TextView text;
    private TextView text1;
    private ProgressBar bar;
    private String bookString;
    private File file;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        text = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);
        bar = findViewById(R.id.progress_bar);
        file = new File(getIntent().getStringExtra("filepath"));
        book = LitePal.where("user_name=? and path=?", Login.loginUser.getUsername(),file.getAbsolutePath())
                .find(Book.class).get(0);
        bookString =FileUtil.getFileContent1(file);
        if(book.getBookContent().equals("NoContent")){
            init1();
        }
        else{
            init2();
        }
    }

    private void init1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                text.setText(bookString);
                bookString = FileUtil.getFileContent2(file);
                ShowBook.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text1.setText("");
                        bar.setVisibility(View.GONE);
                        text.setText(bookString);
                        book.setBookContent(bookString);
                        book.save();
                    }
                });
            }
        }).start();
    }
    private void init2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                text.setText(bookString);
                text1.setText("书籍全力加载中~~~");
                ShowBook.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text1.setText("");
                        bar.setVisibility(View.GONE);
                        text.setText(book.getBookContent());
                    }
                });
            }
        }).start();
    }

}
