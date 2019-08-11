package com.example.bottomtabber.Control;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bottomtabber.Data.Book;
import com.example.bottomtabber.R;

import java.util.List;

public class BookSheetAdapter extends RecyclerView.Adapter<BookSheetAdapter.ViewHolder>{

    private List<Book> data;
    private Context mContext;
    public OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener monItemClickListener) {
        this.mOnItemClickListener = monItemClickListener;
    }

    public BookSheetAdapter(List<Book> data, Context context){
        this.data=data;
        this.mContext=context;
    }
    @Override
    public BookSheetAdapter.ViewHolder onCreateViewHolder (ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nine_book, viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BookSheetAdapter.ViewHolder viewHolder, int i) {

        viewHolder.iv.setImageResource(data.get(i).getBitmapId());
        viewHolder.tv.setText(data.get(i).getName());
        if(mOnItemClickListener!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(viewHolder.itemView,pos);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(viewHolder.itemView,pos);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
         ViewHolder( View itemView) {
            super(itemView);
            iv =itemView.findViewById(R.id.ItemImage);
            tv = itemView.findViewById(R.id.ItemText);
        }
    }
}
