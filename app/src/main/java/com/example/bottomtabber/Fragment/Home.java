package com.example.bottomtabber.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.example.bottomtabber.Activity.Login;
import com.example.bottomtabber.Data.User;
import com.example.bottomtabber.R;


import org.litepal.LitePal;





public class Home extends Fragment {

    private View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);
        return view;
    }


}

