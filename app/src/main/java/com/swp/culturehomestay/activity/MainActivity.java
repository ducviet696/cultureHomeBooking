package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {
    AccessToken accessToken;
    private TextView mTextMessage;
    private FragmentManager fm;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ViewPager pager = (ViewPager) findViewById(R.id.pager);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pager.setCurrentItem(0);
                    return true;
                case R.id.navigation_wishlist:
                    pager.setCurrentItem(1);
                    return true;
                case R.id.navigation_booking:
                    pager.setCurrentItem(2);
                    return true;
                case R.id.navigation_more:
                    pager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm = getSupportFragmentManager();
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        Intent intent = getIntent();
        int checkFragment = intent.getIntExtra("checkFragment", 0);
        switch (checkFragment) {
            case 0:
                pager.setCurrentItem(0);
                navigation.getMenu().getItem(0).setChecked(true);
                break;
            case 1:
                navigation.getMenu().getItem(1).setChecked(true);
                pager.setCurrentItem(1);
                break;
            case 2:
                navigation.getMenu().getItem(2).setChecked(true);
                pager.setCurrentItem(2);
                break;
            case 3:
                navigation.getMenu().getItem(3).setChecked(true);
                pager.setCurrentItem(3);
                break;
            default:
                navigation.getMenu().getItem(0).setChecked(true);
                pager.setCurrentItem(0);
                break;
        }
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int pos) {
                switch (pos) {
                    case 0:
                        navigation.getMenu().getItem(0).setChecked(true);
                        break;
                    case 1:
                        navigation.getMenu().getItem(1).setChecked(true);
                        break;
                    case 2:
                        navigation.getMenu().getItem(2).setChecked(true);
                        break;
                    case 3:
                        navigation.getMenu().getItem(3).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

}