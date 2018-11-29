package com.example.huuph.myship.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.huuph.myship.uis.fragment.FragmentHistory;
import com.example.huuph.myship.uis.fragment.FragmentNews;
import com.example.huuph.myship.uis.fragment.FragmentMap;

public class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return FragmentNews.getInstance();
            case 1:
                return FragmentMap.getInstance();
            case 2:
                return FragmentHistory.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Trang chủ";
            case 1:
                return "Map";
            case 2:
                return "Lịch Sử";
        }
        return super.getPageTitle(position);
    }
}
