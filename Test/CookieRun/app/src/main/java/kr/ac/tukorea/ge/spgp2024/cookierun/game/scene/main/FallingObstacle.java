package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main;

import android.animation.ValueAnimator;
import android.view.animation.BounceInterpolator;

import kr.ac.tukorea.ge.spgp2024.cookierun.R;

public class FallingObstacle extends Obstacle {
    private static final int RES_ID = R.mipmap.epn01_tm01_sda;
    private ValueAnimator animator;

    @Override
    protected void init(int index, float left, float top) {
        init(left, top, RES_ID);

        float destTop = dstRect.top - 1;
        dstRect.offset(0, -dstRect.height());

        initAnimator();
        animator.setFloatValues(dstRect.top, destTop);
        animator.start();
    }

    // Lazy initialization
    private void initAnimator() {
        if (animator != null) return;
        animator = new ValueAnimator();
        animator.setDuration(2000);
        animator.setStartDelay(1300);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(animListener);
    }

    private final ValueAnimator.AnimatorUpdateListener animListener = (ValueAnimator anim) -> {
        float y = (Float)anim.getAnimatedValue();
        dstRect.offsetTo(dstRect.left, y);
    };

    @Override
    public void onRecycle() {
        super.onRecycle();
        animator.end();
    }

    @Override
    public void pause() {
        animator.pause();
    }

    @Override
    public void resume() {
        animator.resume();
    }
}
