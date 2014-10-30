package com.roccatello.indecente.view;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.NinePatchDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.roccatello.indecente.R;

/**
 * Created by Eduard on 23/09/2014.
 */
public class PillView extends View {
    private final static int UNPOPPED = 0;
    private final static int POPPED = 1;

    private final static int PILL_SIZE_W = 31;
    private final static int PILL_SIZE_H = 71;

    private final static int X_OFFSET = 28;
    private final static int Y_OFFSET = 24;

    private Bitmap popped;
    private Bitmap unpopped;
    private NinePatchDrawable background;
    private float density = 1;

    private int hPillsNumber = 0;
    private int vPillsNumber = 0;
    private int pillSizeW = PILL_SIZE_W;
    private int pillSizeH = PILL_SIZE_H;
    private int pillOffsetX = X_OFFSET;
    private int pillOffsetY = Y_OFFSET;
    private int xPadding = 0;
    private int yPadding = 0;

    private int totalPills = 0;
    private int pillMatrix[] = null;
    private int counter;
    private long timer;
    private long elapsed;

    private Vibrator vibrator = (Vibrator)getContext().getSystemService(Service.VIBRATOR_SERVICE);
    private SoundPool soundPool;
    private int popSound;

    public PillView(Context context) {
        super(context);
        init();
    }

    public PillView(Context context, AttributeSet as) {
        super(context, as);
        init();
    }

    public PillView(Context context, AttributeSet as, int i) {
        super(context, as, i);
        init();
    }

    private void init() {
        this.totalPills = this.hPillsNumber * this.vPillsNumber;
        this.pillMatrix = new int[this.totalPills];
        this.soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        this.popSound = this.soundPool.load(getContext(), R.raw.pop, 0);
        this.background = (NinePatchDrawable) getContext().getResources().getDrawable(R.drawable.sfondo);
        this.popped = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pill_off);
        this.unpopped =  BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pill_on);

        this.density = getResources().getDisplayMetrics().density;
        this.pillSizeH *= this.density;
        this.pillSizeW *= this.density;
        this.pillOffsetX *= this.density;
        this.pillOffsetY *= this.density;
    }

    public void reset() {
        for (int i = 0; i < this.totalPills; i++) {
            pillMatrix[i] = 0;
        }

        this.counter = 0;
        this.timer = System.currentTimeMillis();

        this.invalidate();
        this.setFocusable(true);
    }

    private void adaptLayout(int w, int h) {
        this.background.setBounds(0, 0, w, h);
        Log.i("DIM", w + " " + h);

        this.hPillsNumber = (w - this.pillOffsetX * 2) / (this.pillSizeW + this.pillOffsetX);
        this.vPillsNumber = (h - this.pillOffsetY * 2) / (this.pillSizeH + this.pillOffsetY);
        this.totalPills = this.hPillsNumber * this.vPillsNumber;
        this.pillMatrix = new int[this.totalPills];

        this.xPadding = (w - this.hPillsNumber * (this.pillSizeW + this.pillOffsetX) + this.pillOffsetX) / 2;
        this.yPadding = (h - this.vPillsNumber * (this.pillSizeH + this.pillOffsetY) + this.pillOffsetY) / 2;

        this.reset();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.adaptLayout(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.background.draw(canvas);
        for (int x = 0; x < this.hPillsNumber; x++) {
            for (int y = 0; y < this.vPillsNumber; y++) {
                if (this.pillMatrix[x + y * this.hPillsNumber] == UNPOPPED)
                    canvas.drawBitmap(unpopped,
                            x * (this.pillSizeW + this.pillOffsetX) + this.xPadding,
                            y * (this.pillSizeH + this.pillOffsetY) + this.yPadding, null);
                else
                    canvas.drawBitmap(popped,
                            x * (this.pillSizeW + this.pillOffsetX) + this.xPadding,
                            y * (this.pillSizeH + this.pillOffsetY) + this.yPadding, null);

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // We are only interested in finger taps
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }

        float x = event.getX();
        int pillX = (int)((x - this.xPadding) / (this.pillSizeW + this.pillOffsetX));
        if (x > (pillX + 1) * (this.pillSizeW + this.pillOffsetX) - this.pillOffsetX + this.xPadding) {
            return true;
        }
        float y = event.getY();
        int pillY = (int)((y - this.yPadding) / (this.pillSizeH + this.pillOffsetY));
        if (y > (pillY + 1) * (this.pillSizeH + this.pillOffsetY) - this.pillOffsetY + this.yPadding) {
            return true;
        }

        // Don't take into account, clicks outside of the region
        if (pillX > this.hPillsNumber - 1 || pillY > this.vPillsNumber - 1)
            return true;

        int location = pillX + pillY * this.hPillsNumber;

        if (pillMatrix[location] == UNPOPPED) {
            pillMatrix[location] = POPPED;

            this.soundPool.play(this.popSound, 1, 1, 1, 0, 1);

            vibrator.vibrate(35);

            counter++;

            this.invalidate();

            if (counter == this.totalPills) {
                elapsed = (System.currentTimeMillis() - timer) / 1000;

                Toast displayResults = Toast.makeText(getContext(), "Congratulazioni Indecente!\nHai preso tutte le pillole in " + elapsed + " secondi!", Toast.LENGTH_LONG);
                displayResults.setGravity(Gravity.CENTER, 0, 0);
                displayResults.show();

                vibrator.vibrate(500);

                this.reset();
            }
        }

        return true;
    }
}
