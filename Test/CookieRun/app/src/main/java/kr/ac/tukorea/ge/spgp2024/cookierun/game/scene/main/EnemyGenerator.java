package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Enemy;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;

public class EnemyGenerator implements IGameObject {
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    public static final float GEN_INTERVAL = 5.0f;
    private final Random random = new Random();
    private float enemyTime = 0;
    private int wave;
    @Override
    public void update(float elapsedSeconds) {
        enemyTime -= elapsedSeconds;
        if (enemyTime < 0) {
            generate();
            enemyTime = GEN_INTERVAL;
        }
    }

    private void generate() {
        Scene scene = Scene.top();
        if (scene == null) return;

        wave++;
        //Log.v(TAG, "Generating: wave " + wave);
        int level = (wave + 15) / 10 - random.nextInt(3);
        if (level < 0) level = 0;
        if (level > Enemy.MAX_LEVEL) level = Enemy.MAX_LEVEL;
        scene.add(MainScene.Layer.enemy, Enemy.get(level, random.nextInt(5)));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}