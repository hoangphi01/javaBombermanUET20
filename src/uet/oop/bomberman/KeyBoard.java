package uet.oop.bomberman;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class KeyBoard {
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
    void updateRight(MovingEntity bomber, int cnt){
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
    int[][] dir = {{0, 5}, {5, 0}, {-5, 0}, {0, -5}};
    int updateBalloom(MovingEntity balloom, int cnt) {
        if (balloom.check(dir[cnt][0], dir[cnt][1])) {
            if (cnt == 0 || cnt == 1)
                balloom.update(dir[cnt][0], dir[cnt][1], Sprite.balloom_right1.getFxImage());
            else
                balloom.update(dir[cnt][0], dir[cnt][1], Sprite.balloom_left1.getFxImage());
            return cnt;
        }
        Random generator = new Random();
        int value = generator.nextInt(4);
        while (!balloom.check(dir[value][0], dir[value][1])) {
            value = generator.nextInt(4);
        }
        if (value == 0 || value == 1)
            balloom.update(dir[value][0], dir[value][1], Sprite.balloom_right1.getFxImage());
        else
            balloom.update(dir[value][0], dir[value][1], Sprite.balloom_left1.getFxImage());
        return value;
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
