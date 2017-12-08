package com.dong.customview.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dong.customview.R;

/**
 * Created by dc on 2017/12/8.
 */

public class RoundImageView_DSTIN extends View {

    private Paint mBitPint;
    private Bitmap bmpDSt,bmpSRC;


    public RoundImageView_DSTIN(Context context) {
        this(context,null);
    }

    public RoundImageView_DSTIN(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mBitPint=new Paint();
        bmpDSt= BitmapFactory.decodeResource(context.getResources(),R.mipmap.xyjy6,null);
        bmpSRC=BitmapFactory.decodeResource(context.getResources(),R.mipmap.shade,null);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId=canvas.saveLayer(0,0,getWidth() ,getHeight(),null,Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(bmpDSt,0,0,mBitPint);
        mBitPint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bmpSRC,0,0,mBitPint);
        mBitPint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }
}
