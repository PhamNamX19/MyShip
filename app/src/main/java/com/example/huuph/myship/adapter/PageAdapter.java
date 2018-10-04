package com.example.huuph.myship.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.huuph.myship.uis.fragment.FragmentFavorite;
import com.example.huuph.myship.uis.fragment.FragmentNews;
import com.example.huuph.myship.uis.fragment.FragmentSaved;

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
                return FragmentSaved.getInstance();
            case 2:
                return FragmentFavorite.getInstance();
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
                return "News";
            case 1:
                return "Map";
            case 2:
                return "Favorite";
        }
        return super.getPageTitle(position);
    }
}
