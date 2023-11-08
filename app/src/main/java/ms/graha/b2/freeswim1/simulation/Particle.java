package ms.graha.b2.freeswim1.simulation;

import java.util.Random;
// Step 1: Define the Particle class
public class Particle {
    public float x, y, vx, vy, intensity, hue;
    public static Random rand = new Random();
    SimulationContext parent;
    public final float GRAVITY = 3.4f;

    public Particle(float x, float y, float vx, float vy, float intensity, float hue, SimulationContext parent) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.intensity = intensity;
        this.hue = hue;
        this.parent = parent;
    }

    public Particle(int x, int y,float vx, float vy, SimulationContext parent) {
        this.x = x;
        this.y = y;
        this.vx = vx+(float) rand.nextGaussian() * 10;

        this.vy = vx+ (float) rand.nextGaussian() * 15;
        this.intensity = (float) (Math.random() * 0.25f + 0.75f);
        this.hue = (float) (Math.random() * 360);
        this.parent = parent;
    }

    public void update() {
        // Update the position of the particle
        this.x += this.vx;
        this.y += this.vy;

        // bounce if we hit the floor
        if (this.y > parent.getHeight()) {
            this.y = parent.getHeight();
            this.vy *= -0.3f;
            this.vx *= 0.9f;
            this.intensity *= 0.75f;
        }
        this.intensity *= 0.93f;

        vy+=GRAVITY;
    }
}