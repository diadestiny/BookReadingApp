package com.example.bottomtabber.Control;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomtabber.Activity.Login;
import com.example.bottomtabber.Data.Book;
import com.example.bottomtabber.R;
import com.example.bottomtabber.Util.DialogUtils;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;

public class FileOpenRecyclerAdapter extends RecyclerView.Adapter<FileOpenRecyclerAdapter.ViewHolder>{

    private ArrayList<File> list;

    public FileOpenRecyclerAdapter(ArrayList<File> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_open, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //响应item点击事件
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int position = holder.getAdapterPosition();
                final String name = list.get(position).getName();
                DialogUtils.showNormalDialog(view.getContext(), "是否将《" + name + "》加入书架", new DialogUtils.OnButtonClickListener() {
                    @Override
                    public void onConfirmButtonClick() {
                        Book book = new Book(R.mipmap.book1,name, Login.loginUser.getUsername());
                        if(LitePal.where("user_name=? and name =?", Login.loginUser.getUsername(),name).find(Book.class).isEmpty()){
                            book.setPath(list.get(position).getAbsolutePath());
                            book.save();
                            Toast.makeText(view.getContext(), name + "成功加入书架", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(view.getContext(), name + "已在书架中存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelButtonClick() {
                        super.onCancelButtonClick();
                    }
                });
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String nametext = list.get(position).getName();
        String pathtext = list.get(position).getAbsolutePath();
        holder.name.setText(nametext);
        holder.path.setText(pathtext);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView path;
        View view;
         ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            name = itemView.findViewById(R.id.file_name);
            path = itemView.findViewById(R.id.file_path);
        }
    }
}
