package kr.ac.tukorea.ge.colormatchs.game;

import kr.ac.tukorea.ge.colormatchs.R;
import kr.ac.tukorea.ge.colormatchs.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.colormatchs.framework.scene.Scene;

import java.util.Random;

public class MainScene extends Scene {
    public enum Layer {
        bg, Button, HitBar, enemy, COUNT
        }
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
        greenButton.setPosition(5.5f, 15.0f, 2.0f, 2.0f); // (x, y, width, height)
        add(Layer.Button, greenButton);

        // 두 번째 버튼 (파란색)
        ChangeButton yellowButton = new ChangeButton(R.mipmap.yellow_01);
        yellowButton.setPosition(7.5f, 15.0f, 2.f, 2.0f); // (x, y, width, height)
        add(Layer.Button, yellowButton);

        HitBar ColorBar = new HitBar(R.mipmap.red_01);
        ColorBar.setPosition(4.5f, 12.5f, 9.0f, 1.0f);
        add(Layer.HitBar, ColorBar);

    }

}