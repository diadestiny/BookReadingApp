package com.example.bottomtabber;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 4;
    private MyFragment fg1,fg3,fg4;
    private BookSheet bookSheet;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fg1=new MyFragment("书城");
        fg3=new MyFragment("我的");
        fg4=new MyFragment("设置");
        bookSheet=new BookSheet();
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case MainActivity.PAGE_ONE:
                fragment=fg1;
                break;
            case MainActivity.PAGE_TWO:
                fragment=bookSheet;
                break;
            case MainActivity.PAGE_THREE:
                fragment=fg3;
                break;
            case MainActivity.PAGE_FOUR:
                fragment=fg4;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
