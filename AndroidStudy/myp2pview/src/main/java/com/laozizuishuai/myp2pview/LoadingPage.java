package com.laozizuishuai.myp2pview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class LoadingPage extends FrameLayout {

    private static final int PAGE_LOADING_STATE = 1;  //开始加载
    private static final int PAGE_ERROR_STATE = 2;  //加载失败
    private static final int PAGE_EMPTY_STATE = 3;  //返回为空
    private static final int PAGE_SUCCESS_STATE = 3; //加载成功

    private int PAGE_CURRENT = 1 ;//当前状态

    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;

private LayoutParams layoutParams;
    public LoadingPage(Context context) {
        this(context,null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);




    }

    private void init(Context context) {

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        if (loadingView == null){
            loadingView = LayoutInflater.from(context).inflate(R.layout.page_loading,null);
            addView(loadingView,layoutParams);
        }


        if (errorView == null){
            errorView = LayoutInflater.from(context).inflate(R.layout.page_error,null);
            addView(errorView,layoutParams);
        }


        if (emptyView == null){
            emptyView = LayoutInflater.from(context).inflate(R.layout.page_empty,null);
            addView(emptyView,layoutParams);
        }






    }

}
