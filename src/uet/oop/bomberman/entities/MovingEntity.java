package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class MovingEntity extends Entity {
    public  MovingEntity() {

    }
    public MovingEntity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public boolean check(int addX, int addY) {
        if (this.x + addX >= 40 && this.x + addX <= 720 && this.y + addY >= 120 && this.y + addY <= 440)
            return true;
        return false;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update(int x, int y, Image img);
}
