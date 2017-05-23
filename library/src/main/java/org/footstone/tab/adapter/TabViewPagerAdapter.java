package org.footstone.tab.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by chenpengfei on 2017/3/21.
 */
public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragmentArray;

    public TabViewPagerAdapter(FragmentManager mFragmentManager, Fragment[] fragmentArray) {
        super(mFragmentManager);

        this.mFragmentArray = fragmentArray;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentArray[position];
    }

    @Override
    public int getCount() {
        return mFragmentArray.length;
    }
}

