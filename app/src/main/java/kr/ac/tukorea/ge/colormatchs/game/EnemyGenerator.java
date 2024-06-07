package kr.ac.tukorea.ge.colormatchs.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.colormatchs.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.colormatchs.framework.scene.Scene;

public class EnemyGenerator implements IGameObject {
    private final Random random = new Random();
    private float enemyTime = 0;
    @Override
    public void update(float elapsedSeconds) {
        enemyTime -= elapsedSeconds;
        if (enemyTime < 0) {
            int level = random.nextInt(10);
            int index = random.nextInt(5);
            //Scene.top().add(new Enemy(level, index));
            enemyTime = random.nextFloat() + 0.5f;
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}