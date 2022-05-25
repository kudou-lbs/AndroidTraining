package com.lbs.day2_speedometer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SpeedometerView extends View {

    //画圆弧的画笔
    Paint drawablePaint;
    //写文字的画笔
    Paint textPaint;
    int stepCountNum=0;
    double currentSpeed=0;
    String stepCount="0";
    String myPrefix ="截至当前已走";
    String mySuffix="步";
    public int sweepAngle=300;

    public SpeedometerView(Context context) {
        super(context);
        initPaint();
    }

    public SpeedometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        if(attrs!=null){
            //赋值，设置画笔颜色
            TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.SpeedometerView);
            drawablePaint.setColor(typedArray.getColor(R.styleable.SpeedometerView_paintColor,getResources().getColor(R.color.speedometer_background,null)));
            sweepAngle=typedArray.getInt(R.styleable.SpeedometerView_sweepAngle,300);

            textPaint.setTextSize(100);
            textPaint.setColor(typedArray.getColor(R.styleable.SpeedometerView_textColor,getResources().getColor(R.color.white,null)));
        }
    }

    public SpeedometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    //初始化画笔
    private void initPaint(){
        drawablePaint =new Paint();
        drawablePaint.setStyle(Paint.Style.STROKE);
        drawablePaint.setAntiAlias(true);
        drawablePaint.setStrokeWidth(10);
        drawablePaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint=new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x=getWidth()/2;
        int y=getHeight()/2;
        int minLen=Math.min(getWidth(),getHeight());
        int myRadius=minLen/3;

        drawablePaint.setStrokeWidth(minLen/20);
        //画圆测试
        //canvas.drawCircle(x,y,minLen/3,paint);
        RectF rectF=new RectF(x-myRadius,y-myRadius,x+myRadius,y+myRadius);
        canvas.drawArc(rectF,-240,sweepAngle,false, drawablePaint);

        //文字居中显示，使用了Descent和Ascent计算文字y坐标
        float middleTextY=y-(textPaint.descent()+textPaint.ascent())/2;
        textPaint.setTextSize(100);
        canvas.drawText(stepCount,x-textPaint.measureText(stepCount)/2,
                middleTextY,textPaint);
        float middleTextBottom=middleTextY+textPaint.getFontMetrics().bottom;

        //设置前缀：截至当前已走
        float paintTop= y-(textPaint.descent()+textPaint.ascent())/2+textPaint.getFontMetrics().top;
        textPaint.setTextSize(40);
        canvas.drawText(myPrefix,x-textPaint.measureText(myPrefix)/2,
                paintTop-textPaint.getFontMetrics().bottom,textPaint);
        //设置后缀：步
        float nextLineY=middleTextBottom-textPaint.getFontMetrics().top;
        canvas.drawText(mySuffix,x-textPaint.measureText(mySuffix)/2,
                nextLineY,textPaint);
    }

    //设置角度，绘制动画时此函数必须有
    public void setSweepAngle(int sweepAngle) {
        currentSpeed=sweepAngle;
        this.sweepAngle = sweepAngle;
        invalidate();
    }
    public void setStepCount(String stepCount) {
        this.stepCount = stepCount;
        invalidate();
    }
    //设置步数+1
    public void setStepCount() {
        stepCountNum++;
        stepCount=String.valueOf(stepCountNum);
        invalidate();
    }
    //加速度变化时调用，更改当前速度
    public void onSpeedChanged(double a){
        currentSpeed+=a;
        sweepAngle=(int)currentSpeed;
        if(sweepAngle<=0){sweepAngle=0;}
        invalidate();
    }
}
