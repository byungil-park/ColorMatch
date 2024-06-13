package kr.ac.tukorea.ge.spgp2024.cookierun.game.scene.main;

import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;

public class ObstacleFactory {
    public static final int COUNT = 4;
    public static Obstacle get(char mapChar, float left, float top) {
        Obstacle obs = null;
        int index = 0;
        switch (mapChar) {
            case 'X':
                obs = (Obstacle) RecycleBin.get(Obstacle.class);
                if (obs == null) {
                    obs = new Obstacle();
                }
                break;
            case 'Y': case 'Z':
                obs = (Obstacle) RecycleBin.get(AnimObstacle.class);
                if (obs == null) {
                    obs = new AnimObstacle();
                }
                index = mapChar - 'Y';
                break;
            case 'W':
                obs = (Obstacle) RecycleBin.get(FallingObstacle.class);
                if (obs == null) {
                    obs = new FallingObstacle();
                }
                break;
            default: // 위 case 가 아니라면 이 mapChar 는 Obstacle 이 아니다
                return null;
        }
        obs.init(index, left, top);
        return obs;
    }
}

