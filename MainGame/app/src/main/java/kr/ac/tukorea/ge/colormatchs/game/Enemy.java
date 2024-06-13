package kr.ac.tukorea.ge.colormatchs.game;

import kr.ac.tukorea.ge.colormatchs.R;
import kr.ac.tukorea.ge.colormatchs.framework.objects.Sprite;
import kr.ac.tukorea.ge.colormatchs.framework.scene.Scene;
import kr.ac.tukorea.ge.colormatchs.framework.view.Metrics;

public class Enemy extends Sprite {
    private static final float SPEED = 3.0f;
    private static final float RADIUS = 0.9f;

    private static final int[] resIds = {
            R.mipmap.red_01, R.mipmap.green_01, R.mipmap.blue_01, R.mipmap.yellow_01,
    };

    public Enemy(int level, int index) {
        super(resIds[level]);
        setPosition(Metrics.width / 10 * (2 * index + 1), -RADIUS, RADIUS);
        dy = SPEED;
    }
    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.top > Metrics.height) {
            if (dstRect.top > Metrics.height) {
                Scene.top().remove(MainScene.Layer.enemy, this);
            }
        }
    }
}