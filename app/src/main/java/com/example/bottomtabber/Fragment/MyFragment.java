package com.example.bottomtabber.Fragment;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bottomtabber.R;


@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {
    private String content;

    public MyFragment(){}
    public MyFragment(String content){
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_temp,container,false);
        TextView txt_content = view.findViewById(R.id.txt_content);
        txt_content.setText(content);
        return view;
    }
}
