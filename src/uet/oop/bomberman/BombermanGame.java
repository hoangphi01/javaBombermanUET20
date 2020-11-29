package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.util.Duration;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 13;
    public  static final Map map = new Map();
    private static final Group root = new Group();

    // Tao scene
    private static final Scene scene = new Scene(root);
    private static gameBackground bg = new gameBackground();

    // Sound
    private static Media media;


    private GraphicsContext gc;
    private Canvas canvas;
    //Các đối tượng động
    private static List<MovingEntity> entities = new ArrayList<>();

    //Các đối tượng tĩnh: grass, wall
    private static List<Entity> stillObjects = new ArrayList<>();

    public static void playClick() {
        Media media;
        String soundLocation = "src/Music/ClickSound.mp3";
        media = new Media(new File(soundLocation).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static void startGame() {
        root.getChildren().remove(bg);
        bg = null;
        //playBackgroundSound();
        createMap();
    }

    public static void loadTutorial() {
        //Image img;
        ImageView imageView = new ImageView(new Image("/textures/HOWTO.png"));
        imageView.setFitWidth(800);
        imageView.setFitHeight(520);

        root.getChildren().remove(bg);
        bg = null;

        root.getChildren().add(imageView);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.ENTER) {
                playClick();
                root.getChildren().remove(imageView);
                BombermanGame.startGame();
            }
        });
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Bomberman 2020");

        // Tao Nhac nen
        String soundLocation = "src/Music/BackgroundSound.mp3";
        media = new Media(new File(soundLocation).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        root.getChildren().addAll(canvas, bg);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        //update
        Update updates = new Update();

        //MovingEntity bomb = new Bomb();
        AnimationTimer timer = new AnimationTimer() {
            int cnt = -1;
            int timeBomb = -1;
            int[][] dir = {{0, 5}, {5, 0}, {-5, 0}, {0, -5}};
            int timeBombExplode = -1;
            int cntBalloom = 0;
            int cntBombExplode = -1;
            int timeBalloom = 0;
            int direction = 0;
            int directionBalloom = 0;
            int posX;
            int posY;
            long last = 0;
            boolean checkBombUp = false;
            boolean checkBombDown = false;
            boolean checkBombRight = false;
            boolean checkBombLeft = false;
            MovingEntity bomb = new Bomb();
            MovingEntity flameCenter = new Flame();
            MovingEntity flameUp = new Flame();
            MovingEntity flameDown = new Flame();
            MovingEntity flameRight = new Flame();
            MovingEntity flameLeft = new Flame();
            @Override
            public void handle(long l) {
                render();
                if (cnt >= 0 && cnt < 4) {
                    if (direction == 0) {
                        updates.updateUp(entities.get(0), cnt);
                    }
                    else if (direction == 1) {
                        updates.updateDown(entities.get(0), cnt);
                    }
                    else if (direction == 2) {
                        updates.updateRight(entities.get(0), cnt);
                    }
                    else {
                        updates.updateLeft(entities.get(0), cnt);
                    }
                    //entities.get(0)
                }

                if (timeBomb == 0) {
                        posX = entities.get(0).getX();
                        posY = entities.get(0).getY();
                        updates.updateBomb(bomb, timeBomb, posX, posY);
                        timeBomb++;
                }
                if (timeBomb > 0 && timeBomb < 120) {
                        updates.updateBomb(bomb, timeBomb, posX, posY);
                        timeBomb++;
                }

                if (timeBalloom % 10 == 0) {
                    updates.updateBombExplode(bomb, timeBomb, posX, posY);
                    cntBalloom++;

                }
                //System.out.println(cntBombExplode);
                if (cntBombExplode >= 0)
                    cntBombExplode++;
                if (cntBombExplode >= 15 && cntBombExplode <= 30) {
                    entities.remove(flameCenter);
                    flameCenter = new Flame(posX / 40, posY / 40, Sprite.center_flame1.getFxImage());
                    entities.add(flameCenter);
                    if (checkBombUp) {
                        entities.remove(flameUp);
                        flameUp = new Flame(posX / 40, posY / 40 - 1, Sprite.up_flame1.getFxImage());
                        entities.add(flameUp);

                    }

                    if (checkBombDown) {
                        entities.remove(flameDown);
                        flameDown = new Flame(posX / 40, posY / 40 + 1, Sprite.down_flame1.getFxImage());
                        entities.add(flameDown);
                    }

                    if (checkBombRight) {
                        entities.remove(flameRight);
                        flameRight = new Flame(posX / 40 + 1, posY / 40, Sprite.right_flame1.getFxImage());
                        entities.add(flameRight);
                    }
                    if (checkBombLeft) {
                        entities.remove(flameLeft);
                        flameLeft = new Flame(posX / 40 - 1, posY / 40, Sprite.left_flame1.getFxImage());
                        entities.add(flameLeft);
                    }
                    //cntBombExplode++;
                }

                else if (cntBombExplode > 30 && cntBombExplode < 45) {
                    entities.remove(flameCenter);
                    flameCenter = new Flame(posX / 40, posY / 40, Sprite.center_flame2.getFxImage());
                    entities.add(flameCenter);
                    if (checkBombUp) {
                        entities.remove(flameUp);
                        flameUp = new Flame(posX / 40, posY / 40 - 1, Sprite.up_flame2.getFxImage());
                        entities.add(flameUp);

                    }

                    if (checkBombDown) {
                        entities.remove(flameDown);
                        flameDown = new Flame(posX / 40, posY / 40 + 1, Sprite.down_flame2.getFxImage());
                        entities.add(flameDown);
                    }

                    if (checkBombRight) {
                        entities.remove(flameRight);
                        flameRight = new Flame(posX / 40 + 1, posY / 40, Sprite.right_flame2.getFxImage());
                        entities.add(flameRight);
                    }
                    if (checkBombLeft) {
                        entities.remove(flameLeft);
                        flameLeft = new Flame(posX / 40 - 1, posY / 40, Sprite.left_flame2.getFxImage());
                        entities.add(flameLeft);
                    }
                    //cntBombExplode++;
                }

                else if (cntBombExplode > 45 && cntBombExplode <= 60) {
                    entities.remove(flameCenter);
                    flameCenter = new Flame(posX / 40, posY / 40, Sprite.center_flame3.getFxImage());
                    entities.add(flameCenter);
                    if (checkBombUp) {
                        entities.remove(flameUp);
                        flameUp = new Flame(posX / 40, posY / 40 - 1, Sprite.up_flame3.getFxImage());
                        entities.add(flameUp);

                    }

                    if (checkBombDown) {
                        entities.remove(flameDown);
                        flameDown = new Flame(posX / 40, posY / 40 + 1, Sprite.down_flame3.getFxImage());
                        entities.add(flameDown);
                    }

                    if (checkBombRight) {
                        entities.remove(flameRight);
                        flameRight = new Flame(posX / 40 + 1, posY / 40, Sprite.right_flame3.getFxImage());
                        entities.add(flameRight);
                    }
                    if (checkBombLeft) {
                        entities.remove(flameLeft);
                        flameLeft = new Flame(posX / 40 - 1, posY / 40, Sprite.left_flame3.getFxImage());
                        entities.add(flameLeft);
                    }
                    //cntBombExplode = -1;
                }
                else if (cntBombExplode > 60){
                    entities.remove(flameCenter);
                    entities.remove(flameUp);
                    entities.remove(flameDown);
                    entities.remove(flameRight);
                    entities.remove(flameLeft);
                    cntBombExplode = -1;
                }

                if (timeBomb == 120) {
                    timeBomb = -1;
                    cntBombExplode = 0;
                    flameCenter = new Flame(posX / 40, posY / 40, Sprite.center_flame.getFxImage());
                    entities.add(flameCenter);
                    //System.out.println(posX + " " + (-20 % 40));
                    if (bomb.check(0, -5)) {
                        flameUp = new Flame(posX / 40, posY / 40 - 1, Sprite.up_flame.getFxImage());
                        entities.add(flameUp);
                        checkBombUp = true;
                    }

                    if (bomb.check(0, 5)) {
                        flameDown = new Flame(posX / 40, posY / 40 + 1, Sprite.down_flame.getFxImage());
                        entities.add(flameDown);
                        checkBombDown = true;
                    }

                    if (bomb.check(5, 0)) {
                        flameRight = new Flame(posX / 40 + 1, posY / 40, Sprite.right_flame.getFxImage());
                        entities.add(flameRight);
                        checkBombRight = true;
                    }
                    if (bomb.check(-5, 0)) {
                        flameLeft = new Flame(posX / 40 - 1, posY / 40, Sprite.left_flame.getFxImage());
                        entities.add(flameLeft);
                        checkBombLeft = true;
                    }
                    entities.remove(bomb);
                }


                if (timeBalloom % 10 == 0) {
                    if (entities.get(1).check(dir[directionBalloom][0], dir[directionBalloom][1])) {
                        if (directionBalloom == 0 || directionBalloom == 1) {
                            if (cntBalloom % 4 == 0)
                                entities.get(1).update(dir[directionBalloom][0], dir[directionBalloom][1], Sprite.balloom_right1.getFxImage());
                            if (cntBalloom % 4 == 1)
                                entities.get(1).update(dir[directionBalloom][0], dir[directionBalloom][1], Sprite.balloom_right2.getFxImage());
                            if (cntBalloom % 4 == 2)
                                entities.get(1).update(dir[directionBalloom][0], dir[directionBalloom][1], Sprite.balloom_right3.getFxImage());
                            if (cntBalloom % 4== 3)
                                entities.get(1).update(dir[directionBalloom][0], dir[directionBalloom][1], Sprite.balloom_right4.getFxImage());
                        }
                        else {
                            if (cntBalloom % 4 == 0)
                                entities.get(1).update(dir[directionBalloom][0], dir[directionBalloom][1], Sprite.balloom_left1.getFxImage());
                            if (cntBalloom % 4 == 1)
                                entities.get(1).update(dir[directionBalloom][0], dir[directionBalloom][1], Sprite.balloom_left2.getFxImage());
                            if (cntBalloom % 4 == 2)
                                entities.get(1).update(dir[directionBalloom][0], dir[directionBalloom][1], Sprite.balloom_left3.getFxImage());
                            if (cntBalloom % 4 == 3)
                                entities.get(1).update(dir[directionBalloom][0], dir[directionBalloom][1], Sprite.balloom_left4.getFxImage());
                        }
                        cntBalloom++;
                    }
                    else {
                        Random generator = new Random();
                        int newDir = generator.nextInt(4);
                        while (!entities.get(1).check(dir[newDir][0], dir[newDir][1])) {
                            newDir = generator.nextInt(4);
                        }
                        if (newDir == 0 || newDir == 1)
                            entities.get(1).update(dir[newDir][0], dir[newDir][1], Sprite.balloom_right1.getFxImage());
                        else
                            entities.get(1).update(dir[newDir][0], dir[newDir][1], Sprite.balloom_left1.getFxImage());
                        directionBalloom = newDir;
                        cntBalloom = 1;
                    }
                }

                    scene.setOnKeyPressed(e -> {
                        if (!(cnt >= 0 && cnt < 4)) {
                            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
                                cnt = 0;
                                direction = 0;
                            } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
                                cnt = 0;
                                direction = 1;
                            } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                                cnt = 0;
                                direction = 2;
                            } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                                cnt = 0;
                                direction = 3;
                            } else if (e.getCode() == KeyCode.SPACE && timeBomb == -1) {

                                entities.add(bomb);
                                timeBomb = 0;

                            }
                        }

                    });
                if (cnt != -1)
                    cnt++;
                timeBalloom++; // reset sau 1e8
            }
        };
        timer.start();

        createMap();

        // Khởi tạo vị trí của bomber nằm ở ô x, y trong map và hướng nhân vật quay sang trái
        MovingEntity bomberman = new Bomber(1, 3, Sprite.player_down.getFxImage());

        //Thêm bomber vào object entities
        entities.add(bomberman);
        MovingEntity balloom = new Balloom(1, 4, Sprite.balloom_left1.getFxImage());
        entities.add(balloom);
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 2; j < HEIGHT; j++) {
                Entity object;
                // i, j ở rìa thì khởi tạo wall
                if (j == 2 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1 || (i > 2 && i % 2 == 1 && j % 2 == 0 && i < 19 && j < 14)) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                // Khởi tạo grass
                else {
                    object = new Grass(i, j, Sprite.grass[i-1][j-1].getFxImage());
                }

                stillObjects.add(object);
            }
        }
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map.get(i, j) == '*') {
                    Entity object;
                    object = new Brick(i, j, Sprite.brick.getFxImage());
                    stillObjects.add(object);
                }
            }
        }

    }

    public void render() {
        // Xóa màn hình cũ đi
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // render các đối tượng tĩnh
        stillObjects.forEach(g -> g.render(gc));

        //render các đối tượng động
        entities.forEach(g -> g.render(gc));
    }
}
