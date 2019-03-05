package com.swp.culturehomestay.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.fragments.main.FavoriteFragment;
import com.swp.culturehomestay.fragments.main.HomeFragment;
import com.swp.culturehomestay.fragments.main.MoreFragment;
import com.swp.culturehomestay.fragments.main.NotificationFragment;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentManager fm;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.fragment,new HomeFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_favorite:
                    fragmentTransaction.replace(R.id.fragment,new FavoriteFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.fragment,new NotificationFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_more:
                    fragmentTransaction.replace(R.id.fragment,new MoreFragment());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm = getSupportFragmentManager();
    }

}
