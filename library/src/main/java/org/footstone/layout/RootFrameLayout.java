package org.footstone.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.footstone.utils.ObjectUtils;

/**
 * Created by chenpengfei on 2016/12/15.
 */
public class RootFrameLayout extends FrameLayout {


    public static final int LAYOUT_LOADING_ID = 1;

    public static final int LAYOUT_CONTENT_ID = 2;

    public static final int LAYOUT_ERROR_ID = 3;

    public static final int LAYOUT_NETWORK_ERROR_ID = 4;

    public static final int LAYOUT_EMPTYDATA_ID = 5;


    private SparseArray<View> mLayoutSparseArray = new SparseArray();

    private StatusLayoutManager mStatusLayoutManager;


    public RootFrameLayout(Context context) {
        super(context);
    }

    public RootFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RootFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RootFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setStatusLayoutManager(StatusLayoutManager statusLayoutManager) {
        mStatusLayoutManager = statusLayoutManager;

        addAllLayoutToRootLayout();
    }

    private void addAllLayoutToRootLayout() {
        if (mStatusLayoutManager.contentLayoutResId != 0) addLayoutResId(mStatusLayoutManager.contentLayoutResId, RootFrameLayout.LAYOUT_CONTENT_ID);
        if (mStatusLayoutManager.loadingLayoutResId != 0) addLayoutResId(mStatusLayoutManager.loadingLayoutResId, RootFrameLayout.LAYOUT_LOADING_ID);

        if (mStatusLayoutManager.emptyDataVs != null) addView(mStatusLayoutManager.emptyDataVs);
        if (mStatusLayoutManager.errorVs != null) addView(mStatusLayoutManager.errorVs);
        if (mStatusLayoutManager.netWorkErrorVs != null) addView(mStatusLayoutManager.netWorkErrorVs);
    }

    private void addLayoutResId(@LayoutRes int layoutResId, int id) {
        View resView = LayoutInflater.from(mStatusLayoutManager.context).inflate(layoutResId, null);
        mLayoutSparseArray.put(id, resView);

        addView(resView);
    }

    public void showLoading() {
        if (mLayoutSparseArray.get(LAYOUT_LOADING_ID) != null)
            showHideViewById(LAYOUT_LOADING_ID);
    }

    public void showContent() {
        if (mLayoutSparseArray.get(LAYOUT_CONTENT_ID) != null)
            showHideViewById(LAYOUT_CONTENT_ID);
    }

    public void showEmptyData(int iconImage, String textTip) {
        if (inflateLayout(LAYOUT_EMPTYDATA_ID)) {
            showHideViewById(LAYOUT_EMPTYDATA_ID);
            emptyDataViewAddData(iconImage, textTip);
        }
    }

    private void emptyDataViewAddData(int iconImage, String textTip) {
        if (iconImage == 0 && ObjectUtils.isEmpty(textTip)) return;

        View emptyDataView = mLayoutSparseArray.get(LAYOUT_EMPTYDATA_ID);
        View iconImageView = emptyDataView.findViewById(mStatusLayoutManager.emptyDataIconImageId);
        View textView = emptyDataView.findViewById(mStatusLayoutManager.emptyDataTextTipId);

        if (iconImageView != null && iconImageView instanceof ImageView) {
            ((ImageView) iconImageView).setImageResource(iconImage);
        }

        if (textView != null && textView instanceof TextView) {
            ((TextView) textView).setText(textTip);
        }
    }

    public void showLayoutEmptyData(Object... objects) {
        if (inflateLayout(LAYOUT_EMPTYDATA_ID)) {
            showHideViewById(LAYOUT_EMPTYDATA_ID);

            AbsLayout emptyDataLayout = mStatusLayoutManager.emptyDataLayout;
            if (emptyDataLayout != null)
                emptyDataLayout.setData(objects);
        }
    }

    public void showNetWorkError() {
        if (inflateLayout(LAYOUT_NETWORK_ERROR_ID))
            showHideViewById(LAYOUT_NETWORK_ERROR_ID);
    }

    public void showError(int iconImage, String textTip) {
        if (inflateLayout(LAYOUT_ERROR_ID)) {
            showHideViewById(LAYOUT_ERROR_ID);
            errorViewAddData(iconImage, textTip);
        }
    }

    private void errorViewAddData(int iconImage, String textTip) {
        if (iconImage == 0 && TextUtils.isEmpty(textTip)) return;

        View errorView = mLayoutSparseArray.get(LAYOUT_ERROR_ID);
        View iconImageView = errorView.findViewById(mStatusLayoutManager.emptyDataIconImageId);
        View textView = errorView.findViewById(mStatusLayoutManager.emptyDataTextTipId);

        if (iconImageView != null && iconImageView instanceof ImageView) {
            ((ImageView) iconImageView).setImageResource(iconImage);
        }

        if (textView != null && textView instanceof TextView) {
            ((TextView) textView).setText(textTip);
        }
    }

    public void showLayoutError(Object... objects) {
        if (inflateLayout(LAYOUT_ERROR_ID)) {
            showHideViewById(LAYOUT_ERROR_ID);

            AbsLayout errorLayout = mStatusLayoutManager.errorLayout;
            if (errorLayout != null)
                errorLayout.setData(objects);
        }
    }

    private void showHideViewById(int id) {
        for (int i = 0, size = mLayoutSparseArray.size(); i < size; i++) {
            int key = mLayoutSparseArray.keyAt(i);
            View valueView = mLayoutSparseArray.valueAt(i);

            if (key == id) {
                valueView.setVisibility(View.VISIBLE);
                if (mStatusLayoutManager.onShowHideViewListener != null)
                    mStatusLayoutManager.onShowHideViewListener.onShowView(valueView, key);
            } else {
                if (valueView.getVisibility() != View.GONE) {
                    valueView.setVisibility(View.GONE);
                    if (mStatusLayoutManager.onShowHideViewListener != null)
                        mStatusLayoutManager.onShowHideViewListener.onHideView(valueView, key);
                }
            }
        }
    }

    private boolean inflateLayout(int id) {
        boolean isShow = true;
        if (mLayoutSparseArray.get(id) != null) return isShow;

        switch (id) {
            case LAYOUT_NETWORK_ERROR_ID:
                if (mStatusLayoutManager.netWorkErrorVs != null) {
                    View view = mStatusLayoutManager.netWorkErrorVs.inflate();
                    retryLoad(view, mStatusLayoutManager.netWorkErrorRetryViewId);
                    mLayoutSparseArray.put(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_ERROR_ID:
                if (mStatusLayoutManager.errorVs != null) {
                    View view = mStatusLayoutManager.errorVs.inflate();
                    if (mStatusLayoutManager.errorLayout != null)
                        mStatusLayoutManager.errorLayout.setView(view);
                    retryLoad(view, mStatusLayoutManager.errorRetryViewId);
                    mLayoutSparseArray.put(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_EMPTYDATA_ID:
                if (mStatusLayoutManager.emptyDataVs != null) {
                    View view = mStatusLayoutManager.emptyDataVs.inflate();
                    if (mStatusLayoutManager.emptyDataLayout != null)
                        mStatusLayoutManager.emptyDataLayout.setView(view);
                    retryLoad(view, mStatusLayoutManager.emptyDataRetryViewId);
                    mLayoutSparseArray.put(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
        }
        return isShow;
    }

    private void retryLoad(View view, int id) {
        View retryView = view.findViewById(mStatusLayoutManager.retryViewId != 0 ? mStatusLayoutManager.retryViewId : id);
        if (retryView == null || mStatusLayoutManager.onRetryListener == null) return;

        retryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusLayoutManager.onRetryListener.onRetry();
            }
        });
    }
}

