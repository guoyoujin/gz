package com.guoyoujin.gz.gz.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.fragment.GoddessFragment;
import com.guoyoujin.gz.gz.fragment.NewsFragment;
import com.guoyoujin.gz.gz.fragment.WeatherFragment;
import com.guoyoujin.gz.gz.toolbar.NavigationDrawerCallbacks;
import com.guoyoujin.gz.gz.toolbar.NavigationDrawerFragment;

public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static CharSequence mTitle;
    private NewsFragment newsFragment = null;
    private WeatherFragment weatherFragment = null;
    private GoddessFragment goddessFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //布局里面有三套布局随便你淘选
        //activity_main 在toolbar的下方
        //activity_main_blacktoolbar 在toolbar的下方，但是toolbar会蒙上一层阴影
        // activity_main_topdrawer 把toolbar覆盖
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));//使用此方法一定要在 setSupportActionBar(mToolbar);方法之前
//        mToolbar.setSubtitle("1副标题");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("标题");
//        getSupportActionBar().setSubtitle("副标题");
//        getSupportActionBar().setLogo(R.drawable.ic_launcher);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
//        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
//        mToolbar.setTitle("Menu item selected -> " + position);
        initFragmentTrans();
        switch (position) {
            case 0:
                initNewsFragment();
                mTitle = "NewsFragment";
                break;
            case 1:
                initGoddessFragment();
                mTitle = "Goddess";
                break;
            case 2:
                initWeatherFragment();
                mTitle = "Weather";
                break;
            case 3:
                break;
            default:

        }

    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    public void initFragmentTrans() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        if (transaction == null) {
            transaction = fragmentManager.beginTransaction();
        }
    }

    public void initNewsFragment() {
        if (newsFragment == null) {
            newsFragment = new NewsFragment();
        }
        if (goddessFragment != null) {
            if (goddessFragment.isAdded()) {
                fragmentManager.beginTransaction().hide(goddessFragment).commit();
            }
        }
        if (weatherFragment != null) {
            if (weatherFragment.isAdded()) {
                fragmentManager.beginTransaction().hide(weatherFragment).commit();
            }
        }
        if (newsFragment.isAdded()) {
            fragmentManager.beginTransaction().show(newsFragment).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.container, newsFragment, NewsFragment.class.getName()).commit();
        }
    }

    public void initWeatherFragment() {
        if (weatherFragment == null) {
            weatherFragment = new WeatherFragment();
        }
        if (newsFragment != null) {
            if (newsFragment.isAdded()) {
                fragmentManager.beginTransaction().hide(newsFragment).commit();
            }
        }
        if (goddessFragment != null) {
            if (goddessFragment.isAdded()) {
                fragmentManager.beginTransaction().hide(goddessFragment).commit();
            }
        }
        if (weatherFragment.isAdded()) {
            fragmentManager.beginTransaction().show(weatherFragment).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.container, weatherFragment, WeatherFragment.class.getName()).commit();
        }
    }

    public void initGoddessFragment() {
        if (goddessFragment == null) {
            goddessFragment = new GoddessFragment();
        }
        if (newsFragment != null) {
            if (newsFragment.isAdded()) {
                fragmentManager.beginTransaction().hide(newsFragment).commit();
            }
        }
        if (goddessFragment != null) {
            if (goddessFragment.isAdded()) {
                fragmentManager.beginTransaction().hide(goddessFragment).commit();
            }
        }
        if (goddessFragment.isAdded()) {
            fragmentManager.beginTransaction().show(goddessFragment).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.container, goddessFragment, GoddessFragment.class.getName()).commit();
        }
    }

}
