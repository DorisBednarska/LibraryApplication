package com.example.rc.samples.views.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rc.samples.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    protected ViewPager mViewPager;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.tabs)
    protected TabLayout tabLayout;

    private HomeAdapter homeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        homeAdapter = new HomeAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(homeAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position < 2) {
                    ListFragment fragment = (ListFragment) homeAdapter.instantiateItem(mViewPager, position);
                    fragment.load();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
