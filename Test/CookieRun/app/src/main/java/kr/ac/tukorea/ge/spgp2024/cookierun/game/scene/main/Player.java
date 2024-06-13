package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.JsonReader;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.ge.spgp2024.cookierun.R;
import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.objects.SheetSprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class Player extends SheetSprite implements IBoxCollidable {
    public enum State {
        running, jump, doubleJump, falling, slide, hurt, COUNT
    }
    private float jumpSpeed;
    //private static final float JUMP_POWER = 9.0f;
    private static final float GRAVITY = 17.0f;
    private final RectF collisionRect = new RectF();
    protected State state = State.running;
    protected Obstacle obstacle;
    private int imageSize = 0;

    public static class CookieInfo {
        public int id;
        public String name;
        public float jumpPower, scoreRate;
    }
    public static int[] COOKIE_IDS;
    public static HashMap<Integer, CookieInfo> cookieInfoMap;
    private final CookieInfo cookieInfo;
    public static void load(Context context) {
        if (cookieInfoMap != null) return;
        ArrayList<Integer> idArrayList = new ArrayList<>();
        AssetManager assets = context.getAssets();
        try {
            InputStream is = assets.open("cookies.json");
            InputStreamReader isr = new InputStreamReader(is);
            JsonReader jr = new JsonReader(isr);
            jr.beginArray();
            cookieInfoMap = new HashMap<>();
            while (jr.hasNext()) {
                CookieInfo ci = new CookieInfo();
                jr.beginObject();
                while (jr.hasNext()) {
                    String name = jr.nextName();
                    switch (name) { // Java 에서는 String 으로 switch-case 가 가능하다
                        case "id":
                            ci.id = jr.nextInt();
                            break;
                        case "name":
                            ci.name = jr.nextString();
                            break;
                        case "jumpPower":
                            ci.jumpPower = (float) jr.nextDouble();
                            break;
                        case "scoreRate":
                            ci.scoreRate = (float) jr.nextDouble();
                            break;
                    }
                }
                jr.endObject();
                if (ci.id == 0) break;
                cookieInfoMap.put(ci.id, ci);
                idArrayList.add(ci.id);
            }
            jr.endArray();
            jr.close();
            COOKIE_IDS = new int[idArrayList.size()];
            for (int i = 0; i < COOKIE_IDS.length; i++) {
                COOKIE_IDS[i] = idArrayList.get(i);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected Rect[][] srcRectsArray;
    private void makeSourceRects() {
        srcRectsArray = new Rect[][] {
                makeRects(100, 101, 102, 103), // State.running
                makeRects(7, 8),               // State.jump
                makeRects(1, 2, 3, 4),         // State.doubleJump
                makeRects(0),                  // State.falling
                makeRects(9, 10),              // State.slide
                makeRects(503, 504),           // State.hurt
        };
    }
    protected static float[][] edgeInsets = {
            { 1.20f, 1.95f, 1.10f, 0.00f }, // State.running
            { 1.20f, 2.25f, 1.10f, 0.00f }, // State.jump
            { 1.20f, 2.20f, 1.10f, 0.00f }, // State.doubleJump
            { 1.20f, 1.80f, 1.20f, 0.00f }, // State.falling
            { 0.80f, 2.90f, 0.80f, 0.00f }, // slide
            { 1.20f, 1.95f, 1.10f, 0.00f }, // State.hurt
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = 2 + (idx % 100) * (imageSize + 2);
            int t = 2 + (idx / 100) * (imageSize + 2);
            rects[i] = new Rect(l, t, l + imageSize, t + imageSize);
        }
        return rects;
    }
    public Player(int cookieId) {
        super(0, 8);
        loadSheetFromAsset(cookieId);
        cookieInfo = cookieInfoMap.get(cookieId);
        setAnimationResource(0, 8.0f);
        setPosition(2.0f, 3.0f, 3.86f, 3.86f);
        srcRects = srcRectsArray[state.ordinal()];
        fixCollisionRect();
    }

    private void loadSheetFromAsset(int cookieId) {
        AssetManager assets = GameActivity.activity.getAssets();
        String filename = "cookies/" + cookieId + "_sheet.png";
        Log.d("Player", filename);
        try {
            InputStream is = assets.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            imageSize = (bitmap.getWidth() - 2) / 11 - 2;
            Log.d("Player", "imageSize=" + imageSize);
            makeSourceRects();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(float elapsedSeconds) {
        switch (state) {
        case jump:
        case doubleJump:
        case falling:
            float dy = jumpSpeed * elapsedSeconds;
            jumpSpeed += GRAVITY * elapsedSeconds;
            if (jumpSpeed >= 0) { // 낙하하고 있다면 발밑에 땅이 있는지 확인한다
                float foot = collisionRect.bottom;
                float floor = findNearestPlatformTop(foot);
                if (foot + dy >= floor) {
                    dy = floor - foot;
                    setState(State.running);
                }
            }
            y += dy;
            dstRect.offset(0, dy);
            break;
        case running:
        case slide:
            float foot = collisionRect.bottom;
            float floor = findNearestPlatformTop(foot);
            if (foot < floor) {
                setState(State.falling);
                jumpSpeed = 0;
            }
            break;
        case hurt:
            if (!CollisionHelper.collides(this, obstacle)) {
                setState(State.running);
                obstacle = null;
            }
            break;
        }

        if (magSpeed != 0) {
            scale += elapsedSeconds * magSpeed;
            if (magSpeed < 0 && scale <= SCALE_NORMAL) {
                magSpeed = 0;
                scale = SCALE_NORMAL;
            } else if (magSpeed > 0 && scale >= SCALE_MAGNIFIED) {
                magSpeed = 0;
                scale = SCALE_MAGNIFIED;
            }
            float bot = dstRect.bottom;
            float hw = width / 2 * scale;
            float left = x - hw, right = x + hw;
            float top = bot - height * scale;
            dstRect.set(left, top, right, bot);
        }

        fixCollisionRect();
    }
    private float findNearestPlatformTop(float foot) {
        Platform platform = findNearestPlatform(foot);
        if (platform == null) return Metrics.height;
        return platform.getCollisionRect().top;
    }

    private Platform findNearestPlatform(float foot) {
        Platform nearest = null;
        MainScene scene = (MainScene) Scene.top();
        if (scene == null) return null;
        ArrayList<IGameObject> platforms = scene.objectsAt(MainScene.Layer.platform);
        float top = Metrics.height;
        for (IGameObject obj: platforms) {
            Platform platform = (Platform) obj;
            RectF rect = platform.getCollisionRect();
            if (rect.left > x || x > rect.right) {
                continue;
            }
            //Log.d(TAG, "foot:" + foot + " platform: " + rect);
            if (rect.top < foot) {
                continue;
            }
            if (top > rect.top) {
                top = rect.top;
                nearest = platform;
            }
            //Log.d(TAG, "top=" + top + " gotcha:" + platform);
        }
        return nearest;
    }

    private void fixCollisionRect() {
        float[] insets = edgeInsets[state.ordinal()];
        collisionRect.set(
                dstRect.left + scale * insets[0],
                dstRect.top + scale * insets[1],
                dstRect.right - scale * insets[2],
                dstRect.bottom -  scale * insets[3]);
    }
    private void setState(State state) {
        this.state = state;
        srcRects = srcRectsArray[state.ordinal()];
    }

    public void jump() {
        if (state == State.running) {
            jumpSpeed = -cookieInfo.jumpPower;
            setState(State.jump);
        } else if (state == State.jump) {
            jumpSpeed = -cookieInfo.jumpPower;
            setState(State.doubleJump);
        }
    }
    public void fall() {
        if (state != State.running) return;
        float foot = collisionRect.bottom;
        Platform platform = findNearestPlatform(foot);
        if (platform == null) return;
        if (!platform.canPass()) return;
        setState(State.falling);
        dstRect.offset(0, 0.001f);
        collisionRect.offset(0, 0.001f);
        jumpSpeed = 0;
    }
    public void slide(boolean startsSlide) {
        if (state == State.running && startsSlide) {
            setState(State.slide);
            fixCollisionRect();
            return;
        }
        if (state == State.slide && !startsSlide) {
            setState(State.running);
            fixCollisionRect();
            //return;
        }
    }
    public void hurt(Obstacle obstacle) {
        if (state == State.hurt) return;
        setState(State.hurt);
        fixCollisionRect();
        this.obstacle = obstacle;
    }

    private static final float SCALE_NORMAL = 1.0f;
    private static final float SCALE_MAGNIFIED = 2.0f;
    private float scale = 1.0f, magSpeed = 0;
    public void magnify(boolean enlarges) {
//        magSpeed = enlarges ? 1.0f : -1.0f;
        magSpeed = scale == 1.0f ? 1.0f : -1.0f;
        Log.d("Player", "Scale="+scale+" magSpeed="+magSpeed);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
