package com.example.bottomtabber.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bottomtabber.R;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Category extends Fragment {
    private View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_category, container, false);

        return view;
    }


}
