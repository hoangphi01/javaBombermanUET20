package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Map;

public class Flame extends MovingEntity {

    public Flame() {

    }
    public Flame(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public boolean check(int addX, int addY){
        return true;
    }
    @Override
    public void update(int x, int y, Image img) {

    }
}