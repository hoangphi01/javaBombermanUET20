package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Map;

public abstract class MovingEntity extends Entity {
    public  MovingEntity() {

    }
    public MovingEntity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }
    public boolean check(int addX, int addY, char val, Map map)  {
        if (this.x % Sprite.SCALED_SIZE == 0 && this.y % Sprite.SCALED_SIZE == 0) {
            if (addX == 0 && addY == 5) {
                if (map.get(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE + 1) == val)
                    return true;
                return false;
            }
            else if (addX == 0 && addY == -5) {
                if (map.get(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE - 1) == val)
                    return true;
                return false;
            }
            else if (addX == 5 && addY == 0) {
                if (map.get(this.x / Sprite.SCALED_SIZE + 1, this.y / Sprite.SCALED_SIZE) == val)
                    return true;
                return false;
            }
            else {
                if (map.get(this.x / Sprite.SCALED_SIZE - 1, this.y / Sprite.SCALED_SIZE) == val)
                    return true;
                return false;
            }
        }
        else if (this.x % Sprite.SCALED_SIZE != 0 && this.y % Sprite.SCALED_SIZE == 0) {
            if (addX == 0 && addY == 5) {
                if (map.get(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE + 1) == val && map.get(this.x / Sprite.SCALED_SIZE + 1, this.y / Sprite.SCALED_SIZE + 1) == val)
                    return true;
                return false;
            }
            else if (addX == 0 && addY == -5) {
                if (map.get(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE - 1) == val && map.get(this.x / Sprite.SCALED_SIZE + 1, this.y / Sprite.SCALED_SIZE - 1) == val)
                    return true;
                return false;
            }
            else {
                return true;
            }
        }
        else if (this.x % Sprite.SCALED_SIZE == 0 && this.y % Sprite.SCALED_SIZE != 0) {
            if (addX == 0 && addY == 5) {
                return true;
            }
            else if (addX == 0 && addY == -5) {
                return true;
            }
            else if (addX == 5 && addY == 0) {
                if (map.get(this.x / Sprite.SCALED_SIZE + 1, this.y / Sprite.SCALED_SIZE) == val && map.get(this.x / Sprite.SCALED_SIZE + 1, this.y / Sprite.SCALED_SIZE + 1) == val)
                    return true;
                return false;
            }
            else {
                if (map.get(this.x / Sprite.SCALED_SIZE - 1, this.y / Sprite.SCALED_SIZE) == val && map.get(this.x / Sprite.SCALED_SIZE - 1, this.y / Sprite.SCALED_SIZE + 1) == val)
                    return true;
                return false;
            }
        }
        else {
            return true;
        }
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update(int x, int y, Image img);
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
}
