package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends MovingEntity {
    int cnt = 0;
    public Bomber(int x, int y, Image img) {

        super( x, y, img);
        System.out.println(x + " " + y);
    }

    @Override
    public void update(int x, int y, Image img) {
        this.x += x;
        this.y += y;
        this.img = img;
    }
}