package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main;

import kr.ac.tukorea.ge.spgp2024.cookierun.R;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;

public class Platform extends MapObject {
    public enum Type {
        T_10x2, T_2x2, T_3x1, COUNT, RANDOM;
        int resId() { return resIds[this.ordinal()]; }
        int width() { return widths[this.ordinal()]; }
        int height() { return heights[this.ordinal()]; }
        static final int[] resIds = {
                R.mipmap.cookierun_platform_480x48,
                R.mipmap.cookierun_platform_124x120,
                R.mipmap.cookierun_platform_120x40,
        };
        static final int[] widths = { 10, 2, 3 };
        static final int[] heights = { 2, 2, 1 };
    }
    private boolean passes;
    private Platform() {
        super(MainScene.Layer.platform);
    }

    public static Platform get(Type type, float left, float top) {
        if (type == Type.RANDOM) {
            type = random.nextInt(2) == 0 ? Type.T_10x2 : Type.T_2x2;
        }
        Platform platform = (Platform) RecycleBin.get(Platform.class);
        if (platform == null) {
            platform = new Platform();
        }
        platform.init(type, left, top);
        return platform;
    }

    public static Platform get(char type, float left, float top) {
        switch (type) {
            case 'O': return get(Type.T_10x2, left, top);
            case 'P': return get(Type.T_2x2, left, top);
            case 'Q': return get(Type.T_3x1, left, top);
        }
        return null;
    }

    private void init(Type type, float left, float top) {
        bitmap = BitmapPool.get(type.resId());
        width = type.width();
        height = type.height();
        dstRect.set(left, top, left + width, top + height);
        passes = type == Type.T_3x1;
    }

    public boolean canPass() {
        return passes;
    }
}
