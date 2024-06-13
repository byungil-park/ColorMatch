package kr.ac.tukorea.ge.colormatchs.game;

import android.view.MotionEvent;
import kr.ac.tukorea.ge.colormatchs.R;
import kr.ac.tukorea.ge.colormatchs.framework.objects.Sprite;
import kr.ac.tukorea.ge.colormatchs.framework.view.Metrics;

public class ChangeButton extends Sprite {

    private static final int[] resId = {
            R.mipmap.red_01, R.mipmap.green_01, R.mipmap.blue_01, R.mipmap.yellow_01,
    };

    private static final float WIDTH = 2.25f; // 사각형의 너비
    private static final float HEIGHT = 2.0f; // 사각형의 높이


    public Runnable runnable;
    public ChangeButton(int colorId) {
        super(colorId);
    }

    public void onTouch(float x, float y) {
        if (dstRect.contains(x, y)) {
            runnable.run();
        }
    }



}
