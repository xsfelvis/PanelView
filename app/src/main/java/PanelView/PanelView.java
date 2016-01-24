package PanelView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import util.PxUtils;
import util.StringUtil;

/**
 * Created by Administrator on 2016/1/5.
 */
public class PanelView extends View {

    private int mPercent; //seekBar传入的值

    private int mArcWidth;//第一个弧的宽度
    private int mScendArcWidth;//第二个弧的宽度


    private String mText = ""; //文字内容
    private String speed = "";//速度显示
    private String unit = "";//显示单位


    private int mTextSize;//文字的大小

    //设置文字颜色
    private int mTextColor;
    private int mArcColor;

    //圆弧渐变色
    private int acrStartColor;
    private int acrEndColor;

    //小圆和指针颜色
    private int mPointerColor;

    //刻度的个数
    private int mTikeCount;

    //画笔
    private Paint paintOuter_Arc;//外圈弧画笔
    private Paint paintouter_Num;//外弧的刻度的画笔
    private Paint paintInerArc;//内圈白色画笔
    private Paint paintInerArc_tranform;//内圈蓝色画笔
    private Paint paint_centerPoint_Pointer;//内圆画笔
    private Paint paint_text;//文字画笔
    private RectF rectF1, rectF2, rectF3;
    private Shader shader;//渐变器

    private int OFFSET = 80;
    private int START_ARC = 150;
    private int DURING_ARC = 240;
    private int mMinCircleRadius = 15; //中心圆点的半径
    private int mMinRingRadius = 30; //中心圆环的半径

    private Context mContext;

    private PanelViewAttr panelViewattr;

    public PanelView(Context context) {
        this(context, null);
        init(context);
    }

    public PanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;//这里必须在构造器里获取
        panelViewattr = new PanelViewAttr(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        //OFFSET = PxUtils.dpToPx(OFFSET,mContext);

        mArcColor = panelViewattr.getmArcColor();
        mPointerColor = panelViewattr.getmPointerColor();
        mTikeCount = panelViewattr.getmTikeCount();
        mTextSize = panelViewattr.getmTextSize();
        mTextColor = panelViewattr.getTextColor();
        mText = panelViewattr.getmText();
        mArcWidth = panelViewattr.getArcwidth();
        mScendArcWidth = panelViewattr.getmScendArcWidth();
        unit = panelViewattr.getUnit();
        acrStartColor = panelViewattr.getAcrStartColor();
        acrEndColor = panelViewattr.getAcrEndColor();

        // 如果手机版本在4.0以上,则开启硬件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }

        //初始化画笔
        paintOuter_Arc = new Paint();
        paintOuter_Arc.setAntiAlias(true);
        paintOuter_Arc.setColor(mArcColor);
        paintOuter_Arc.setStyle(Paint.Style.STROKE);//空心画笔
        paintOuter_Arc.setStrokeWidth(3);

        paintouter_Num = new Paint();
        paintouter_Num.setAntiAlias(true);
        paintouter_Num.setColor(mArcColor);
        paintouter_Num.setStyle(Paint.Style.FILL);//空心画笔
        paintouter_Num.setStrokeWidth(1);

        paintInerArc = new Paint();
        paintInerArc.setAntiAlias(true);
        paintInerArc.setStrokeWidth(mScendArcWidth);
        paintInerArc.setStyle(Paint.Style.STROKE);
        paintInerArc.setColor(Color.WHITE);

        paintInerArc_tranform = new Paint();
        paintInerArc_tranform.setAntiAlias(true);
        paintInerArc_tranform.setStrokeWidth(mScendArcWidth);
        paintInerArc_tranform.setStyle(Paint.Style.STROKE);

        paint_centerPoint_Pointer = new Paint();
        paint_centerPoint_Pointer.setAntiAlias(true);
        paint_centerPoint_Pointer.setColor(mPointerColor);
        paint_centerPoint_Pointer.setStyle(Paint.Style.FILL);//实心画笔

        paint_text = new Paint();
        paint_text.setAntiAlias(true);
        paint_text.setColor(mTextColor);
        paint_text.setStrokeWidth(1);
        paint_text.setStyle(Paint.Style.FILL);//实心画笔


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int realWidth = startMeasure(widthMeasureSpec);
        int realHeight = startMeasure(heightMeasureSpec);

        setMeasuredDimension(realWidth, realHeight);
    }

    private int startMeasure(int msSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(msSpec);
        int size = MeasureSpec.getSize(msSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = PxUtils.dpToPx(200, mContext);
        }
        //Log.d("xsf", "startMeasure " + result);
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float percent = mPercent / 100f;

        //最外面线条
        drawOutAcr(canvas);
        //绘制刻度
        drawerNum(canvas);

        //绘制粗圆弧
        drawInArc(canvas, percent);

        //绘制中间小圆和圆环
        drawInPoint(canvas);


        //绘制指针
        drawerPointer(canvas, percent);

        //绘制矩形和文字
        drawerRecAndText(canvas, percent);


    }

    private void drawerRecAndText(Canvas canvas, float percent) {
        float length = 0;
        paint_text.setTextSize(mTextSize);

        length = paint_text.measureText(mText);
        canvas.drawText(mText, getWidth() / 2 - length / 2, (float) (getHeight() / 2 * (1 + Math.sqrt(2) / 3)), paint_text);


        paint_text.setTextSize(mTextSize * 1.5f);
        speed = StringUtil.floatFormat(120 * percent) + unit;
        length = paint_text.measureText(speed);

        canvas.drawText(speed, getWidth() / 2 - length / 2, (float) (getHeight() / 2 * (1 + Math.sqrt(2) / 2)), paint_text);

    }

    private void drawerPointer(Canvas canvas, float percent) {
        canvas.save();
        float angel = DURING_ARC * (percent - 0.5f);
        canvas.rotate(angel, getWidth() / 2, getHeight() / 2);//指针与外弧边缘持平
        paint_centerPoint_Pointer.setStrokeWidth(mArcWidth);
        canvas.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2 - mArcWidth * 2 - OFFSET-mScendArcWidth, paint_centerPoint_Pointer);
        canvas.restore();
    }

    private void drawInPoint(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mMinRingRadius, paintOuter_Arc);//中心小圆环
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mMinCircleRadius, paint_centerPoint_Pointer);//中心圆点
    }

    private void drawerNum(Canvas canvas) {
        canvas.save(); //记录画布状态
        canvas.rotate(-(180 - START_ARC + 90), getWidth() / 2, getHeight() / 2);
        float rAngle = DURING_ARC / mTikeCount;
        for (int i = 0; i < mTikeCount + 1; i++) {
            canvas.save(); //记录画布状态
            canvas.rotate(rAngle * i, getWidth() / 2, getHeight() / 2);
            canvas.drawLine(getWidth() / 2, mArcWidth, getWidth() / 2, 20, paintOuter_Arc);//画刻度线
            canvas.drawText("" + i * 10, getWidth() / 2 - mArcWidth * 2, 40, paintouter_Num);//画刻度
            canvas.restore();
        }
        canvas.restore();

    }

    private void drawInArc(Canvas canvas, float percent) {
        rectF2 = new RectF(mArcWidth + OFFSET, mArcWidth + OFFSET, getWidth() - mArcWidth - OFFSET, getHeight() - mArcWidth - OFFSET);

        canvas.drawArc(rectF2, START_ARC, DURING_ARC, false, paintInerArc);

        rectF3 = new RectF(mArcWidth + OFFSET, mArcWidth + OFFSET, getWidth() - mArcWidth - OFFSET, getHeight() - mArcWidth - OFFSET);
        shader = new LinearGradient(mArcWidth + OFFSET, mArcWidth + OFFSET,
                getWidth() - mArcWidth - OFFSET, getHeight() - mArcWidth - OFFSET, acrStartColor, acrEndColor, Shader.TileMode.REPEAT);
        paintInerArc_tranform.setShader(shader);
        canvas.drawArc(rectF3, START_ARC, percent * DURING_ARC, false, paintInerArc_tranform);
    }

    private void drawOutAcr(Canvas canvas) {
        //最外面线条
        rectF1 = new RectF(mArcWidth, mArcWidth, getWidth() - mArcWidth, getHeight() - mArcWidth);
        canvas.drawArc(rectF1, START_ARC, DURING_ARC, false, paintOuter_Arc);
    }


    /**
     * 设置百分比
     *
     * @param percent
     */
    public void setPercent(int percent) {
        mPercent = percent;
        invalidate();
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        mText = text;
        invalidate();
    }

    /**
     * 设置圆弧颜色
     *
     * @param color
     */

    public void setArcColor(int color) {
        mArcColor = color;
        invalidate();
    }


    /**
     * 设置指针颜色
     *
     * @param color
     */
    public void setPointerColor(int color) {
        mPointerColor = color;
        invalidate();
    }

    /**
     * 设置文字大小
     *
     * @param size
     */
    public void setTextSize(int size) {
        mTextSize = size;
        invalidate();
    }

    /**
     * 设置粗弧的宽度
     *
     * @param width
     */
    public void setArcWidth(int width) {
        mScendArcWidth = width;
        invalidate();
    }

    /**
     * 设置粗弧起始颜色
     *
     * @param acrStartColor
     */
    public void setAcrStartColor(int acrStartColor) {
        this.acrStartColor = acrStartColor;
    }

    /**
     * 设置粗弧结束颜色
     *
     * @param acrEndColor
     */

    public void setAcrEndColor(int acrEndColor) {
        this.acrEndColor = acrEndColor;
    }

    /**
     * 设置字体颜色
     *
     * @param mTextColor
     */
    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    /**
     * 设置单位
     *
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 设置字体mText
     *
     * @param mText
     */
    public void setmText(String mText) {
        this.mText = mText;
    }

    /**
     * 设置弧的颜色
     *
     * @param mArcColor
     */
    public void setmArcColor(int mArcColor) {
        this.mArcColor = mArcColor;
    }
}
