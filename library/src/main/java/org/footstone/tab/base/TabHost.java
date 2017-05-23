package org.footstone.tab.base;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.footstone.layout.R;
import org.footstone.tab.OnTabSelectedListener;
import org.footstone.tab.adapter.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenpengfei on 2017/3/21.
 */
public class TabHost {

    private Context mContext;
    private LinearLayout mRootView;


    private List<AbsTab> mTabList = new ArrayList<>();

    private ViewPager mContentViewPager;


    public TabHost(Context context) {
        this.mContext = context;

        initView();
    }


    private void initView() {
        mRootView = new LinearLayout(mContext);
        mRootView.setOrientation(LinearLayout.HORIZONTAL);
        mRootView.setId(R.id.linearlayout_tab);

        RelativeLayout.LayoutParams rootViewLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootViewLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mRootView.setLayoutParams(rootViewLp);
    }

    public void setContentViewPager(ViewPager contentViewPager) {
        this.mContentViewPager = contentViewPager;
    }

    public LinearLayout getRootView() {
        return mRootView;
    }

    public void addTabs(BaseAdapter baseAdapter) {
        int count = baseAdapter.getCount();
        if (count == 0) return;

        mTabList.clear();
        mRootView.removeAllViews();

        for (int i = 0; i < count; i++) {
            addTab(baseAdapter.getTab(i));
        }
    }


    private void addTab(AbsTab tab) {
        if (tab == null) return;

        mTabList.add(tab);
        mRootView.addView(tab.getTabRootView());
        tabAddSelectedListener(tab);
    }

    private void tabAddSelectedListener(AbsTab tab) {
        tab.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(AbsTab tab) {
                mContentViewPager.setCurrentItem(tab.getTabIndex(), false);
            }
        });
    }


    public AbsTab getTabForIndex(int index) {
        if (mTabList.size() <= index) {
            return null;
        }
        return mTabList.get(index);
    }

    public void changeTabHostStatus(int index) {
        for (int i = 0, size = mTabList.size(); i < size; i++) {
            AbsTab tab = mTabList.get(i);
            tab.tabSelected(index == i ? true : false);
        }
    }


    public void setTabBgColor(int bgColor) {
        for (int i = 0, size = mTabList.size(); i < size; i++) {
            AbsTab tab = mTabList.get(i);
            tab.getTabRootView().setBackgroundColor(bgColor);
        }
    }

}
