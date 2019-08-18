package com.example.bottomtabber.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bottomtabber.Activity.AddBook;
import com.example.bottomtabber.Activity.Login;
import com.example.bottomtabber.Activity.Main;
import com.example.bottomtabber.Activity.ShowBook;
import com.example.bottomtabber.Activity.search_show;
import com.example.bottomtabber.Control.OnItemClickListener;
import com.example.bottomtabber.Control.BookSheetAdapter;
import com.example.bottomtabber.Data.Book;
import com.example.bottomtabber.R;
import com.example.bottomtabber.Util.DialogUtils;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.bottomtabber.Activity.Main.PAGE_THREE;

public class BookSheet_Nine extends Fragment {

    public static int putFlag=0;
    public static ArrayList<File> putBooks=null;

    private View view;
    private List<Book> allBooks;
    private int status=0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_nine_book, container, false);
        initData(true,0);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        initData(false,-1);
        initView();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        putFlag=0;
        super.onDestroy();
    }

    public void initView() {
        EditText edit = view.findViewById(R.id.search_activity);
        edit.setFocusable(false);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), search_show.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        BookSheetAdapter adapter = new BookSheetAdapter(allBooks,getContext());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position==allBooks.size()-1){
                    Intent intent = new Intent(getActivity(), AddBook.class);
                    startActivity(intent);
                }else{
                    Book b =LitePal.where("user_name=? and name=?", Login.loginUser.getUsername(),allBooks.get(position).getName())
                            .find(Book.class).get(0);
                    b.setSum(b.getSum()+1);
                    b.save();
                    //Toast.makeText(getContext(), "您点击了" + position +"  "+b.getSum(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), ShowBook.class);
                    i.putExtra("filepath",b.getPath());
                    startActivity(i);
                }
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                final String name = allBooks.get(position).getName();
                DialogUtils.showNormalDialog(view.getContext(), "是否将《" + name + "》移出书架", new DialogUtils.OnButtonClickListener() {
                    @Override
                    public void onConfirmButtonClick() {
                        if(position!=allBooks.size()-1){
                            LitePal.deleteAll(Book.class,"user_name=? and name=?", Login.loginUser.getUsername(),name);
                            initData(false,-1);
                            initView();
                            Toast.makeText(getContext(),"移除成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelButtonClick() {
                        super.onCancelButtonClick();
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void initData(boolean change,int flag){

        if(LitePal.findAll(Book.class).isEmpty()){
            allBooks = new ArrayList<>();
        }
        else{
            if(change){
                realList(flag);
                status = flag;
            }
            else{
                realList(status);
            }
        }
        Book addBook = new Book(R.mipmap.add,"add");
        if (!allBooks.contains(addBook)){
            allBooks.add(addBook);
        }
    }

    private void realList(int i){
        switch (i){
            case 0:
                allBooks = LitePal.where("user_name=?", Login.loginUser.getUsername()).find(Book.class);
                break;
            case 1:
                allBooks = LitePal.where("user_name=?", Login.loginUser.getUsername()).order("name").find(Book.class);
                break;
            case 2:
                allBooks = LitePal.where("user_name=?", Login.loginUser.getUsername()).order("id desc").find(Book.class);
                break;
            case 3:
                allBooks = LitePal.where("user_name=?", Login.loginUser.getUsername()).order("sum desc").find(Book.class);
                break;

        }
    }


}
