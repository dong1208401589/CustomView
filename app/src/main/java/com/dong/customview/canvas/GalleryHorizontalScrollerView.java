package com.dong.customview.canvas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/12/17.
 */

public class GalleryHorizontalScrollerView extends HorizontalScrollView {

    private LinearLayout container;
    private int centerX;
    private int icon_with;


    public GalleryHorizontalScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryHorizontalScrollerView(Context context) {
        super(context);
        init();
    }

    private void init(){
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        container=new LinearLayout(getContext());
        container.setLayoutParams(layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View v=container.getChildAt(0);
        icon_with=v.getWidth();
        centerX=getWidth()/2;
        centerX=centerX-icon_with/2;
        container.setPadding(centerX,0,centerX,0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
