package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2024.cookierun.R;
import kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.paused.PausedScene;
import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2024.framework.objects.HorzScrollBackground;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;

public class MainScene extends Scene {

    public enum Layer {
        bg, platform, item, obstacle, player, ui, HitBar , enemy ,touch, controller, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker(this));

        HitBar hitBar = new HitBar(R.mipmap.red_01, 4.5f, 12.5f, 9.0f, 1.0f);
        add(Layer.HitBar, hitBar);

        add(Layer.touch, new Button(R.mipmap.red_01, 1.5f, 15.0f, 2.0f, 2.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                remove(Layer.HitBar, hitBar);
                HitBar hitBar = new HitBar(R.mipmap.red_01, 4.5f, 12.5f, 9.0f, 1.0f);
                add(Layer.HitBar, hitBar);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.blue_01, 3.5f, 15.0f, 2.0f, 2.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                remove(Layer.HitBar, hitBar);
                HitBar hitBar = new HitBar(R.mipmap.blue_01, 4.5f, 12.5f, 9.0f, 1.0f);
                add(Layer.HitBar, hitBar);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.green_01, 5.5f, 15.0f, 2.0f, 2.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                remove(Layer.HitBar, hitBar);
                HitBar hitBar = new HitBar(R.mipmap.green_01, 4.5f, 12.5f, 9.0f, 1.0f);
                add(Layer.HitBar, hitBar);
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.yellow_01, 7.5f, 15.0f, 2.0f, 2.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                remove(Layer.HitBar, hitBar);
                HitBar hitBar = new HitBar(R.mipmap.yellow_01, 4.5f, 12.5f, 9.0f, 1.0f);
                add(Layer.HitBar, hitBar);
                return true;
            }
        }));



    }

}
