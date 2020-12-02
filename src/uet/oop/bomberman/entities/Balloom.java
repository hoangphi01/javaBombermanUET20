package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Balloom extends MovingEntity {

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update(int x, int y, Image img) {
        //if (check(x, y))
        {
            this.x += x;
            this.y += y;
            this.img = img;
        }
    }
}