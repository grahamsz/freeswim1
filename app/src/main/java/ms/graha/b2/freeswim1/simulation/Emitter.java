package ms.graha.b2.freeswim1.simulation;

public class Emitter {
    public float x;
    public float y;
    public float hue;
    public boolean enabled;

    private static final int MAX_HISTORY_SIZE = 10;
    private float[] xHistory = new float[MAX_HISTORY_SIZE];
    private float[] yHistory = new float[MAX_HISTORY_SIZE];
    private int historyIndex = 0;

    public void tick() {
        // Save the current x and y values in the history arrays
        xHistory[historyIndex] = x;
        yHistory[historyIndex] = y;

        // Increment the history index and wrap it around
        historyIndex = (historyIndex + 1) % MAX_HISTORY_SIZE;
    }

    // Access the oldest x and y values
    public float getOldestX() {
        return xHistory[historyIndex];
    }

    public float getOldestY() {
        return yHistory[historyIndex];
    }


     // Access the most recent x and y values
     public float getMostRecentX() {
        int lastIndex = (historyIndex - 1 + MAX_HISTORY_SIZE) % MAX_HISTORY_SIZE;
        return xHistory[lastIndex];
    }

    public float getMostRecentY() {
        int lastIndex = (historyIndex - 1 + MAX_HISTORY_SIZE) % MAX_HISTORY_SIZE;
        return yHistory[lastIndex];
    }

}
