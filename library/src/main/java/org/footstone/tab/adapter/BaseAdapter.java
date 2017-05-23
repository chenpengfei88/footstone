package org.footstone.tab.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.footstone.tab.base.AbsTab;


/**
 * Created by chenpengfei on 2017/3/22.
 */
public abstract class BaseAdapter {

    private Fragment[] mFragmentArray;
    private FragmentManager mFragmentManager;
    protected Context mContext;

    public BaseAdapter(Context context, Fragment[] fragments, FragmentManager fragmentManager) {
        mContext = context;
        mFragmentArray = fragments;
        mFragmentManager = fragmentManager;
    }


    public int getCount() {
        return mFragmentArray != null ? mFragmentArray.length : 0;
    }


    public Fragment[] getFragmentArray() {
        return mFragmentArray;
    }

    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }


    public abstract AbsTab getTab(int index);

}
