package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class StaticEntity extends Entity {

    public StaticEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update(int x, int y, Image img) {

    }
}