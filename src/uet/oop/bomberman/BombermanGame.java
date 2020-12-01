package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Pair;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 13;
    public Map map = new Map();
    //public static final MovingEntity open = new MovingEntity();
    private GraphicsContext gc;
    private Canvas canvas;
    //Các đối tượng động
    private List<MovingEntity> entities = new ArrayList<>();

    //Các đối tượng tĩnh: grass, wall
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> stuffObjects = new ArrayList<>();
    private List<Entity> hearts = new ArrayList<>();
    private Entity bombItem = new Flame();
    private Entity speedItem = new Flame();
    private Entity flameItem = new Flame();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Update updates = new Update();
        //MovingEntity bomb = new Bomb();
        MovingEntity bomberman = new Bomber(1, 3, Sprite.player[1][0].getFxImage());
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
                entities = updates.update(entities, cnt, dBomber, bomb );
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
                        } else if (e.getCode() == KeyCode.SPACE && entities.size() == 2) {
                            bomb = new Bomb();
                            entities.add(bomb);
                        }
                    }
                });
                if (cnt != -1)
                    cnt++;

            }
        };
        timer.start();

        createMap();

        // Khởi tạo vị trí của bomber nằm ở ô x, y trong map và hướng nhân vật quay sang trái

        entities.add(bomberman);
        //Thêm bomber vào object entities

        MovingEntity balloom = new Bomber(18, 11, Sprite.balloom_left[0].getFxImage());
        entities.add(balloom);

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
                    stuffObjects.add(new StaticEntity(i, j, Sprite.brick.getFxImage()));
                }
            }
        }
        hearts.add(new StaticEntity(0, 0, Sprite.heart.getFxImage()));
        hearts.add(new StaticEntity(1, 0, Sprite.heart.getFxImage()));
        hearts.add(new StaticEntity(2, 0, Sprite.heart.getFxImage()));

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
        entities.forEach(g -> g.render(gc));

        stuffObjects.forEach(g -> g.render(gc));
        hearts.forEach(g -> g.render(gc));

        bombItem.render(gc);
        speedItem.render(gc);
        flameItem.render(gc);
        //entities.get(0).render(gc);
    }
}
