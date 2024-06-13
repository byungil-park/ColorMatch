package kr.ac.tukorea.ge.colormatchs.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.colormatchs.game.MainScene;
import kr.ac.tukorea.ge.colormatchs.framework.activity.GameActivity;
import kr.ac.tukorea.ge.colormatchs.framework.scene.Scene;
import kr.ac.tukorea.ge.colormatchs.framework.view.Metrics;
public class ColorMatchActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Metrics.setGameSize(9, 16);
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}
