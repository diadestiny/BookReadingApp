package com.example.bottomtabber.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomtabber.Activity.Login;
import com.example.bottomtabber.Activity.Main;
import com.example.bottomtabber.Activity.ShowBookChapter;
import com.example.bottomtabber.Control.BookCityOpenRecyclerAdapter;
import com.example.bottomtabber.Control.OnItemClickListener;
import com.example.bottomtabber.R;
import com.example.bottomtabber.Util.DialogUtils;
import com.google.gson.Gson;
import com.zia.easybookmodule.bean.Book;
import com.zia.easybookmodule.engine.EasyBook;
import com.zia.easybookmodule.rx.StepSubscriber;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class BookCity extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private List<Book> list =new ArrayList<>();
    private SearchView searchView;
    private ProgressBar bar;
    private TextView textView;
    public static Book book;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_bookcity, container, false);
        bar = view.findViewById(R.id.progressbar1);
        bar.setVisibility(View.GONE);
        textView = view.findViewById(R.id.textcity);
        textView.setText("  客官请点击左上角的搜索图标~~~");
        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint("请输入你想阅读的书籍");
        recyclerView = view.findViewById(R.id.recycler);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                initView();
                getSearchContent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }


    private void initView() {
        bar.setVisibility(View.GONE);
        textView.setText("  ");
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDivider = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDivider);
        BookCityOpenRecyclerAdapter adapter = new BookCityOpenRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                book = list.get(position);
                Intent intent = new Intent(getActivity(), ShowBookChapter.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                final String name = list.get(position).getBookName();
                final String imageUri = list.get(position).getImageUrl();
                DialogUtils.showNormalDialog(view.getContext(), "是否将《" + name + "》加入书架", new DialogUtils.OnButtonClickListener() {
                    @Override
                    public void onConfirmButtonClick() {
                        com.example.bottomtabber.Data.Book book = new com.example.bottomtabber.Data.Book(R.mipmap.zanwu,name,Login.loginUser.getUsername());
                        if(LitePal.where("user_name=? and name =?", Login.loginUser.getUsername(),name).find(com.example.bottomtabber.Data.Book.class).isEmpty()){
                            book.setListNum(Main.lists.size());
                            book.setImageUrl(imageUri);
                            book.save();
                            Main.lists.add(list.get(position));
                            SharedPreferences sp = getActivity().getSharedPreferences("BOOK_LIST", Activity.MODE_PRIVATE);
                            Gson gson = new Gson();
                            String jsonStr=gson.toJson(Main.lists); //将List转换成Json
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("KEY_BOOK_DATA",jsonStr);
                            editor.apply();
                            Toast.makeText(getContext(), name + "成功加入书架", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), name + "已在书架中存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelButtonClick() {
                        super.onCancelButtonClick();
                    }
                });
            }
        });
    }


    private void getSearchContent(String s){
        bar.setVisibility(View.VISIBLE);
        textView.setText("正在搜索书籍~~请耐心等候~~~");
        EasyBook.search(s)
                .subscribe(new StepSubscriber<List<Book>>() {
                    @Override
                    public void onFinish(@NonNull List<Book> books) {
                        //所有站点小说爬取完后调用这个方法，传入所有站点解析的有序结果
                        list = books;
                        Toast.makeText(getContext(),"长按可以添加到书架噢~~",Toast.LENGTH_SHORT).show();
                        //Log.d("lkh"," "+list.size());
                        initView();
                    }
                    @Override
                    public void onError(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onMessage(@NonNull String message) {
                        //一些搜索中的进度消息，错误原因等，可以用toast弹出
                    }
                    @Override
                    public void onProgress(int progress) {
                        //搜索进度
                    }
                    @Override
                    public void onPart(@NonNull List<Book> books) {
                        //某一个站点的小说搜索结果
                    }
                });
    }


}
