package com.nbjinque.myapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioButton radio_home, radio_type, radio_cart, radio_mine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager mViewPager = findViewById(R.id.viewPager);
        radio_home = findViewById(R.id.rb_home);
        radio_type = findViewById(R.id.rb_type);
        radio_cart = findViewById(R.id.rb_cart);
        radio_mine = findViewById(R.id.rb_user);

        List<Fragment> list = new ArrayList<>();
        //list.add(TabTwoFragment.newInstance("我是1"));
        //list.add(TabTwoFragment.newInstance("我是2"));
        //list.add(TabTwoFragment.newInstance("我是3"));

        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), list));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                reSetStatus();
                switch (position) {
                    case 0:
                        radio_home.setChecked(true);
                        break;
                    case 1:
                        radio_type.setChecked(true);
                        break;
                    case 2:
                        radio_cart.setChecked(true);
                        break;
                    case 3:
                        radio_mine.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void reSetStatus() {
        radio_home.setChecked(false);
        radio_type.setChecked(false);
        radio_cart.setChecked(false);
        radio_mine.setChecked(false);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<android.support.v4.app.Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm, List<android.support.v4.app.Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }

}
