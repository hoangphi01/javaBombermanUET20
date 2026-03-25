package uet.oop.bomberman;

/**
 * Time-based timer that ticks at a fixed interval regardless of frame rate.
 * Uses millisecond elapsed time instead of frame counting.
 */
public class GameTimer {
    private long accumulatedMs = 0;
    private final long intervalMs;

    public GameTimer(long intervalMs) {
        this.intervalMs = intervalMs;
    }

    /**
     * Accumulate delta time. Returns how many ticks have elapsed.
     */
    public int update(long deltaMs) {
        accumulatedMs += deltaMs;
        int ticks = 0;
        while (accumulatedMs >= intervalMs) {
            accumulatedMs -= intervalMs;
            ticks++;
        }
        return ticks;
    }

    public void reset() {
        accumulatedMs = 0;
    }
}
