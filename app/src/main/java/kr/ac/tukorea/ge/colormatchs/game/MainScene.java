package kr.ac.tukorea.ge.colormatchs.game;

import kr.ac.tukorea.ge.colormatchs.R;
import kr.ac.tukorea.ge.colormatchs.framework.scene.Scene;
public class MainScene extends Scene {
    public enum Layer {
        Button
        }
    public MainScene() {
        add(Layer.Button, new ChangeButton());
    }
}