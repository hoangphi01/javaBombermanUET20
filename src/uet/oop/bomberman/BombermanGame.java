package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 13;

    private static final Group root = new Group();
    public static Map map = new Map();

    private static MediaPlayer levelStartSound;

    // Tao scene
    private static final Scene scene = new Scene(root);
    private static gameBackground bg = new gameBackground();

    private static gameSnow gSnow;

    private GraphicsContext gc;
    private Canvas canvas;
    private static AnimationTimer timer;
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<MovingEntity> stuffObjects = new ArrayList<>();
    private static List<Entity> hearts = new ArrayList<>();
    private static List<MovingEntity> bomberman = new ArrayList<>();
    private static List<MovingEntity> balloom = new ArrayList<>();
    private static List<MovingEntity> oneal = new ArrayList<>();
    private static List<MovingEntity> bombs = new ArrayList<>();
    private static List<MovingEntity> entities = new ArrayList<>();

    private static Entity bombItem = new Flame();
    private static Entity speedItem = new Flame();
    private static Entity flameItem = new Flame();
    private static Entity portal = new Flame(6, 10, Sprite.portal.getFxImage());

    private static Entity rbombItem = new Flame();
    private static Entity rspeedItem = new Flame();
    private static Entity rflameItem = new Flame();

    public static void Christmas() {
        startGameSpecial();
        gSnow = new gameSnow();
        root.getChildren().add(gSnow);
    }

    public static void startLevel1() {
        timer.stop();
        createMap();
        try {
            levelStartSound = new MediaPlayer(new Media(new File("src/Music/StartLevel.mp3").toURI().toString()));
            levelStartSound.setVolume(0.5);
            levelStartSound.play();
        } catch (Exception e) {
            System.err.println("Sound unavailable: " + e.getMessage());
        }
        Image startLevel = new Image("/textures/level/STAGE1.png");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(800);
        imageView.setFitHeight(520);
        root.getChildren().add(imageView);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(imageView.imageProperty(), startLevel)),
                new KeyFrame(Duration.seconds(2), new KeyValue(imageView.imageProperty(), null))
        );
        timeline.play();
        timer.start();
    }

    public static void showOver() {
        Image endLevel = new Image("/textures/GAMEOVER.gif");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(800);
        imageView.setFitHeight(520);
//        root.getChildren().add(imageView);
//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.ZERO, new KeyValue(imageView.imageProperty(), endLevel)),
//                new KeyFrame(Duration.seconds(5), new KeyValue(imageView.imageProperty(), null))
//        );
//        timeline.play();
//        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
//            if (key.getCode() == KeyCode.ENTER) {
//                if (!root.getChildren().contains(bg)) {
//                    gameSound.playClick();
//                    root.getChildren().remove(imageView);
//                    root.getChildren().add(bg);
//                }
//            }
//        });

        //gameOver.overSound();
        if (!root.getChildren().contains(bg))
            root.getChildren().add(bg);
    }

    public static void gameWin() {
        ImageView imageView = new ImageView(new Image("/textures/GAMEWIN.png"));
        imageView.setFitWidth(800);
        imageView.setFitHeight(520);

        root.getChildren().add(imageView);
    }

    public static void clearObjs() {
        bomberman.clear();
        balloom.clear();
        oneal.clear();
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static void startGame() {
        root.getChildren().remove(bg);
        //bg = null;
        startLevel1();
        gameSound.playBackgroundMusic();
        //createMap();
    }

    public static void startGameSpecial() {
        root.getChildren().remove(bg);
        //bg = null;
        startLevel1();
        gameSound.soundChristmas();
        //createMap();
    }

    public static void loadTutorial() {
        //Image img;
        ImageView imageView = new ImageView(new Image("/textures/HOWTO.png"));
        imageView.setFitWidth(800);
        imageView.setFitHeight(520);

        root.getChildren().remove(bg);
        //bg = null;

        root.getChildren().add(imageView);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                if (root.getChildren().contains(imageView)) {
                    gameSound.playClick();
                    root.getChildren().remove(imageView);
                    BombermanGame.startGame();
                }
            } else if (key.getCode() == KeyCode.ESCAPE) {
                if (!root.getChildren().contains(bg)) {
                    gameSound.playClick();
                    root.getChildren().remove(imageView);
                    root.getChildren().add(bg);
                }
            }
        });
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Bomberman 2020");

        // Nhac menu
        gameSound.playMenuMusic();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        root.getChildren().addAll(canvas, bg);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        //clearObjs();
        //update
        Update updates = new Update();

//        bomberman.add(new Bomber(1, 3, Sprite.player[1][0].getFxImage()));
//
//        balloom.add(new Bomber(18, 11, Sprite.balloom_left[0].getFxImage()));
//        balloom.add(new Bomber(18, 2, Sprite.balloom_left[0].getFxImage()));
//        oneal.add(new Bomber(10, 7, Sprite.oneal_left[0].getFxImage()));
//        oneal.add(new Bomber(2, 11, Sprite.oneal_left[0].getFxImage()));

        timer = new AnimationTimer() {
            int cnt = -1;
            int dBomber = 0;
            int frameBomber = 5;
            MovingEntity bomb = new Bomb();
            @Override
            public void handle(long l) {
                render();
                updates.update(stuffObjects, hearts, entities, bomberman, balloom, oneal, cnt, frameBomber, dBomber, bombs, map);
                scene.setOnKeyPressed(e -> {
                    if (!(cnt >= 0 && cnt < frameBomber * 3 + 1)) {
                        if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
                            cnt = 0;
                            dBomber = 0;
                            gameSound.moveUpDownSound();
                        } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
                            cnt = 0;
                            dBomber = 1;
                            gameSound.moveUpDownSound();
                        } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                            cnt = 0;
                            dBomber = 2;
                            gameSound.moveUpDownSound();
                        } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                            cnt = 0;
                            dBomber = 3;
                            gameSound.moveRightLeftSound();
                        } else if (e.getCode() == KeyCode.SPACE && bombs.size() == 0) {
                            bombs.add(new Bomb());
                        }
                    }
                });
                if (cnt != -1)
                    cnt++;
                /*map = updates.getMap();
                bomberman = updates.getBomberman();
                balloom = updates.getBalloom();
                oneal = updates.getOneal();
                entities = updates.getEntities();
                bombs = updates.getBombs();
                hearts = updates.getHearts();
                stuffObjects = updates.getStuffObjects();*/
                if (bomberman.size() > 0 && updates.collision(bomberman.get(0).getX(), bomberman.get(0).getY(), 3 * 40, 7 * 40, 20)) {
                    frameBomber = 2;
                    rspeedItem = new Flame();
                    speedItem = new Flame();
                }
                if (bomberman.size() > 0 && updates.collision(bomberman.get(0).getX(), bomberman.get(0).getY(), 12 * 40, 5 * 40, 20)) {
                    rflameItem = new Flame();
                    flameItem = new Flame();
                }
                if (bomberman.size() > 0 && updates.collision(bomberman.get(0).getX(), bomberman.get(0).getY(), 17 * 40, 11 * 40, 20)) {
                    rbombItem = new Flame();
                    rbombItem = new Flame();
                }
                if (hearts.size() == 0) {
                    this.stop();
                    //timer.stop();
                    showOver();
                }
                if (bomberman.size() > 0 && balloom.size() == 0 && oneal.size() == 0 && updates.collision(bomberman.get(0).getX(), bomberman.get(0).getY(), 6 * 40, 10 * 40, 20)) {
                    this.stop();
                    gameWin();
                }

                /*map = updates.getMap();
                bomberman = updates.getBomberman();
                balloom = updates.getBalloom();
                oneal = updates.getOneal();
                entities = updates.getEntities();
                bombs = updates.getBombs();
                hearts = updates.getHearts();
                stuffObjects = updates.getStuffObjects();*/

                if (bomberman.size() > 0 && updates.collision(bomberman.get(0).getX(), bomberman.get(0).getY(), 3 * 40, 7 * 40, 20)) {
                    frameBomber = 2;
                    rspeedItem = new Flame();
                    speedItem = new Flame();
                }
                if (bomberman.size() > 0 && updates.collision(bomberman.get(0).getX(), bomberman.get(0).getY(), 12 * 40, 5 * 40, 20)) {
                    rflameItem = new Flame();
                    flameItem = new Flame();
                }
                if (bomberman.size() > 0 && updates.collision(bomberman.get(0).getX(), bomberman.get(0).getY(), 17 * 40, 11 * 40, 20)) {
                    rbombItem = new Flame();
                    rbombItem = new Flame();
                }
                if (hearts.size() == 0) {
                    this.stop();
                }
                if (bomberman.size() > 0 && balloom.size() == 0 && oneal.size() == 0 && updates.collision(bomberman.get(0).getX(), bomberman.get(0).getY(), 6 * 40, 10 * 40, 20)) {
                    this.stop();
                }

            }
        };
        timer.start();

        //createMap();
    }

    public static void createMap() {
        clearObjs();
        stillObjects.clear();

        bomberman.add(new Bomber(1, 3, Sprite.player[1][0].getFxImage()));
        balloom.add(new Bomber(18, 11, Sprite.balloom_left[0].getFxImage()));
        balloom.add(new Bomber(18, 2, Sprite.balloom_left[0].getFxImage()));
        oneal.add(new Bomber(10, 7, Sprite.oneal_left[0].getFxImage()));
        oneal.add(new Bomber(2, 11, Sprite.oneal_left[0].getFxImage()));
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < 2; j++) {
                stillObjects.add(new StaticEntity(i, j, Sprite.grass[20][15].getFxImage()));
            }
        }
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 2; j < HEIGHT; j++) {
                if (j == 2 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1 || (i > 2 && i % 2 == 1 && j % 2 == 0 && i < 19 && j < 14)) {
                    stillObjects.add(new StaticEntity(i, j, Sprite.wall.getFxImage()));
                } else {
                    stillObjects.add(new StaticEntity(i, j, Sprite.grass[i - 1][j - 1].getFxImage()));
                }
            }
        }
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map.get(i, j) == '*') {
                    stuffObjects.add(new Bomb(i, j, Sprite.brick[0].getFxImage()));
                }
            }
        }


        hearts.add(new StaticEntity(2, 0, Sprite.heart.getFxImage()));
        hearts.add(new StaticEntity(1, 0, Sprite.heart.getFxImage()));
        hearts.add(new StaticEntity(0, 0, Sprite.heart.getFxImage()));

        bombItem = new StaticEntity(17, 0, Sprite.BombItem.getFxImage());
        speedItem = new StaticEntity(18, 0, Sprite.SpeedItem.getFxImage());
        flameItem = new StaticEntity(19, 0, Sprite.FlameItem.getFxImage());

        rbombItem = new StaticEntity(17, 11, Sprite.BombItem.getFxImage());
        rspeedItem = new StaticEntity(3, 7, Sprite.SpeedItem.getFxImage());
        rflameItem = new StaticEntity(12, 5, Sprite.FlameItem.getFxImage());
    }

    public void render() {
        // Xóa màn hình cũ đi
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // render các đối tượng tĩnh
        stillObjects.forEach(g -> g.render(gc));

        //render các đối tượng động
        bombs.forEach(g -> g.render(gc));
        rbombItem.render(gc);
        rspeedItem.render(gc);
        rflameItem.render(gc);
        portal.render(gc);
        stuffObjects.forEach(g -> g.render(gc));
        hearts.forEach(g -> g.render(gc));

        bombItem.render(gc);
        speedItem.render(gc);
        flameItem.render(gc);


        //entities.get(0).render(gc);

        balloom.forEach(g -> g.render(gc));
        oneal.forEach(g -> g.render(gc));
        bomberman.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));

    }
}
