package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kr.ac.tukorea.ge.spgp2024.cookierun.R;
import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class MapLoader implements IGameObject {
    //private final MainScene scene;
    private final Random random = new Random();
    private float x;
    private int index, mapLength;
    private int columns;
    private ArrayList<String> lines = new ArrayList<>();

    public MapLoader(MainScene scene) {
        //this.scene = scene;
        //int stage = scene.getStage();
        //loadStage(GameActivity.activity, stage);
    }

    private static final int STAGE_HEIGHT = 9;

    // JSON 에 있던 int[] 를 가져온다.

    // 일단 JSON 로드 버전은 사용하지 않는다
    private void loadStage(Context context, int stage) {
        AssetManager assets = context.getAssets();
        try {
            String file = String.format("stage_%02d.txt", stage);
            InputStream is = assets.open(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            columns = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                if (columns == 0) {
                    columns = line.indexOf('|');
                }
                lines.add(line);
            }

            int pages = lines.size() / STAGE_HEIGHT;
            int lastCol = lines.get(lines.size() - 1).length();
            mapLength = (pages - 1) * columns + lastCol;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update(float elapsedSeconds) {
        // x 는 화면의 어디에까지 만들었나를 기억한다. SPEED 가 음수이므로 시간에 따라 감소한다
        x += MapObject.SPEED * elapsedSeconds;
        float left = x + index; // index 는 맵에서 마지막으로 가져온 곳을 기억한다.
        while (left < Metrics.width) {
            createColumn(left);
            index++;
            left += 1.0f;
        }
    }
    // 세로줄 한 줄을 검색하여 생성해야 하는 map object 를 알아내서 생성한다
    private void createColumn(float left) {
        for (int row = 0; row < STAGE_HEIGHT; row++) {
            char tile = getAt(index, row); // 알아내서
            createObject(tile, left, row); // 생성한다
        }
    }
    protected static interface MapObjectCreator {
        MapObject get(char tile, float left, float top);
    }
    protected static List<MapObjectCreator> mapCreators = Arrays.asList(
            JellyItem::get, Platform::get, ObstacleFactory::get
    );
    private void createObject(char tile, float left, float top) {
        MapObject mapObject = null;
        for (MapObjectCreator creator: mapCreators) {
            mapObject = creator.get(tile, left, top);
            if (mapObject != null) break;
        }
        if (mapObject == null) {
            return;
        }

        mapObject.addToScene();
    }

    private char getAt(int col, int row) {
        try {
            int lineIndex = col / columns * STAGE_HEIGHT + row; // 텍스트파일의 몇번째 라인에서 가져와야 하나
            String line = lines.get(lineIndex);
            return line.charAt(col % columns); // 고른 문자열에서 몇번째 글자인가
        } catch (Exception e) {
            return 0; // 계산이 잘못된 경우에는 아무것도 없다고 리턴한다
        }
    }

    Gauge gauge = new Gauge(0.025f, R.color.mapGaugeFg, R.color.mapGaugeBg);
    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(2.0f, 1.0f);
        canvas.scale(12.0f, 14.0f);
        gauge.draw(canvas, (float)index / mapLength);
        canvas.restore();
    }
}
