package com.swp.culturehomestay.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.swp.culturehomestay.fragments.main.FavoriteFragment;
import com.swp.culturehomestay.fragments.main.HomeFragment;
import com.swp.culturehomestay.fragments.main.MoreFragment;
import com.swp.culturehomestay.fragments.main.NotificationFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                return new HomeFragment();
            case 1:
                return new FavoriteFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new MoreFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
