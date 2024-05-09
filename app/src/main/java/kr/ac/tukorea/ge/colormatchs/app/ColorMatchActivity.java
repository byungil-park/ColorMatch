package kr.ac.tukorea.ge.colormatchs.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.colormatchs.game.MainScene;
import kr.ac.tukorea.ge.colormatchs.framework.activity.GameActivity;

public class ColorMatchActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}
