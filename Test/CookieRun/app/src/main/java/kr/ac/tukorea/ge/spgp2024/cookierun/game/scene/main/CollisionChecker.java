package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Enemy;
import kr.ac.tukorea.ge.spgp2024.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2024.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final MainScene scene;

    public CollisionChecker(MainScene scene) {
        this.scene = scene;
    }

    @Override
    public void update(float elapsedSeconds) {
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        for (int e = enemies.size() - 1; e >= 0; e--) {
            Enemy enemy = (Enemy)enemies.get(e);
            ArrayList<IGameObject> bullets = scene.objectsAt(MainScene.Layer.HitBar);
            for (int b = bullets.size() - 1; b >= 0; b--) {
                HitBar hitbar = (HitBar)bullets.get(b);
                if (CollisionHelper.collides(enemy, hitbar)) {
                    //Log.d(TAG, "Collision !!");
                    //scene.remove(MainScene.Layer.HitBar, hitbar);

                    scene.remove(MainScene.Layer.enemy, enemy);
                    //scene.addScore(enemy.getScore());

                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}