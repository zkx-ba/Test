package com.laozizuishuai.myp2pview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class RoundProgress extends View {
    Paint paint = new Paint();
    private int roundColor;
    private float roundwidth;
    private int roundProgressColor;
    private int maxProgress = 100;
    private float textSize;
    private int textColor;
    private int progress = 0;

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        //圆环的颜色
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.RED);
        //圆环的宽度
        roundwidth = typedArray.getDimension(R.styleable.RoundProgress_roundwidth, 5);
        //圆环进度的颜色
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgreColor, Color.BLUE);
        //中间进度百分比文字字符串的颜色
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        //中间进度百分比文字字符串的字体大小
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, 15);
        //最大进度
        // maxProgress = typedArray.getInteger(R.styleable.RoundProgress_max, 100);
        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //第一步，绘制最外层的圆
        int center = getWidth() / 2;
        paint.setColor(roundColor);
        paint.setStrokeWidth(roundwidth);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        int radius = (int) (center - roundwidth / 2);//半径
        canvas.drawCircle(center, center, radius, paint);


//绘制正中间的文本
        float sx = 0;
        float sy = 0;
        float textwidth = paint.measureText(progress + "%");
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        canvas.drawText(progress + "%", center - (textwidth / 2), center + textSize / 2, paint);


        //第三步绘制圆弧
        /**
         * 参数解释
         * 1.画弧型时所包含的范围轮廓（内圈轮廓）
         * 2.起始地扫描角度
         * 3.  扫描过的角度
         * 4. 是否包含圆心
         *
         */
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        paint.setColor(roundProgressColor);
        paint.setStrokeWidth(roundwidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, 0, 360 * progress / maxProgress, false, paint);

    }


    public void setProgress(int progress) {
        this.progress = progress;
        if (progress>100){
            this.progress = 100;
        }
        postInvalidate();//重新执行onDraw（）

    }


    //Activity中调用

//    private  int totaProgress = 90; //绘制的总进度（后期网络获取）
//
//        new Thread(new Runnable() {
//        @Override
//        public void run() {
//            int tempProgress = 0;//绘制的当前进度
//            while (tempProgress<=totaProgress){
//                roundProgress.setProgress(tempProgress);（自定义View对象）
//                tempProgress++;
//                SystemClock.sleep(100);
//            }
//        }
//    }).start();
//}

}
