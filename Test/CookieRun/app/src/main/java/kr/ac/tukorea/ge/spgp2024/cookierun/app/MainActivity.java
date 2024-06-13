package kr.ac.tukorea.ge.spgp2024.cookierun.app;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

import kr.ac.tukorea.ge.spgp2024.cookierun.BuildConfig;
import kr.ac.tukorea.ge.spgp2024.cookierun.R;
import kr.ac.tukorea.ge.spgp2024.cookierun.databinding.ActivityMainBinding;
import kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main.MainScene;
import kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main.Player;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int stage, cookieIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void runGameActivity() {
        Intent intent = new Intent(this, CookieRunActivity.class);
        startActivity(intent);
    }

    public void onBtnStartGame(View view) {
        runGameActivity();
    }
}