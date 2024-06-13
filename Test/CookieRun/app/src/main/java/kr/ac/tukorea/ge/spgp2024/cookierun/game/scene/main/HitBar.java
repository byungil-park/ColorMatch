package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.cookierun.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;

public class HitBar extends Sprite implements IBoxCollidable {
    private int mipmapId;

    protected RectF collisionRect = new RectF();

    public HitBar(int mipmapId, float x, float y, float width, float height) {
        super(mipmapId, x, y, width, height);
        this.mipmapId = mipmapId;
    }

    public int getMipmapId() {
        return mipmapId;
    }

    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.top > Metrics.height) {
            Scene.top().remove(MainScene.Layer.enemy, this);
        }
        collisionRect.set(dstRect);
        collisionRect.inset(0.11f, 0.11f);
    }

    public RectF getCollisionRect() {
        return collisionRect;
    }
}
