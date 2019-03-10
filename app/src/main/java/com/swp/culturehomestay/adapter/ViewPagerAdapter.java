package com.swp.culturehomestay.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.swp.culturehomestay.fragments.main.AccountFragment;
import com.swp.culturehomestay.fragments.main.BookingFragment;
import com.swp.culturehomestay.fragments.main.HomeFragment;
import com.swp.culturehomestay.fragments.main.WishlistFragment;

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
                return new WishlistFragment();
            case 2:
                return new BookingFragment();
            case 3:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
