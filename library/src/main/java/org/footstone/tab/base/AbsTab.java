package org.footstone.tab.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.footstone.tab.OnTabSelectedListener;


/**
 * Created by chenpengfei on 2017/4/27.
 */
public abstract class AbsTab {

    protected Context mContext;

    private int mIndex;


    private View mRootView;


    protected boolean mIsSelected;


    private OnTabSelectedListener mOnTabSelectedListener;

    public AbsTab(Context context, int index) {
        mContext = context;
        mIndex = index;
    }


    protected void inflaterView(final AbsTab tab, @LayoutRes int layoutResId) {
        mRootView = LayoutInflater.from(mContext).inflate(layoutResId, null);
        LinearLayout.LayoutParams rootViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootViewLp.weight = 1;
        mRootView.setLayoutParams(rootViewLp);

        initView(mRootView);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTabSelectedListener != null) {
                    mOnTabSelectedListener.onTabSelected(tab);
                }
            }
        });
    }


    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mOnTabSelectedListener = onTabSelectedListener;
    }


    public View getTabRootView() {
        return mRootView;
    }


    public int getTabIndex() {
        return  mIndex;
    }


    public void showMessageTip(boolean show, int count) {};


    protected abstract void tabSelected(boolean isSelected);


    protected abstract void initView(View rootView);
}
