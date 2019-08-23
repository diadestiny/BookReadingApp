package com.example.bottomtabber.Control;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bottomtabber.R;
import com.zia.easybookmodule.bean.Book;

import java.util.List;

public class BookCityOpenRecyclerAdapter extends RecyclerView.Adapter<BookCityOpenRecyclerAdapter.ViewHolder>{

    private List<Book> list;

    public BookCityOpenRecyclerAdapter(List<Book> list) { this.list = list; }

    public OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener monItemClickListener) {
        this.mOnItemClickListener = monItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_city, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Book book = list.get(position);
        holder.name.setText(book.getBookName());
        holder.author.setText(book.getAuthor());
        holder.content.setText(book.getIntroduce());
        Glide.with(holder.view)
                .load(book.getImageUrl())
                .placeholder(R.mipmap.zanwu)
                .into((ImageView)holder.view.findViewById(R.id.book_image));
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView,pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView author;
        TextView content;
        ImageView imageView;
        View view;
        ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            name = itemView.findViewById(R.id.name);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
            imageView = itemView.findViewById(R.id.book_image);
        }
    }
}
