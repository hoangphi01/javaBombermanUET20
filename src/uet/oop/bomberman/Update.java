package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Update {

    final int[][] d = {{0, -5}, {0, 5}, {5, 0}, {-5, 0}};
    int[] cntBalloom = {0, 0};
    int[] cntOneal = {0, 0};
    int cntBombExplode = -1;
    int timeNewBomb = 0;
    int[] dBalloom = {0, 0};
    int[] dOneal = {0, 0};
    int posX;
    int posY;
    int time;
    int timeBomb = -1;
    int frameBalloom = 15;
    int frameOneal = 7;
    int frameBomber;
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
    private static List<MovingEntity> stuffObjects = new ArrayList<>();
    private static List<Entity> hearts = new ArrayList<>();
    private static List<MovingEntity> bomberman = new ArrayList<>();
    private static List<MovingEntity> bombs = new ArrayList<>();
    private static List<MovingEntity> balloom = new ArrayList<>();
    private static List<MovingEntity> oneal = new ArrayList<>();
    private static List<MovingEntity> entities = new ArrayList<>();
    Map map;

    //Map map = new Map();
    /*Map getMap() {
        return this.map;
    }
    List<MovingEntity> getBomberman (){
        return this.bomberman;
    }
    List<MovingEntity> getBalloom (){
        return this.balloom;
    }
    List<MovingEntity> getOneal (){
        return this.oneal;
    }
    List<MovingEntity> getBombs (){
        return this.bombs;
    }
    List<MovingEntity> getEntities (){
        return this.entities;
    }
    List<Entity> getHearts (){
        return this.hearts;
    }
    List<MovingEntity> getStuffObjects (){
        return this.stuffObjects;
    }*/
    void update(List<MovingEntity> stuffObjects, List<Entity> hearts, List<MovingEntity> entities, List<MovingEntity> bomberman, List<MovingEntity> balloom, List<MovingEntity> oneal, int cnt, int frameBomber, int dBomber, List<MovingEntity> bombs, Map map) {
        this.map = map;
        this.bomberman = bomberman;
        this.balloom = balloom;
        this.oneal = oneal;
        this.bombs = bombs;
        this.entities = entities;
        this.hearts = hearts;
        this.stuffObjects = stuffObjects;
        this.frameBomber = frameBomber;
        if (this.bombs.size() == 0)
            timeBomb = -1;
        else if (timeBomb == -1 && cntBombExplode == -1) {
            timeBomb = 0;
            checkBombUp = false;
            checkBombDown = false;
            checkBombRight = false;
            checkBombLeft = false;
            checkEvent = false;
        }
        if (this.bomberman.size() == 0 && timeNewBomb < 50) {
            timeNewBomb++;
        }
        if (this.bomberman.size() == 0 && timeNewBomb == 50) {
            bomberman.add(new Bomber(1, 3, Sprite.player[1][0].getFxImage()));
            timeNewBomb = 0;
        }
        if (this.bomberman.size() > 0) {
            if (cnt == 0 * frameBomber) {
                if ((this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], ' ', this.map) || this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], 'b', this.map))) {
                    this.bomberman.get(0).update(d[dBomber][0], d[dBomber][1], Sprite.player[dBomber][0].getFxImage());
                } else {
                    this.bomberman.get(0).update(0, 0, Sprite.player[dBomber][0].getFxImage());
                }
            }
            else if (cnt == 1 * frameBomber) {
                if ((this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], ' ', this.map) || this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], 'b', this.map))) {
                    this.bomberman.get(0).update(d[dBomber][0], d[dBomber][1], Sprite.player[dBomber][1].getFxImage());
                } else {
                    this.bomberman.get(0).update(0, 0, Sprite.player[dBomber][1].getFxImage());
                }
            }
            else if (cnt == 2 * frameBomber) {
                if ((this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], ' ', this.map) || this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], 'b', this.map))) {
                    this.bomberman.get(0).update(d[dBomber][0], d[dBomber][1], Sprite.player[dBomber][2].getFxImage());
                } else {
                    this.bomberman.get(0).update(0, 0, Sprite.player[dBomber][2].getFxImage());
                }
            }
            else if (cnt == 3 * frameBomber) {
                if ((this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], ' ', this.map) || this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], 'b', this.map))) {
                    this.bomberman.get(0).update(d[dBomber][0], d[dBomber][1], Sprite.player[dBomber][3].getFxImage());
                } else {
                    this.bomberman.get(0).update(0, 0, Sprite.player[dBomber][3].getFxImage());
                }
            }
        }
        /*if (this.bomberman.size() > 0 && cnt >= 0 && cnt < 4) {
            if ((this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], ' ', this.map) || this.bomberman.get(0).check(d[dBomber][0], d[dBomber][1], 'b', this.map))) {
                this.bomberman.get(0).update(d[dBomber][0], d[dBomber][1], Sprite.player[dBomber][cnt].getFxImage());
            } else {
                this.bomberman.get(0).update(0, 0, Sprite.player[dBomber][cnt].getFxImage());
            }
        }*/

        if (timeBomb == 0) {
            posX = bomberman.get(0).getX();
            posY = bomberman.get(0).getY();
            updateBomb(bombs.get(0), timeBomb, posX, posY);
            timeBomb++;
        }
        else if (timeBomb > 0 && timeBomb < 180) {
            //if (checkEvent == false && (abs(entities.get(0).getX() - posX) > Sprite.SCALED_SIZE || abs(entities.get(0).getY() - posY) > Sprite.SCALED_SIZE)) {
            if (checkEvent == false && (abs(this.bomberman.get(0).getX() - posX) > 20 || abs(this.bomberman.get(0).getY() - posY) > 20)) {
                this.map.set(posX /40, posY / 40,'b');
                checkEvent = true;
            }
            updateBomb(bombs.get(0), timeBomb, posX, posY);
            timeBomb++;
        }
        for (int i = 0; i < this.balloom.size(); i++) {
            if (time % frameBalloom == 0) {
                cntBalloom[i]++;
            }
        }
        for (int i = 0; i < this.oneal.size(); i++) {
            if (time % frameOneal == 0) {
                cntOneal[i]++;
            }
        }

        int t = 10;
        if (cntBombExplode >= 0 && cntBombExplode <= t) {
            updateBombExplode(posX, posY, 0);
        }
        else if (cntBombExplode > t && cntBombExplode <= t * 2) {
            updateBombExplode(posX, posY, 1);
        }
        else if (cntBombExplode > t * 2 && cntBombExplode <= t * 3) {
            updateBombExplode(posX, posY, 2);
        }
        else if (cntBombExplode > t * 3 && cntBombExplode <= t * 4){
            updateBombExplode(posX, posY, 3);
        }
        else if (cntBombExplode > t * 4){
            this.entities.remove(flameCenter);
            this.entities.remove(flameUp);
            this.entities.remove(flameDown);
            this.entities.remove(flameRight);
            this.entities.remove(flameLeft);
            cntBombExplode = -1;
        }

        if (cntBombExplode >= 0) {
            cntBombExplode++;
        }

        if (timeBomb == 180) {
            timeBomb = -1;
            cntBombExplode = 0;
            this.map.set(posX/40, posY/40, ' ');
            //checkEvent = false;
            flameCenter = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE, Sprite.flame_center[0].getFxImage());
            this.entities.add(flameCenter);
            if (this.bombs.get(0).check(d[0][0], d[0][1], ' ', this.map) || this.bombs.get(0).check(d[0][0], d[0][1], '*', this.map)) {
                flameUp = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE - 1, Sprite.flame_up[0].getFxImage());
                this.entities.add(flameUp);
                checkBombUp = true;
            }
            if (this.bombs.get(0).check(d[1][0], d[1][1], ' ', this.map) || this.bombs.get(0).check(d[1][0], d[1][1], '*', this.map)) {
                flameDown = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE + 1, Sprite.flame_down[0].getFxImage());
                this.entities.add(flameDown);
                checkBombDown = true;
            }
            if (this.bombs.get(0).check(d[2][0], d[2][1], ' ', this.map) || this.bombs.get(0).check(d[2][0], d[2][1], '*', this.map)) {
                flameRight = new Flame(posX / Sprite.SCALED_SIZE + 1, posY / Sprite.SCALED_SIZE, Sprite.flame_right[0].getFxImage());
                this.entities.add(flameRight);
                checkBombRight = true;
            }
            if (this.bombs.get(0).check(d[3][0], d[3][1], ' ', this.map) || this.bombs.get(0).check(d[3][0], d[3][1], '*', this.map)) {
                flameLeft = new Flame(posX / Sprite.SCALED_SIZE - 1, posY / Sprite.SCALED_SIZE, Sprite.flame_left[0].getFxImage());
                this.entities.add(flameLeft);
                checkBombLeft = true;
            }
            bombs.remove(0);
            gameSound.startExplosion();
        }
        for (int i = 0; i < this.balloom.size(); i++) {
            if (time % frameBalloom == 0) {
                if (this.balloom.get(i).check(d[dBalloom[i]][0], d[dBalloom[i]][1], ' ', this.map)) {
                    if (dBalloom[i] == 0 || dBalloom[i] == 1) {
                        this.balloom.get(i).update(d[dBalloom[i]][0], d[dBalloom[i]][1], Sprite.balloom_right[cntBalloom[i]%4].getFxImage());
                    } else {
                        this.balloom.get(i).update(d[dBalloom[i]][0], d[dBalloom[i]][1], Sprite.balloom_left[cntBalloom[i]%4].getFxImage());
                    }
                    cntBalloom[i]++;
                } else {
                    Random generator = new Random();
                    int newD = generator.nextInt(4);
                    boolean checkD = false;
                    for (int j = 0; j < 4; j++) {
                        if (this.balloom.get(i).check(d[j][0], d[j][1], ' ', this.map)) {
                            checkD = true;
                            break;
                        }
                    }
                    if (checkD == true) {
                        while (!this.balloom.get(i).check(d[newD][0], d[newD][1], ' ', this.map)) {
                            newD = generator.nextInt(4);
                        }
                        if (newD == 0 || newD == 2)
                            this.balloom.get(i).update(d[newD][0], d[newD][1], Sprite.balloom_right[0].getFxImage());
                        else
                            this.balloom.get(i).update(d[newD][0], d[newD][1], Sprite.balloom_left[0].getFxImage());
                        dBalloom[i] = newD;
                        cntBalloom[i] = 0;
                    }
                }
            }
        }
        for (int i = 0; i < this.oneal.size(); i++) {
            if (time % frameOneal == 0) {
                if (this.oneal.get(i).check(d[dOneal[i]][0], d[dOneal[i]][1], ' ', this.map)) {
                    if (dOneal[i] == 0 || dOneal[i] == 1) {
                        this.oneal.get(i).update(d[dOneal[i]][0], d[dOneal[i]][1], Sprite.oneal_right[cntOneal[i]%4].getFxImage());
                    } else {
                        this.oneal.get(i).update(d[dOneal[i]][0], d[dOneal[i]][1], Sprite.oneal_left[cntOneal[i]%4].getFxImage());
                    }
                    cntOneal[i]++;
                } else {
                    Random generator = new Random();
                    int newD = generator.nextInt(4);
                    boolean checkD = false;
                    for (int j = 0; j < 4; j++) {
                        if (this.oneal.get(i).check(d[j][0], d[j][1], ' ', this.map)) {
                            checkD = true;
                            break;
                        }
                    }
                    if (checkD == true) {
                        frameOneal = (generator.nextInt(2) + 1) * 10;
                        while (!this.oneal.get(i).check(d[newD][0], d[newD][1], ' ', this.map)) {
                            newD = generator.nextInt(4);
                        }
                        if (newD == 0 || newD == 2)
                            this.oneal.get(i).update(d[newD][0], d[newD][1], Sprite.oneal_right[0].getFxImage());
                        else
                            this.oneal.get(i).update(d[newD][0], d[newD][1], Sprite.oneal_left[0].getFxImage());
                        dOneal[i] = newD;
                        cntOneal[i] = 0;
                    }
                }
            }
        }
        time++;
        if (this.bomberman.size() > 0) {
            balloom.forEach(o -> {
                if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), o.getX(), o.getY(), 20)) {
                    this.bomberman.remove(0);
                    hearts.remove(0);
                    return;
                }
            });
            oneal.forEach(o -> {
                if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), o.getX(), o.getY(), 20)) {
                    this.bomberman.remove(0);
                    hearts.remove(0);
                    return;
                }
            });
        }
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
    boolean collision(int x1, int y1, int x2, int y2, int val) {
        if (abs(x1 - x2) < val && abs(y1 - y2) < val)
            return true;
        return false;
    }
    void removeObjects(int posX, int posY) {
        List<MovingEntity> removeBalloom = new ArrayList<>();
        for (int i = 0; i < this.balloom.size(); i++) {
            if (this.balloom.size() > 0 && collision(this.balloom.get(i).getX(), this.balloom.get(i).getY(), posX, posY, 40)) {
                removeBalloom.add(this.balloom.get(i));
            }
        }
        removeBalloom.forEach(o -> {
            this.balloom.remove(o);
        });
        List<MovingEntity> removeOneal = new ArrayList<>();
        for (int i = 0; i < this.oneal.size(); i++) {
            if (this.oneal.size() > 0 && collision(this.oneal.get(i).getX(), this.oneal.get(i).getY(), posX, posY, 40)) {
                removeOneal.add(this.oneal.get(i));
            }
        }
        removeOneal.forEach(o -> {
            this.oneal.remove(o);
        });
        List<MovingEntity> removeObject = new ArrayList<>();
        this.stuffObjects.forEach(o -> {
            if (collision(o.getX(), o.getY(), posX, posY, 40)) {
                this.map.set(o.getX()/40, o.getY()/40, ' ');
                removeObject.add(o);
            }
        });
        removeObject.forEach(o -> {
            this.stuffObjects.remove(o);
        });
    }
    void updateBombExplode(int posX, int posY, int cnt) {
        this.entities.remove(flameCenter);
        flameCenter = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE, Sprite.flame_center[cnt].getFxImage());
        this.entities.add(flameCenter);
        if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX, posY, 40)) {
            bomberman.remove(0);
            hearts.remove(0);
        }
        removeObjects(posX, posY);
        if (checkBombUp) {
            this.entities.remove(flameUp);
            flameUp = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE - 1, Sprite.flame_up[cnt].getFxImage());
            this.entities.add(flameUp);
            if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX, posY - 40, 40)) {
                bomberman.remove(0);
                hearts.remove(0);
            }
            removeObjects(posX, posY - 40);
        }

        if (checkBombDown) {
            this.entities.remove(flameDown);
            flameDown = new Flame(posX / Sprite.SCALED_SIZE, posY / Sprite.SCALED_SIZE + 1, Sprite.flame_down[cnt].getFxImage());
            this.entities.add(flameDown);
            if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX, posY + 40, 40)) {
                this.bomberman.remove(0);
                this.hearts.remove(0);
            }
            removeObjects(posX, posY + 40);
        }

        if (checkBombRight) {
            this.entities.remove(flameRight);
            flameRight = new Flame(posX / Sprite.SCALED_SIZE + 1, posY / Sprite.SCALED_SIZE, Sprite.flame_right[cnt].getFxImage());
            this.entities.add(flameRight);
            if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX + 40, posY, 40)) {
                this.bomberman.remove(0);
                this.hearts.remove(0);
            }
            removeObjects(posX + 40, posY);
        }
        if (checkBombLeft) {
            this.entities.remove(flameLeft);
            flameLeft = new Flame(posX / Sprite.SCALED_SIZE - 1, posY / Sprite.SCALED_SIZE, Sprite.flame_left[cnt].getFxImage());
            this.entities.add(flameLeft);
            if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX - 40, posY, 40)) {
                this.bomberman.remove(0);
                this.hearts.remove(0);
            }
            removeObjects(posX - 40, posY);
        }
    }
}
