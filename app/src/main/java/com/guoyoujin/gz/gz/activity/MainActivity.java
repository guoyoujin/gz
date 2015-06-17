package com.guoyoujin.gz.gz.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.fragment.GoddessFragment;
import com.guoyoujin.gz.gz.fragment.NewsFragment;
import com.guoyoujin.gz.gz.fragment.WeatherFragment;
import com.guoyoujin.gz.gz.toolbar.NavigationDrawerCallbacks;
import com.guoyoujin.gz.gz.toolbar.NavigationDrawerFragment;

public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks {

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //布局里面有三套布局随便你淘选
        //activity_main 在toolbar的下方
        //activity_main_blacktoolbar 在toolbar的下方，但是toolbar会蒙上一层阴影
        // activity_main_topdrawer 把toolbar覆盖
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbar.setTitle("你好");//使用此方法一定要在 setSupportActionBar(mToolbar);方法之前
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
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
//        mToolbar.setTitle("Menu item selected -> " + position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new NewsFragment();
                mTitle = "NewsFragment";
                break;
            case 1:
                fragment = new WeatherFragment();
                mTitle = "Weather";
                break;
            case 3:
                fragment = new GoddessFragment();
                mTitle = "Goddess";
                break;
            case 4:
                break;
            default:

        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
}
