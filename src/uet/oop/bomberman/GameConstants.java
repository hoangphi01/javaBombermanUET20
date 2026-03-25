package uet.oop.bomberman;

public final class GameConstants {
    private GameConstants() {}

    // Map dimensions (in tiles)
    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 13;

    // Tile size (in pixels)
    public static final int BLOCK_WIDTH = 40;
    public static final int BLOCK_HEIGHT = 40;

    // Canvas size (in pixels)
    public static final int CANVAS_WIDTH = MAP_WIDTH * BLOCK_WIDTH;
    public static final int CANVAS_HEIGHT = MAP_HEIGHT * BLOCK_HEIGHT;

    // Movement speed (pixels per step)
    public static final int MOVE_SPEED = 5;

    // --- Time-based intervals (in milliseconds) ---

    // Game tick rate: 1 tick every 33ms = ~30 ticks/sec
    public static final long TICK_INTERVAL_MS = 33;

    // Player moves once per tick (5px per 33ms = ~150px/sec = ~3.75 tiles/sec)
    public static final long PLAYER_MOVE_INTERVAL_MS = 80;

    // Bomb fuse time
    public static final long BOMB_FUSE_TIME_MS = 3000; // 3 seconds

    // Explosion phase duration (each of 4 animation phases)
    public static final long EXPLOSION_PHASE_MS = 150;

    // Bomb animation interval (each of 4 sprite frames)
    public static final long BOMB_ANIM_INTERVAL_MS = 250;

    // Respawn delay after death
    public static final long RESPAWN_DELAY_MS = 1500;

    // Enemy movement intervals
    public static final long BALLOOM_MOVE_INTERVAL_MS = 250;
    public static final long ONEAL_MOVE_INTERVAL_MS = 120;

    // Collision threshold (in pixels)
    public static final int COLLISION_THRESHOLD = 20;
    public static final int EXPLOSION_COLLISION_THRESHOLD = 40;

    // Player lives
    public static final int INITIAL_LIVES = 3;

    // Directions: UP=0, DOWN=1, RIGHT=2, LEFT=3
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_LEFT = 3;

    public static final int[][] DIRECTION_DELTAS = {
            {0, -MOVE_SPEED},
            {0, MOVE_SPEED},
            {MOVE_SPEED, 0},
            {-MOVE_SPEED, 0}
    };
}
