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
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<MovingEntity> stuffObjects = new ArrayList<>();
    private static List<Integer> timeStuffObjects = new ArrayList<>();
    private static List<Entity> hearts = new ArrayList<>();
    private static List<MovingEntity> bomberman = new ArrayList<>();
    private static List<MovingEntity> balloom = new ArrayList<>();
    private static List<MovingEntity> bombs = new ArrayList<>();
    private static List<MovingEntity> entities = new ArrayList<>();
    private static Entity bombItem = new Flame();
    private static Entity speedItem = new Flame();
    private static Entity flameItem = new Flame();

    public static void Christmas() {
//        ImageView imageView = new ImageView(new Image("/textures/SNOW.gif"));
//        imageView.setFitWidth(800);
//        imageView.setFitHeight(520);
//        root.getChildren().add(imageView);

        startGameSpecial();
        gSnow = new gameSnow();
        root.getChildren().add(gSnow);
    }

    public static void startLevel1() {
        levelStartSound = new MediaPlayer(new Media(new File("src/Music/StartLevel.mp3").toURI().toString()));
        levelStartSound.setVolume(10);
        levelStartSound.play();
        Image startLevel = new Image("/textures/level/STAGE1.png") ;
        ImageView imageView = new ImageView();
        imageView.setFitWidth(800);
        imageView.setFitHeight(520);
        root.getChildren().add(imageView);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(imageView.imageProperty(), startLevel)),
                new KeyFrame(Duration.seconds(2), new KeyValue(imageView.imageProperty(), null))
        );
        timeline.play();
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static void startGame() {
        root.getChildren().remove(bg);
        bg = null;
        startLevel1();
        gameSound.playBackgroundMusic();
        //createMap();
    }

    public static void startGameSpecial() {
        root.getChildren().remove(bg);
        bg = null;
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
            if(key.getCode()==KeyCode.ENTER) {
                if(root.getChildren().contains(imageView)) {
                    gameSound.playClick();
                    root.getChildren().remove(imageView);
                    BombermanGame.startGame();
                }
            }

            else if(key.getCode()==KeyCode.ESCAPE) {
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

        //update
        Update updates = new Update();

        bomberman.add(new Bomber(1, 3, Sprite.player[1][0].getFxImage()));

        balloom.add(new Bomber(18, 11, Sprite.balloom_left[0].getFxImage()));
        //MovingEntity bomb = new Bomb();
        AnimationTimer timer = new AnimationTimer() {
            int cnt = -1;
            int dBomber = 0;
            MovingEntity bomb = new Bomb();
            @Override
            public void handle(long l) {
                render();
                /*if (cntBombExplode != - 1) {
                    int dx = entities.get(0).getX() - entities.get(0).getX() % 40;
                    int dy = entities.get(0).getY() - entities.get(0).getY() % 40;
                    if ((dx == posX && dy == posY) || (dx == posX - 40 && dy == posY) || (dx == posX + 40 && dy == posY) || (dx == posX && dy == posY + 40) || (dx == posX && dy == posY - 40)) {
                        return;
                    }
                }*/
                updates.update(stuffObjects, hearts, entities, bomberman, balloom, cnt, dBomber, bombs, map);
                scene.setOnKeyPressed(e -> {
                    if (!(cnt >= 0 && cnt < 4)) {
                        if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
                            cnt = 0;
                            dBomber = 0;
                        } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
                            cnt = 0;
                            dBomber = 1;
                        } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                            cnt = 0;
                            dBomber = 2;
                        } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                            cnt = 0;
                            dBomber = 3;
                        } else if (e.getCode() == KeyCode.SPACE && bombs.size() == 0) {
                            bombs.add(new Bomb());
                        }
                    }
                });
                if (cnt != -1)
                    cnt++;
                map = updates.getMap();
                bomberman = updates.getBomberman();
                balloom = updates.getBalloom();
                entities = updates.getEntities();
                bombs = updates.getBombs();
                hearts = updates.getHearts();
                stuffObjects = updates.getStuffObjects();
                if (hearts.size() == 0) {
                    this.stop();
                }

            }
        };
        timer.start();

        createMap();
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < 2; j++) {
                stillObjects.add(new StaticEntity(i, j, Sprite.grass[20][15].getFxImage()));
            }
        }
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 2; j < HEIGHT; j++) {
                if (j == 2 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1 || (i > 2 && i % 2 == 1 && j % 2 == 0 && i < 19 && j < 14)) {
                    stillObjects.add(new StaticEntity(i, j, Sprite.wall.getFxImage()));
                }
                else {
                    stillObjects.add(new StaticEntity(i, j, Sprite.grass[i - 1][j - 1].getFxImage()));
                }
            }
        }
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map.get(i, j) == '*') {
                    stuffObjects.add(new Bomb(i, j, Sprite.brick[0].getFxImage()));
                    timeStuffObjects.add(0);
                }
            }
        }


        hearts.add(new StaticEntity(2, 0, Sprite.heart.getFxImage()));
        hearts.add(new StaticEntity(1, 0, Sprite.heart.getFxImage()));
        hearts.add(new StaticEntity(0, 0, Sprite.heart.getFxImage()));

        bombItem = new StaticEntity(17, 0, Sprite.BombItem.getFxImage());
        speedItem = new StaticEntity(18, 0, Sprite.SpeedItem.getFxImage());
        flameItem = new StaticEntity(19, 0, Sprite.FlameItem.getFxImage());
    }

    public void render() {
        // Xóa màn hình cũ đi
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // render các đối tượng tĩnh
        stillObjects.forEach(g -> g.render(gc));

        //render các đối tượng động
        bombs.forEach(g -> g.render(gc));

        stuffObjects.forEach(g -> g.render(gc));
        hearts.forEach(g -> g.render(gc));

        bombItem.render(gc);
        speedItem.render(gc);
        flameItem.render(gc);
        //entities.get(0).render(gc);

        balloom.forEach(g -> g.render(gc));
        bomberman.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
