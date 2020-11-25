package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 13;

    private static AnimationTimer timer;
    private static Group root = new Group();
    private static gameBackground bg = new gameBackground();

    private GraphicsContext gc;
    private Canvas canvas;
    //Các đối tượng động
    private static List<MovingEntity> entities = new ArrayList<>();

    //Các đối tượng tĩnh: grass, wall
    private static List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static void startGame() {
        root.getChildren().remove(bg);
        bg = null;
        createMap();
        timer.start();
    }


    @Override
    public void start(Stage stage) {

        stage.setTitle("Bomberman 2020");

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        root.getChildren().addAll(canvas, bg);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        KeyBoard key = new KeyBoard();
        //MovingEntity bomb = new Bomb();
        timer = new AnimationTimer() {
            int cnt = -1;
            int timeBomb = -1;
            int[][] dir = {{0, 5}, {5, 0}, {-5, 0}, {0, -5}};
            int timeBombExplode = -1;
            int cntBalloom = 0;
            int timeBalloom = 0;
            int direction = 0;
            int directionBalloom = 0;
            int posX;
            int posY;
            MovingEntity bomb = new Bomb();

            @Override
            public void handle(long l) {
                render();
                if (cnt >= 0 && cnt < 4) {
                    if (direction == 0) {
                        key.updateUp(entities.get(0), cnt);
                    }
                    else if (direction == 1) {
                        key.updateDown(entities.get(0), cnt);
                    }
                    else if (direction == 2) {
                        key.updateRight(entities.get(0), cnt);
                    }
                    else {
                        key.updateLeft(entities.get(0), cnt);
                    }
                }
                if (timeBomb == 0) {
                        posX = entities.get(0).getX();
                        posY = entities.get(0).getY();
                        key.updateBomb(bomb, timeBomb, posX, posY);
                        timeBomb++;
                }
                if (timeBomb > 0 && timeBomb < 120) {
                        key.updateBomb(bomb, timeBomb, posX, posY);
                        timeBomb++;
                }

                if (timeBomb == 120) {
                    timeBomb = -1;
                    timeBombExplode = 0;
                    entities.remove(bomb);
                }

                if (timeBalloom % 10 == 0) {
                    //directionBalloom = key.updateBalloom(entities.get(1), directionBalloom);
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
                        int value = generator.nextInt(4);
                        while (!entities.get(1).check(dir[value][0], dir[value][1])) {
                            value = generator.nextInt(4);
                        }
                        if (value == 0 || value == 1)
                            entities.get(1).update(dir[value][0], dir[value][1], Sprite.balloom_right1.getFxImage());
                        else
                            entities.get(1).update(dir[value][0], dir[value][1], Sprite.balloom_left1.getFxImage());
                        directionBalloom = value;
                        cntBalloom = 1;
                    }
                    System.out.println(cntBalloom + " " + directionBalloom);
                }
                scene.setOnKeyPressed(e -> {

                    if (e.getCode() == KeyCode.UP) {
                        cnt = 0;
                        direction = 0;
                    }
                    else if (e.getCode() == KeyCode.DOWN) {
                        cnt = 0;
                        direction = 1;
                    }
                    else if (e.getCode() == KeyCode.RIGHT) {
                        cnt = 0;
                        direction = 2;
                    }
                    else if (e.getCode() == KeyCode.LEFT) {
                        cnt = 0;
                        direction = 3;
                    }
                    else if (e.getCode() == KeyCode.SPACE && timeBomb == -1) {

                        entities.add(bomb);
                        timeBomb = 0;
                    }

                });
                if (cnt != -1)
                    cnt++;
                timeBalloom++;
            }
        };
        timer.start();

        createMap();

        // Khởi tạo vị trí của bomber nằm ở ô x, y trong map và hướng nhân vật quay sang trái
        MovingEntity bomberman = new Bomber(10, 10, Sprite.player_down.getFxImage());

        //Thêm bomber vào object entities
        entities.add(bomberman);
        MovingEntity balloom = new Balloom(1, 4, Sprite.balloom_left1.getFxImage());
        entities.add(balloom);
    }

    public static void createMap() {
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
