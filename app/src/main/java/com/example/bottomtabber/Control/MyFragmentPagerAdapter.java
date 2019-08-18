package com.example.bottomtabber.Control;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bottomtabber.Fragment.BookCity;
import com.example.bottomtabber.Fragment.BookSheet_Nine;
import com.example.bottomtabber.Fragment.Category;
import com.example.bottomtabber.Fragment.Home;
import com.example.bottomtabber.Activity.Main;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 4;
    private BookCity bookCity;
    private BookSheet_Nine bookSheet;
    private Home home;
    private Category category;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        bookCity=new BookCity();
        category=new Category();
        home=new Home();
        bookSheet=new BookSheet_Nine();
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case Main.PAGE_ONE:
                fragment=bookCity;
                break;
            case Main.PAGE_TWO:
                fragment=bookSheet;
                break;
            case Main.PAGE_THREE:
                fragment=category;
                break;
            case Main.PAGE_FOUR:
                fragment=home;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }


}
