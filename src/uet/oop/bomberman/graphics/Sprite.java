package uet.oop.bomberman.graphics;

import javafx.scene.image.*;


/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */
public class Sprite {

    public static final int SCALED_SIZE = 40; //scale hình gốc
    private static final int TRANSPARENT_COLOR = 0xffffffff; // color keying
    public final int SIZE = 40; // = DEFAULT_SIZE
    private int _x, _y;
    public int[] _pixels;
    private SpriteSheet _sheet; //_sheet.size = tiles.size

    public static Sprite[][] grass = new Sprite[21][21];
    public static Sprite[] brick = new Sprite[6];
    public static Sprite wall = new Sprite(14, 15, SpriteSheet.tiles);
    public static Sprite heart = new Sprite(15, 15, SpriteSheet.tiles);

    public static Sprite[][] player = new Sprite[6][6];


    public static Sprite[] balloom_left = new Sprite[6];
    public static Sprite[] balloom_right = new Sprite[6];

    public static Sprite[] flame_center = new Sprite[6];
    public static Sprite[] flame_up = new Sprite[6];
    public static Sprite[] flame_down = new Sprite[6];
    public static Sprite[] flame_left = new Sprite[6];
    public static Sprite[] flame_right = new Sprite[6];

    public static Sprite[] bomb = new Sprite[6];
    public static Sprite BombItem = new Sprite(0, 16, SpriteSheet.tiles);
    public static Sprite FlameItem = new Sprite(1, 16, SpriteSheet.tiles);
    public static Sprite SpeedItem = new Sprite(2, 16, SpriteSheet.tiles);

    public static Sprite test = new Sprite();

    // Load đối tượng vào màn hình
    public Sprite(int x, int y, SpriteSheet sheet) {
        _pixels = new int[((SIZE * SIZE)*SIZE)];
        _x = x * SIZE;
        _y = y * SIZE;
        _sheet = sheet;
        load();
    }
    public Sprite() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 15; j++) {
                grass[i][j] = new Sprite(i, j, SpriteSheet.tiles);
            }
        }
        grass[20][15] = new Sprite(14, 14, SpriteSheet.tiles);
        player[0][0] = new Sprite(16, 15, SpriteSheet.tiles);
        player[0][1] = new Sprite(17, 15, SpriteSheet.tiles);
        player[0][2] = new Sprite(18, 15, SpriteSheet.tiles);
        player[0][3] = new Sprite(19, 15, SpriteSheet.tiles);

        player[1][0] = new Sprite(16, 16, SpriteSheet.tiles);
        player[1][1] = new Sprite(17, 16, SpriteSheet.tiles);
        player[1][2] = new Sprite(18, 16, SpriteSheet.tiles);
        player[1][3] = new Sprite(19, 16, SpriteSheet.tiles);

        player[2][0] = new Sprite(16, 17, SpriteSheet.tiles);
        player[2][1] = new Sprite(17, 17, SpriteSheet.tiles);
        player[2][2] = new Sprite(18, 17, SpriteSheet.tiles);
        player[2][3] = new Sprite(19, 17, SpriteSheet.tiles);

        player[3][0] = new Sprite(16, 18, SpriteSheet.tiles);
        player[3][1] = new Sprite(17, 18, SpriteSheet.tiles);
        player[3][2] = new Sprite(18, 18, SpriteSheet.tiles);
        player[3][3] = new Sprite(19, 18, SpriteSheet.tiles);

        balloom_left[0] = new Sprite(8, 16, SpriteSheet.tiles);
        balloom_left[1] = new Sprite(9, 16, SpriteSheet.tiles);
        balloom_left[2] = new Sprite(10, 16, SpriteSheet.tiles);
        balloom_left[3] = new Sprite(11, 16, SpriteSheet.tiles);

        balloom_right[0] = new Sprite(8, 15, SpriteSheet.tiles);
        balloom_right[1] = new Sprite(9, 15, SpriteSheet.tiles);
        balloom_right[2] = new Sprite(10, 15, SpriteSheet.tiles);
        balloom_right[3] = new Sprite(11, 15, SpriteSheet.tiles);

        bomb[0] = new Sprite(7, 19, SpriteSheet.tiles);
        bomb[1] = new Sprite(8, 19, SpriteSheet.tiles);
        bomb[2] = new Sprite(9, 19, SpriteSheet.tiles);
        bomb[3] = new Sprite(10, 19, SpriteSheet.tiles);

        flame_center[0] = new Sprite(3, 15, SpriteSheet.tiles);
        flame_center[1] = new Sprite(2, 15, SpriteSheet.tiles);
        flame_center[2] = new Sprite(1, 15, SpriteSheet.tiles);
        flame_center[3] = new Sprite(0, 15, SpriteSheet.tiles);

        flame_up[0] = new Sprite(15, 19, SpriteSheet.tiles);
        flame_up[1] = new Sprite(15, 18, SpriteSheet.tiles);
        flame_up[2] = new Sprite(15, 17, SpriteSheet.tiles);
        flame_up[3] = new Sprite(15, 16, SpriteSheet.tiles);

        flame_down[0] = new Sprite(14, 16, SpriteSheet.tiles);
        flame_down[1] = new Sprite(14, 17, SpriteSheet.tiles);
        flame_down[2] = new Sprite(14, 18, SpriteSheet.tiles);
        flame_down[3] = new Sprite(14, 19, SpriteSheet.tiles);

        flame_right[0] = new Sprite(4, 15, SpriteSheet.tiles);
        flame_right[1] = new Sprite(5, 15, SpriteSheet.tiles);
        flame_right[2] = new Sprite(6, 15, SpriteSheet.tiles);
        flame_right[3] = new Sprite(7, 15, SpriteSheet.tiles);

        flame_left[0] = new Sprite(7, 14, SpriteSheet.tiles);
        flame_left[1] = new Sprite(6, 14, SpriteSheet.tiles);
        flame_left[2] = new Sprite(5, 14, SpriteSheet.tiles);
        flame_left[3] = new Sprite(4, 14, SpriteSheet.tiles);

        brick[0] = new Sprite(16, 19, SpriteSheet.tiles);
        brick[1] = new Sprite(17, 19, SpriteSheet.tiles);
        brick[2] = new Sprite(18, 19, SpriteSheet.tiles);
        brick[3] = new Sprite(19, 19, SpriteSheet.tiles);
    }

    private void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                _pixels[x + y * SIZE] = _sheet._pixels[((x + _x) + (y + _y) * _sheet.SIZE)];
            }
        }
    }

    public Image getFxImage() {
        WritableImage wr = new WritableImage(SIZE, SIZE);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if ( _pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, _pixels[x + y * SIZE]);
                }
            }
        }
        return new ImageView(wr).getImage();
    }
}