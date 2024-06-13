package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.paused;

import android.app.AlertDialog;
import android.content.DialogInterface;

import kr.ac.tukorea.ge.spgp2024.cookierun.R;
import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class PausedScene extends Scene {
    public enum Layer {
        bg, title, touch, COUNT
    }
    private final Sprite title;
    private float angle;
    public PausedScene() {
        initLayers(Layer.COUNT);
        float w = Metrics.width, h = Metrics.height;
        float cx = w / 2, cy = h / 2;
        add(Layer.bg, new Sprite(R.mipmap.trans_50b, cx, cy, w, h));
        add(Layer.bg, new Sprite(R.mipmap.bg_city_landscape, cx, cy, 12.00f, 6.75f));
        title = new Sprite(R.mipmap.cookie_run_title, cx, cy, 3.69f, 1.36f);
        add(Layer.title, title);
        add(Layer.touch, new Button(R.mipmap.btn_resume_n, 14.5f, 1.0f, 2.0f, 0.75f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pop();
                return false;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_exit_n, 8f, 5.5f, 2.667f, 1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new AlertDialog.Builder(GameActivity.activity)
                        .setTitle("Confirm")
                        .setMessage("Do you really want to exit the game?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finishActivity();
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        }));
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        angle += (float) (elapsedSeconds * Math.PI / 4);
        float x = (float) (8.0f + 4.0f * Math.cos(angle));
        float y = (float) (4.5f + 2.0f * Math.sin(angle));
        title.moveTo(x, y);
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
