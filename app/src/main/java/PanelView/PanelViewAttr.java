package PanelView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.xsf.panelview.R;

import util.PxUtils;

/**
 * Created by hzxushangfei on 2016/1/23.
 */
public class PanelViewAttr {
    private int mArcColor;
    private int mPointerColor;
    private int mTikeCount;
    private int mTextSize;
    private String mText = "";
    private int arcwidth;
    private int mScendArcWidth;
    private String unit;//单位
    private int acrStartColor;
    private int acrEndColor;
    private int textColor;


    public PanelViewAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PanelView, defStyleAttr, 0);
        mArcColor = ta.getColor(R.styleable.PanelView_arcColor, context.getResources().getColor(R.color.colorPrimaryDark));
        mPointerColor = ta.getColor(R.styleable.PanelView_pointerColor, context.getResources().getColor(R.color.PointerColor));
        mTikeCount = ta.getInt(R.styleable.PanelView_tikeCount, 12);
        mTextSize = ta.getDimensionPixelSize(PxUtils.spToPx(R.styleable.PanelView_android_textSize, context), 24);
        mText = ta.getString(R.styleable.PanelView_android_text);
        arcwidth = ta.getInt(R.styleable.PanelView_arcWidth, 3);
        mScendArcWidth = ta.getInt(R.styleable.PanelView_secArcWidth, 50);
        unit = ta.getString(R.styleable.PanelView_Unit);
        acrStartColor = ta.getColor(R.styleable.PanelView_AcrStartColor, context.getResources().getColor(R.color.GREEN));
        acrEndColor = ta.getColor(R.styleable.PanelView_AcrEndColor, context.getResources().getColor(R.color.RED));
        textColor = ta.getColor(R.styleable.PanelView_textColor, context.getResources().getColor(R.color.Yellow));
        ta.recycle();
    }


    public int getAcrEndColor() {
        return acrEndColor;
    }

    public int getAcrStartColor() {
        return acrStartColor;
    }

    public int getArcwidth() {
        return arcwidth;
    }

    public int getmArcColor() {
        return mArcColor;
    }

    public int getmPointerColor() {
        return mPointerColor;
    }

    public int getmTikeCount() {
        return mTikeCount;
    }

    public int getmTextSize() {
        return mTextSize;
    }

    public String getmText() {
        return mText;
    }

    public int getmScendArcWidth() {
        return mScendArcWidth;
    }

    public String getUnit() {
        return unit;
    }
    public int getTextColor() {
        return textColor;
    }

}
