package kr.ac.tukorea.ge.colormatchs.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.colormatchs.R;
import kr.ac.tukorea.ge.colormatchs.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.colormatchs.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.colormatchs.framework.scene.Scene;
import kr.ac.tukorea.ge.colormatchs.framework.view.Metrics;

import java.util.ArrayList;
import java.util.Random;

public class MainScene extends Scene {
    public enum Layer {
        bg, Button, HitBar, enemy, COUNT
        }

    private HitBar colorBar;

    public MainScene() {
        initLayers(Layer.COUNT);

        // 첫 번째 버튼 (빨간색)
        ChangeButton redButton = new ChangeButton(R.mipmap.red_01);
        redButton.setPosition(1.5f, 15.0f, 2.0f, 2.0f); // (x, y, width, height)
        add(Layer.Button, redButton);

        // 두 번째 버튼 (파란색)
        ChangeButton blueButton = new ChangeButton(R.mipmap.blue_01);
        blueButton.setPosition(3.5f, 15.0f, 2.0f, 2.0f); // (x, y, width, height)
        add(Layer.Button, blueButton);

        // 두 번째 버튼 (파란색)
        ChangeButton greenButton = new ChangeButton(R.mipmap.green_01);
        greenButton.runnable = new Runnable() {
            @Override
            public void run() {
              /////
            }
        };
        greenButton.setPosition(5.5f, 15.0f, 2.0f, 2.0f); // (x, y, width, height)
        add(Layer.Button, greenButton);

        // 두 번째 버튼 (파란색)
        ChangeButton yellowButton = new ChangeButton(R.mipmap.yellow_01);
        yellowButton.runnable = ()->{
            //////
        };
        yellowButton.setPosition(7.5f, 15.0f, 2.f, 2.0f); // (x, y, width, height)
        add(Layer.Button, yellowButton);

        // HitBar 객체 생성 및 추가
        colorBar = new HitBar(R.mipmap.red_01);
        colorBar.setPosition(4.5f, 12.5f, 9.0f, 1.0f);
        add(Layer.HitBar, colorBar);

    }

    @Override
    public boolean onTouch(MotionEvent event) {
        float[] pts = Metrics.fromScreen(event.getX(), event.getY());
        ArrayList<IGameObject> buttons = objectsAt(Layer.Button);
        for (IGameObject obj: buttons) {
            ChangeButton btn = (ChangeButton) obj;
            btn.onTouch(pts[0], pts[1]);
           if (btn.ptInRect(pts[0], pts[1])) {
                btn.runnable.run();
            }
        }
        return false;
    }
}