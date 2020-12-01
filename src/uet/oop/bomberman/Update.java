package uet.oop.bomberman;

import uet.oop.bomberman.entities.Flame;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Update {

    final int[][] d = {{0, -5}, {0, 5}, {5, 0}, {-5, 0}};
    int cntBalloom = 0;
    int cntBombExplode = -1;
    int dBalloom = 0;
    int posX;
    int posY;
    int timeBalloom = 0;
    int timeBomb = -1;
    boolean checkBombUp = false;
    boolean checkBombDown = false;
    boolean checkBombRight = false;
    boolean checkBombLeft = false;
    boolean checkEvent = false;

    MovingEntity flameCenter = new Flame();
    MovingEntity flameUp = new Flame();
    MovingEntity flameDown = new Flame();
    MovingEntity flameRight = new Flame();
    MovingEntity flameLeft = new Flame();
    Map map = new Map();

    //Map map = new Map();
    List<MovingEntity> update(List<MovingEntity> entities, int cnt, int dBomber, MovingEntity bomb) {
        if (entities.size() == 2)
            timeBomb = -1;
        else if (timeBomb == -1 && cntBombExplode == -1) {
            timeBomb = 0;
            checkBombUp = false;
            checkBombDown = false;
            checkBombRight = false;
            checkBombLeft = false;
            checkEvent = false;
        }
        if (cnt >= 0 && cnt < 4) {
            if (entities.get(0).check(d[dBomber][0], d[dBomber][1])) {
                entities.get(0).update(d[dBomber][0], d[dBomber][1], Sprite.player[dBomber][cnt].getFxImage());
            }
            else {
                entities.get(0).update(0, 0, Sprite.player[dBomber][cnt].getFxImage());
            }
        }

        if (timeBomb == 0) {
            posX = entities.get(0).getX();
            posY = entities.get(0).getY();
            updateBomb(bomb, timeBomb, posX, posY);
            timeBomb++;
        }
        else if (timeBomb > 0 && timeBomb < 120) {
            if (checkEvent == false && (abs(entities.get(0).getX() - posX) > Sprite.SCALED_SIZE || abs(entities.get(0).getY() - posY) > Sprite.SCALED_SIZE)) {
                map.set(posX /40, posY / 40,'b');
                checkEvent = true;
            }
            updateBomb(bomb, timeBomb, posX, posY);
            timeBomb++;
        }
        //if (checkEvent == true)
          //  System.out.println(map.get(1, 3));
        if (timeBalloom % 10 == 0) {
            cntBalloom++;
        }

        int t = 15;
        if (cntBombExplode >= 0 && cntBombExplode <= t) {
            entities = updateBombExplode(entities, posX, posY, 0);
        }
        else if (cntBombExplode > t && cntBombExplode <= t * 2) {
            entities = updateBombExplode(entities, posX, posY, 1);
        }
        else if (cntBombExplode > t * 2 && cntBombExplode <= t * 3) {
            entities = updateBombExplode(entities, posX, posY, 2);
        }
        else if (cntBombExplode > t * 3 && cntBombExplode <= t * 4){
            entities = updateBombExplode(entities, posX, posY, 3);
        }
        else if (cntBombExplode > t * 4){
            entities.remove(flameCenter);
            entities.remove(flameUp);
            entities.remove(flameDown);
            entities.remove(flameRight);
            entities.remove(flameLeft);
            cntBombExplode = -1;
        }

        if (cntBombExplode >= 0)
            cntBombExplode++;

        if (timeBomb == 120) {
            timeBomb = -1;
            cntBombExplode = 0;
            map.set(posX/40, posY/40, ' ');
            //checkEvent = false;
            flameCenter = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE, Sprite.flame_center[0].getFxImage());
            entities.add(flameCenter);
            if (bomb.check(d[0][0], d[0][1])) {
                flameUp = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE - 1, Sprite.flame_up[0].getFxImage());
                entities.add(flameUp);
                checkBombUp = true;
            }
            if (bomb.check(d[1][0], d[1][1])) {
                flameDown = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE + 1, Sprite.flame_down[0].getFxImage());
                entities.add(flameDown);
                checkBombDown = true;
            }
            if (bomb.check(d[2][0], d[2][1])) {
                flameRight = new Flame(posX / Sprite.SCALED_SIZE + 1, posY / Sprite.SCALED_SIZE, Sprite.flame_right[0].getFxImage());
                entities.add(flameRight);
                checkBombRight = true;
            }
            if (bomb.check(d[3][0], d[3][1])) {
                flameLeft = new Flame(posX / Sprite.SCALED_SIZE - 1, posY / Sprite.SCALED_SIZE, Sprite.flame_left[0].getFxImage());
                entities.add(flameLeft);
                checkBombLeft = true;
            }
            entities.remove(bomb);
        }

        if (timeBalloom % 10 == 0) {
            if (entities.get(1).check(d[dBalloom][0], d[dBalloom][1])) {
                if (dBalloom == 0 || dBalloom == 1) {
                    entities.get(1).update(d[dBalloom][0], d[dBalloom][1], Sprite.balloom_right[cntBalloom%4].getFxImage());
                }
                else {
                    entities.get(1).update(d[dBalloom][0], d[dBalloom][1], Sprite.balloom_left[cntBalloom%4].getFxImage());
                }
                cntBalloom++;
            }
            else {
                Random generator = new Random();
                int newD = generator.nextInt(4);
                while (!entities.get(1).check(d[newD][0], d[newD][1])) {
                    newD = generator.nextInt(4);
                }
                if (newD == 0 || newD == 1)
                    entities.get(1).update(d[newD][0], d[newD][1], Sprite.balloom_right[1].getFxImage());
                else
                    entities.get(1).update(d[newD][0], d[newD][1], Sprite.balloom_left[1].getFxImage());
                dBalloom = newD;
                cntBalloom = 1;
            }
        }
        timeBalloom++;
        return entities;
    }

    void updateBomb(MovingEntity bomb, int time, int x, int y) {
        int t = 15;
        if (time < t || (time >= t * 4 && time < t * 5)) {
            bomb.update(x, y, Sprite.bomb[0].getFxImage());
        }
        else if (time < t * 2 || (time >= t * 5 && time < t * 6)) {
            bomb.update(x, y, Sprite.bomb[1].getFxImage());
        }
        else if (time < t * 3|| (time >= t * 6 && time < t * 7)) {
            bomb.update(x, y, Sprite.bomb[2].getFxImage());
        }
        else if (time < t * 4 || (time >= t * 7 && time < t * 8)) {
            bomb.update(x, y, Sprite.bomb[3].getFxImage());
        }
    }

    List<MovingEntity> updateBombExplode(List<MovingEntity> entities, int posX, int posY, int cnt) {
        entities.remove(flameCenter);
        flameCenter = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE, Sprite.flame_center[cnt].getFxImage());
        entities.add(flameCenter);
        if (checkBombUp) {
            entities.remove(flameUp);
            flameUp = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE - 1, Sprite.flame_up[cnt].getFxImage());
            entities.add(flameUp);
        }

        if (checkBombDown) {
            entities.remove(flameDown);
            flameDown = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE + 1, Sprite.flame_down[cnt].getFxImage());
            entities.add(flameDown);
        }

        if (checkBombRight) {
            entities.remove(flameRight);
            flameRight = new Flame(posX / Sprite.SCALED_SIZE + 1, posY / Sprite.SCALED_SIZE, Sprite.flame_right[cnt].getFxImage());
            entities.add(flameRight);
        }
        if (checkBombLeft) {
            entities.remove(flameLeft);
            flameLeft = new Flame(posX / Sprite.SCALED_SIZE - 1, posY / Sprite.SCALED_SIZE, Sprite.flame_left[cnt].getFxImage());
            entities.add(flameLeft);
        }
        return entities;
    }
}
