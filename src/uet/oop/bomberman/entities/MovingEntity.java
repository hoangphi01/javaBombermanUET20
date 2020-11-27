package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Map;

import static java.lang.Math.abs;

public abstract class MovingEntity extends Entity {
    Map map = new Map();

    public  MovingEntity() {

    }
    public MovingEntity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void setBomb(int x, int y) {
        //map.set();
    }
    public boolean check(int addX, int addY) {
        if (this.x % 40 == 0 && this.y % 40 == 0) {
            if (addX == 0 && addY == 5) {
                if (map.get(this.x / 40, this.y / 40 + 1) == ' ')
                    return true;
                return false;
            }
            else if (addX == 0 && addY == -5) {
                if (map.get(this.x / 40, this.y / 40 - 1) == ' ')
                    return true;
                return false;
            }
            else if (addX == 5 && addY == 0) {
                if (map.get(this.x / 40 + 1, this.y / 40) == ' ')
                    return true;
                return false;
            }
            else {
                if (map.get(this.x / 40 - 1, this.y / 40) == ' ')
                    return true;
                return false;
            }
        }
        else if (this.x % 40 != 0 && this.y % 40 == 0) {
            if (addX == 0 && addY == 5) {
                if (map.get(this.x / 40, this.y / 40 + 1) == ' ' && map.get(this.x / 40 + 1, this.y / 40 + 1) == ' ')
                    return true;
                return false;
            }
            else if (addX == 0 && addY == -5) {
                if (map.get(this.x / 40, this.y / 40 - 1) == ' ' && map.get(this.x / 40 + 1, this.y / 40 - 1) == ' ')
                    return true;
                return false;
            }
            else {
                return true;
            }
        }
        else if (this.x % 40 == 0 && this.y % 40 != 0) {
            if (addX == 0 && addY == 5) {
                return true;
            }
            else if (addX == 0 && addY == -5) {
                return true;
            }
            else if (addX == 5 && addY == 0) {
                if (map.get(this.x / 40 + 1, this.y / 40) == ' ' && map.get(this.x / 40 + 1, this.y / 40 + 1) == ' ')
                    return true;
                return false;
            }
            else {
                if (map.get(this.x / 40 - 1, this.y / 40) == ' ' && map.get(this.x / 40 - 1, this.y / 40 + 1) == ' ')
                    return true;
                return false;
            }
        }
        else {
            return true;
        }
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
