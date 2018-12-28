package com.yzd.create.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yzd.create.R;

import java.util.List;

/**
 * Author: yangzhidan
 * Create: 2018/12/28
 * Remark: 九宫格
 */
public class NineGridView extends ViewGroup {

    private Context mContext;

    //子View的宽度
    private int childWidth;
    //子View的高度
    private int childHeight;
    //子View列数
    private int column = 3;
    //子View行数
    private int row;
    //子View的间距
    private int space;
    //当size为4时显示田字样式
    private boolean isAlignment;

    private List<String> urls;

    private OnNineClickListener onNineListener;

    public NineGridView(Context context) {
        this(context, null);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NineGridView, defStyleAttr, 0);
        isAlignment = a.getBoolean(R.styleable.NineGridView_nineAlignment, false);
        space = (int) a.getDimension(R.styleable.NineGridView_nineSpace, dip2px(mContext, space));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = measureWidth(widthMeasureSpec);

        int height = measureHeight(heightMeasureSpec);

        setMeasuredDimension(width, height);

    }

    /**
     * MeasureSpec.AT_MOST : WRAP_CONTENT
     * MeasureSpec.EXACTLY : MATCH_PARENT
     *
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        //获取测量的模式
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取测量结果
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        //宽高都是设置的WRAP_CONTENT
        if (specMode == MeasureSpec.AT_MOST) {
            //宽度必须为MATCH_PARENT或准确值
//            throw new RuntimeException("Need accurate dimensions for EXCTLY mode");
        } else if (specMode == MeasureSpec.EXACTLY) {
            //宽设置为WRAP_CONTENT,高度为精准模式（EXACTLY）
            result = specSize - getPaddingLeft() - getPaddingRight();
            if (null != urls) {
                //4个子View时分为 两行两列
                if (urls.size() == 4 && isAlignment) {
                    column = 2;
                } else {
                    column = 3;
                }
                //计算每个Child的宽度。
                childWidth = (result - (column - 1) * space) / column;
                childHeight = childWidth;
            }
        }
        Log.e("NINE :", "测量的宽： " + result);
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        //获取测量的模式
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取测量结果
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        //根据已分好的列数计算行数
        if (null != urls && column != 0) {
            row = urls.size() % column == 0 ? urls.size() / column : urls.size() / column + 1;
        }
        if (row > 0) {

            // 固定模式下需要计算子View高度
            if (specMode == MeasureSpec.EXACTLY) {
                result = specSize - getPaddingTop() - getPaddingBottom();
                childHeight = (result - space * (row - 1)) / row;
            } else {//MeasureSpec.AT_MOST 默认每个Child的宽高一致
                result = childHeight * row + space * (row - 1) + getPaddingBottom() + getPaddingBottom();
            }
        } else {
            result = getPaddingBottom() + getPaddingBottom();
        }

        Log.e("NINE :", "测量的高： " + result);
        return result < 0 ? 0 : result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
        for (int i = 0; i < getChildCount(); i++) {

            getChildAt(i).layout(getChildLeft(i), getChildTop(i), getChildLeft(i) + childWidth, getChildTop(i) + childHeight);
        }

    }

    private int getChildLeft(int i) {
        return i % column * space + i % column * childWidth + getPaddingLeft();
//        return 0;
    }

    private int getChildTop(int i) {

        return i / column % row * space + i / column % row * childWidth + getPaddingTop();
    }


    public void setOnNineListener(OnNineClickListener onNineListener) {
        this.onNineListener = onNineListener;
    }

    @SuppressLint("NewApi")
    public void setUrls(List<String> urls) {
        this.urls = urls;
        removeAllViews();
        for (int i = 0; i < urls.size(); i++) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new LayoutParams(childWidth, childHeight));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(mContext).load(urls.get(i)).into(iv);
            iv.setTransitionName("NineView");
            setClick(iv, i);
            addView(iv);
        }
        requestLayout();
    }

    private void setClick(ImageView iv, final int i) {

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onNineListener)
                    onNineListener.onNineListener(v, i, urls.get(i));
            }
        });
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public interface OnNineClickListener {
        void onNineListener(View v, int position, String url);
    }

}
