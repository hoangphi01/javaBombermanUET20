package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends MovingEntity {
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    @Override
    public void update(int x, int y, Image img) {
        this.x += x;
        this.y += y;
        this.img = img;
    }
}