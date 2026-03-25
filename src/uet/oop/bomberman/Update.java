package uet.oop.bomberman;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static uet.oop.bomberman.GameConstants.*;

public class Update {

    int[] cntBalloom = {0, 0};
    int[] cntOneal = {0, 0};
    int[] dBalloom = {0, 0};
    int[] dOneal = {0, 0};
    int posX;
    int posY;
    int animFrame = 0;
    boolean checkBombUp = false;
    boolean checkBombDown = false;
    boolean checkBombRight = false;
    boolean checkBombLeft = false;
    boolean checkEvent = false;

    // Time-based timers
    GameTimer playerMoveTimer = new GameTimer(PLAYER_MOVE_INTERVAL_MS);
    GameTimer bombFuseTimer = new GameTimer(BOMB_FUSE_TIME_MS);
    GameTimer bombAnimTimer = new GameTimer(BOMB_ANIM_INTERVAL_MS);
    GameTimer explosionTimer = new GameTimer(EXPLOSION_PHASE_MS);
    GameTimer respawnTimer = new GameTimer(RESPAWN_DELAY_MS);
    GameTimer balloomMoveTimer = new GameTimer(BALLOOM_MOVE_INTERVAL_MS);
    GameTimer onealMoveTimer = new GameTimer(ONEAL_MOVE_INTERVAL_MS);

    boolean bombActive = false;
    int bombAnimFrame = 0;
    int explosionPhase = -1; // -1 = no explosion
    boolean respawning = false;

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

    void update(List<MovingEntity> stuffObjects, List<Entity> hearts, List<MovingEntity> entities,
                List<MovingEntity> bomberman, List<MovingEntity> balloom, List<MovingEntity> oneal,
                int direction, boolean isMoving, List<MovingEntity> bombs, Map map, long deltaMs) {
        this.map = map;
        this.bomberman = bomberman;
        this.balloom = balloom;
        this.oneal = oneal;
        this.bombs = bombs;
        this.entities = entities;
        this.hearts = hearts;
        this.stuffObjects = stuffObjects;

        // --- Bomb state management ---
        if (this.bombs.size() == 0) {
            bombActive = false;
        } else if (!bombActive && explosionPhase == -1) {
            bombActive = true;
            bombFuseTimer.reset();
            bombAnimTimer.reset();
            bombAnimFrame = 0;
            checkBombUp = false;
            checkBombDown = false;
            checkBombRight = false;
            checkBombLeft = false;
            checkEvent = false;
            posX = bomberman.get(0).getX();
            posY = bomberman.get(0).getY();
        }

        // --- Respawn ---
        if (this.bomberman.size() == 0) {
            if (!respawning) {
                respawning = true;
                respawnTimer.reset();
            }
            if (respawnTimer.update(deltaMs) > 0) {
                bomberman.add(new Bomber(1, 3, Sprite.player[1][0].getFxImage()));
                respawning = false;
            }
        }

        // --- Player movement (time-based) ---
        if (this.bomberman.size() > 0 && isMoving) {
            int ticks = playerMoveTimer.update(deltaMs);
            for (int t = 0; t < ticks; t++) {
                if (this.bomberman.get(0).check(DIRECTION_DELTAS[direction][0], DIRECTION_DELTAS[direction][1], ' ', this.map)) {
                    this.bomberman.get(0).update(DIRECTION_DELTAS[direction][0], DIRECTION_DELTAS[direction][1], Sprite.player[direction][animFrame % 4].getFxImage());
                } else {
                    this.bomberman.get(0).update(0, 0, Sprite.player[direction][animFrame % 4].getFxImage());
                }
                animFrame++;
            }
        } else {
            playerMoveTimer.reset();
        }

        // --- Bomb fuse & animation ---
        if (bombActive && this.bombs.size() > 0) {
            // Check if player walked away from bomb
            if (!checkEvent && this.bomberman.size() > 0 &&
                    (abs(this.bomberman.get(0).getX() - posX) > COLLISION_THRESHOLD ||
                     abs(this.bomberman.get(0).getY() - posY) > COLLISION_THRESHOLD)) {
                this.map.set(posX / BLOCK_WIDTH, posY / BLOCK_HEIGHT, 'b');
                checkEvent = true;
            }

            // Animate bomb
            int animTicks = bombAnimTimer.update(deltaMs);
            if (animTicks > 0) {
                bombAnimFrame = (bombAnimFrame + animTicks) % 4;
            }
            this.bombs.get(0).update(posX, posY, Sprite.bomb[bombAnimFrame].getFxImage());

            // Check if bomb should explode
            if (bombFuseTimer.update(deltaMs) > 0) {
                // EXPLODE
                bombActive = false;
                explosionPhase = 0;
                explosionTimer.reset();
                this.map.set(posX / BLOCK_WIDTH, posY / BLOCK_HEIGHT, ' ');

                flameCenter = new Flame(posX / BLOCK_WIDTH, posY / BLOCK_HEIGHT, Sprite.flame_center[0].getFxImage());
                this.entities.add(flameCenter);
                if (this.bombs.get(0).check(DIRECTION_DELTAS[DIR_UP][0], DIRECTION_DELTAS[DIR_UP][1], ' ', this.map) || this.bombs.get(0).check(DIRECTION_DELTAS[DIR_UP][0], DIRECTION_DELTAS[DIR_UP][1], '*', this.map)) {
                    flameUp = new Flame(posX / BLOCK_WIDTH, posY / BLOCK_HEIGHT - 1, Sprite.flame_up[0].getFxImage());
                    this.entities.add(flameUp);
                    checkBombUp = true;
                }
                if (this.bombs.get(0).check(DIRECTION_DELTAS[DIR_DOWN][0], DIRECTION_DELTAS[DIR_DOWN][1], ' ', this.map) || this.bombs.get(0).check(DIRECTION_DELTAS[DIR_DOWN][0], DIRECTION_DELTAS[DIR_DOWN][1], '*', this.map)) {
                    flameDown = new Flame(posX / BLOCK_WIDTH, posY / BLOCK_HEIGHT + 1, Sprite.flame_down[0].getFxImage());
                    this.entities.add(flameDown);
                    checkBombDown = true;
                }
                if (this.bombs.get(0).check(DIRECTION_DELTAS[DIR_RIGHT][0], DIRECTION_DELTAS[DIR_RIGHT][1], ' ', this.map) || this.bombs.get(0).check(DIRECTION_DELTAS[DIR_RIGHT][0], DIRECTION_DELTAS[DIR_RIGHT][1], '*', this.map)) {
                    flameRight = new Flame(posX / BLOCK_WIDTH + 1, posY / BLOCK_HEIGHT, Sprite.flame_right[0].getFxImage());
                    this.entities.add(flameRight);
                    checkBombRight = true;
                }
                if (this.bombs.get(0).check(DIRECTION_DELTAS[DIR_LEFT][0], DIRECTION_DELTAS[DIR_LEFT][1], ' ', this.map) || this.bombs.get(0).check(DIRECTION_DELTAS[DIR_LEFT][0], DIRECTION_DELTAS[DIR_LEFT][1], '*', this.map)) {
                    flameLeft = new Flame(posX / BLOCK_WIDTH - 1, posY / BLOCK_HEIGHT, Sprite.flame_left[0].getFxImage());
                    this.entities.add(flameLeft);
                    checkBombLeft = true;
                }
                bombs.remove(0);
                gameSound.startExplosion();
            }
        }

        // --- Explosion animation (time-based) ---
        if (explosionPhase >= 0) {
            int expTicks = explosionTimer.update(deltaMs);
            for (int t = 0; t < expTicks; t++) {
                explosionPhase++;
            }
            if (explosionPhase < 4) {
                updateBombExplode(posX, posY, explosionPhase);
            } else {
                // Explosion finished
                this.entities.remove(flameCenter);
                this.entities.remove(flameUp);
                this.entities.remove(flameDown);
                this.entities.remove(flameRight);
                this.entities.remove(flameLeft);
                explosionPhase = -1;
            }
        }

        // --- Enemy movement (time-based) ---
        int balloomTicks = balloomMoveTimer.update(deltaMs);
        for (int tick = 0; tick < balloomTicks; tick++) {
            for (int i = 0; i < this.balloom.size(); i++) {
                cntBalloom[i]++;
                if (this.balloom.get(i).check(DIRECTION_DELTAS[dBalloom[i]][0], DIRECTION_DELTAS[dBalloom[i]][1], ' ', this.map)) {
                    if (dBalloom[i] == 0 || dBalloom[i] == 1) {
                        this.balloom.get(i).update(DIRECTION_DELTAS[dBalloom[i]][0], DIRECTION_DELTAS[dBalloom[i]][1], Sprite.balloom_right[cntBalloom[i] % 4].getFxImage());
                    } else {
                        this.balloom.get(i).update(DIRECTION_DELTAS[dBalloom[i]][0], DIRECTION_DELTAS[dBalloom[i]][1], Sprite.balloom_left[cntBalloom[i] % 4].getFxImage());
                    }
                } else {
                    Random generator = new Random();
                    int newD = generator.nextInt(4);
                    boolean checkD = false;
                    for (int j = 0; j < 4; j++) {
                        if (this.balloom.get(i).check(DIRECTION_DELTAS[j][0], DIRECTION_DELTAS[j][1], ' ', this.map)) {
                            checkD = true;
                            break;
                        }
                    }
                    if (checkD) {
                        while (!this.balloom.get(i).check(DIRECTION_DELTAS[newD][0], DIRECTION_DELTAS[newD][1], ' ', this.map)) {
                            newD = generator.nextInt(4);
                        }
                        if (newD == 0 || newD == 2)
                            this.balloom.get(i).update(DIRECTION_DELTAS[newD][0], DIRECTION_DELTAS[newD][1], Sprite.balloom_right[0].getFxImage());
                        else
                            this.balloom.get(i).update(DIRECTION_DELTAS[newD][0], DIRECTION_DELTAS[newD][1], Sprite.balloom_left[0].getFxImage());
                        dBalloom[i] = newD;
                        cntBalloom[i] = 0;
                    }
                }
            }
        }

        int onealTicks = onealMoveTimer.update(deltaMs);
        for (int tick = 0; tick < onealTicks; tick++) {
            for (int i = 0; i < this.oneal.size(); i++) {
                cntOneal[i]++;
                if (this.oneal.get(i).check(DIRECTION_DELTAS[dOneal[i]][0], DIRECTION_DELTAS[dOneal[i]][1], ' ', this.map)) {
                    if (dOneal[i] == 0 || dOneal[i] == 1) {
                        this.oneal.get(i).update(DIRECTION_DELTAS[dOneal[i]][0], DIRECTION_DELTAS[dOneal[i]][1], Sprite.oneal_right[cntOneal[i] % 4].getFxImage());
                    } else {
                        this.oneal.get(i).update(DIRECTION_DELTAS[dOneal[i]][0], DIRECTION_DELTAS[dOneal[i]][1], Sprite.oneal_left[cntOneal[i] % 4].getFxImage());
                    }
                } else {
                    Random generator = new Random();
                    int newD = generator.nextInt(4);
                    boolean checkD = false;
                    for (int j = 0; j < 4; j++) {
                        if (this.oneal.get(i).check(DIRECTION_DELTAS[j][0], DIRECTION_DELTAS[j][1], ' ', this.map)) {
                            checkD = true;
                            break;
                        }
                    }
                    if (checkD) {
                        while (!this.oneal.get(i).check(DIRECTION_DELTAS[newD][0], DIRECTION_DELTAS[newD][1], ' ', this.map)) {
                            newD = generator.nextInt(4);
                        }
                        if (newD == 0 || newD == 2)
                            this.oneal.get(i).update(DIRECTION_DELTAS[newD][0], DIRECTION_DELTAS[newD][1], Sprite.oneal_right[0].getFxImage());
                        else
                            this.oneal.get(i).update(DIRECTION_DELTAS[newD][0], DIRECTION_DELTAS[newD][1], Sprite.oneal_left[0].getFxImage());
                        dOneal[i] = newD;
                        cntOneal[i] = 0;
                    }
                }
            }
        }

        // --- Player-enemy collision ---
        if (this.bomberman.size() > 0) {
            balloom.forEach(o -> {
                if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), o.getX(), o.getY(), COLLISION_THRESHOLD)) {
                    this.bomberman.remove(0);
                    hearts.remove(0);
                    return;
                }
            });
            oneal.forEach(o -> {
                if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), o.getX(), o.getY(), COLLISION_THRESHOLD)) {
                    this.bomberman.remove(0);
                    hearts.remove(0);
                    return;
                }
            });
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
            if (this.balloom.size() > 0 && collision(this.balloom.get(i).getX(), this.balloom.get(i).getY(), posX, posY, EXPLOSION_COLLISION_THRESHOLD)) {
                removeBalloom.add(this.balloom.get(i));
            }
        }
        removeBalloom.forEach(o -> this.balloom.remove(o));

        List<MovingEntity> removeOneal = new ArrayList<>();
        for (int i = 0; i < this.oneal.size(); i++) {
            if (this.oneal.size() > 0 && collision(this.oneal.get(i).getX(), this.oneal.get(i).getY(), posX, posY, EXPLOSION_COLLISION_THRESHOLD)) {
                removeOneal.add(this.oneal.get(i));
            }
        }
        removeOneal.forEach(o -> this.oneal.remove(o));

        List<MovingEntity> removeObject = new ArrayList<>();
        this.stuffObjects.forEach(o -> {
            if (collision(o.getX(), o.getY(), posX, posY, EXPLOSION_COLLISION_THRESHOLD)) {
                this.map.set(o.getX() / BLOCK_WIDTH, o.getY() / BLOCK_HEIGHT, ' ');
                removeObject.add(o);
            }
        });
        removeObject.forEach(o -> this.stuffObjects.remove(o));
    }

    void updateBombExplode(int posX, int posY, int cnt) {
        this.entities.remove(flameCenter);
        flameCenter = new Flame(posX / BLOCK_WIDTH, posY / BLOCK_HEIGHT, Sprite.flame_center[cnt].getFxImage());
        this.entities.add(flameCenter);
        if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX, posY, EXPLOSION_COLLISION_THRESHOLD)) {
            bomberman.remove(0);
            hearts.remove(0);
        }
        removeObjects(posX, posY);
        if (checkBombUp) {
            this.entities.remove(flameUp);
            flameUp = new Flame(posX / BLOCK_WIDTH, posY / BLOCK_HEIGHT - 1, Sprite.flame_up[cnt].getFxImage());
            this.entities.add(flameUp);
            if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX, posY - BLOCK_HEIGHT, EXPLOSION_COLLISION_THRESHOLD)) {
                bomberman.remove(0);
                hearts.remove(0);
            }
            removeObjects(posX, posY - BLOCK_HEIGHT);
        }
        if (checkBombDown) {
            this.entities.remove(flameDown);
            flameDown = new Flame(posX / BLOCK_WIDTH, posY / BLOCK_HEIGHT + 1, Sprite.flame_down[cnt].getFxImage());
            this.entities.add(flameDown);
            if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX, posY + BLOCK_HEIGHT, EXPLOSION_COLLISION_THRESHOLD)) {
                this.bomberman.remove(0);
                this.hearts.remove(0);
            }
            removeObjects(posX, posY + BLOCK_HEIGHT);
        }
        if (checkBombRight) {
            this.entities.remove(flameRight);
            flameRight = new Flame(posX / BLOCK_WIDTH + 1, posY / BLOCK_HEIGHT, Sprite.flame_right[cnt].getFxImage());
            this.entities.add(flameRight);
            if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX + BLOCK_WIDTH, posY, EXPLOSION_COLLISION_THRESHOLD)) {
                this.bomberman.remove(0);
                this.hearts.remove(0);
            }
            removeObjects(posX + BLOCK_WIDTH, posY);
        }
        if (checkBombLeft) {
            this.entities.remove(flameLeft);
            flameLeft = new Flame(posX / BLOCK_WIDTH - 1, posY / BLOCK_HEIGHT, Sprite.flame_left[cnt].getFxImage());
            this.entities.add(flameLeft);
            if (this.bomberman.size() > 0 && collision(this.bomberman.get(0).getX(), this.bomberman.get(0).getY(), posX - BLOCK_WIDTH, posY, EXPLOSION_COLLISION_THRESHOLD)) {
                this.bomberman.remove(0);
                this.hearts.remove(0);
            }
            removeObjects(posX - BLOCK_WIDTH, posY);
        }
    }
}
