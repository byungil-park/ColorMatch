package kr.ac.tukorea.ge.colormatchs.game;

import kr.ac.tukorea.ge.colormatchs.R;
import kr.ac.tukorea.ge.colormatchs.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.colormatchs.framework.scene.Scene;

public class MainScene extends Scene {
    public enum Layer {
        bg, Button, COUNT
        }
    public MainScene() {
        initLayers(Layer.COUNT);


    }
}