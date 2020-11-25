package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Bomb extends MovingEntity {

    public Bomb() {

    }
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update(int x, int y, Image img) {
        this.x = x - (x % 40);
        this.y = y - (y % 40);
        this.img = img;
    }
}