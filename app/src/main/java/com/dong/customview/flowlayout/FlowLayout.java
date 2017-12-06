package com.dong.customview.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/11/18.
 */
public class FlowLayout extends ViewGroup {

    int mWidth, mHeight;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        //当前行宽
        int currWidthLength = 0;
        int childWidth, childHeight;
        int maxWidthLength = 0, maxHeightLength = 0, totalHeight = 0;
        MarginLayoutParams params;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //如果为gone不用测量
            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            params = (MarginLayoutParams) childView.getLayoutParams();

            //测量子view
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            childWidth = childView.getWidth() + params.leftMargin + params.rightMargin;
            childHeight = childView.getHeight() + params.topMargin + params.bottomMargin;
            //如果子view再排一个view就超过当前最大行宽就换行
            if (currWidthLength + childWidth > wSize + getPaddingLeft() + getPaddingRight()) {
                //比较当前最大宽度与历史最大宽度，拿到最大宽度
                maxWidthLength = Math.max(maxWidthLength, currWidthLength);
                //总高度递增
                totalHeight += maxHeightLength;
                //重置最大行高
                maxHeightLength = childHeight;
                //重置最大行宽
                currWidthLength = childWidth;
            } else {
                //还没达到最大行宽
                //当前宽度加上当前子view宽度
                currWidthLength += childWidth;
                //比较行高
                maxHeightLength = Math.max(maxHeightLength, childHeight);
            }

            if (i == childCount - 1) {
                //最后一条数据
                maxWidthLength = Math.max(maxWidthLength, currWidthLength);
                totalHeight += maxHeightLength;
            }
        }

        switch (wMode) {
            case MeasureSpec.AT_MOST:
                mWidth = maxWidthLength + getPaddingRight() + getPaddingLeft();
                break;
            case MeasureSpec.EXACTLY:
                mWidth = wSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                mWidth = maxWidthLength + getPaddingRight() + getPaddingLeft();
                break;
            default:
                break;
        }

        switch (hMode) {
            case MeasureSpec.AT_MOST:
                mHeight = maxHeightLength + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                mHeight = hSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                mHeight = maxHeightLength + getPaddingTop() + getPaddingBottom();
                break;
            default:
                break;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childCount=getChildCount();
        int currWidthLength=0;
        int childWidth=0;
        int childHeight;
        int maxHeightLength = 0;
        int totalHeightLength = 0;
        MarginLayoutParams params;

        int mLeft = 0,mTop = 0,mRight = 0,mBottom = 0;

        for (int j=0;j<childCount;j++){
            View childView=getChildAt(j);
            if (childView.getVisibility()==View.GONE){
                continue;
            }
            params= (MarginLayoutParams) childView.getLayoutParams();
            childWidth=childView.getMeasuredWidth()+params.rightMargin+params.leftMargin;
            childHeight=childView.getMeasuredHeight()+params.topMargin+params.bottomMargin;

            if (currWidthLength+childWidth>getWidth()-getPaddingLeft()-getPaddingRight()){

                totalHeightLength+=childHeight;
                currWidthLength=childWidth;
                maxHeightLength=childHeight;

                mLeft=getPaddingLeft()+params.leftMargin;
                mTop=totalHeightLength+getPaddingTop()+params.topMargin;
                mRight=mLeft+childView.getMeasuredWidth();
                mBottom=mTop+childView.getMeasuredHeight();

            }else {

                mLeft=currWidthLength+getPaddingLeft()+params.leftMargin;
                mTop=totalHeightLength+getPaddingTop()+params.topMargin;
                mRight=mLeft+childView.getMeasuredWidth();
                mBottom=mTop+childView.getMeasuredHeight();

                currWidthLength+=childWidth;
                maxHeightLength=Math.max(maxHeightLength,childHeight);

            }
            childView.layout(mLeft,mTop,mRight,mBottom);
        }

    }
}
