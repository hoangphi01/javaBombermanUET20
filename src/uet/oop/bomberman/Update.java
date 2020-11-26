package uet.oop.bomberman;

import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Update {
    void updateUp(MovingEntity bomber, int cnt){
        int dx = 0;
        int dy = -5;
        if (!bomber.check(dx, dy)) {
            dx = 0;
            dy = 0;
        }
        if (cnt == 0)
            bomber.update(dx, dy, Sprite.player_up.getFxImage());
        if (cnt == 1)
            bomber.update(dx, dy, Sprite.player_up_1.getFxImage());
        if (cnt == 2)
            bomber.update(dx, dy, Sprite.player_up_2.getFxImage());
        if (cnt == 3)
            bomber.update(dx, dy, Sprite.player_up_3.getFxImage());
    }
    void updateDown(MovingEntity bomber, int cnt){
        int dx = 0;
        int dy = 5;
        if (!bomber.check(dx, dy)) {
            dx = 0;
            dy = 0;
        }
        if (cnt == 0)
            bomber.update(dx, dy, Sprite.player_down.getFxImage());
        if (cnt == 1)
            bomber.update(dx, dy, Sprite.player_down_1.getFxImage());
        if (cnt == 2)
            bomber.update(dx, dy, Sprite.player_down_2.getFxImage());
        if (cnt == 3)
            bomber.update(dx, dy, Sprite.player_down_3.getFxImage());
    }
    void updateRight(MovingEntity bomber, int cnt) {
        int dx = 5;
        int dy = 0;
        if (!bomber.check(dx, dy)) {
            dx = 0;
            dy = 0;
        }
        if (cnt == 0)
            bomber.update(dx, dy, Sprite.player_right.getFxImage());
        if (cnt == 1)
            bomber.update(dx, dy, Sprite.player_right_1.getFxImage());
        if (cnt == 2)
            bomber.update(dx, dy, Sprite.player_right_2.getFxImage());
        if (cnt == 3)
            bomber.update(dx, dy, Sprite.player_right_3.getFxImage());
    }
    void updateLeft(MovingEntity bomber, int cnt) {
        int dx = -5;
        int dy = 0;
        if (!bomber.check(dx, dy)) {
            dx = 0;
            dy = 0;
        }
        if (cnt == 0)
            bomber.update(dx, dy, Sprite.player_left.getFxImage());
        if (cnt == 1)
            bomber.update(dx, dy, Sprite.player_left_1.getFxImage());
        if (cnt == 2)
            bomber.update(dx, dy, Sprite.player_left_2.getFxImage());
        if (cnt == 3) {
            bomber.update(dx, dy, Sprite.player_left_3.getFxImage());
        }
    }

    int updateBombExplode(MovingEntity bomb, int cnt, int x, int y) {
        if (cnt == 0) {
            return 0;
        }
        if (cnt == 1) {
            return 0;
        }
        if (cnt == 2) {
            return 0;
        }
        if (cnt == 3) {
            return 0;
        }
        return 0;
    }

    void updateBomb(MovingEntity bomb, int cnt, int x, int y) {
        if (cnt < 15 || (cnt >= 60 && cnt < 75)) {
            bomb.update(x, y, Sprite.bomb.getFxImage());
        }
        else if (cnt < 30 || (cnt >= 75 && cnt < 90)) {
            bomb.update(x, y, Sprite.bomb_1.getFxImage());
        }
        else if (cnt < 45 || (cnt >= 90 && cnt < 105)) {
            bomb.update(x, y, Sprite.bomb_2.getFxImage());
        }
        else if (cnt < 60 || (cnt >= 105 && cnt < 120)) {
            bomb.update(x, y, Sprite.bomb_3.getFxImage());
        }
    }
}
