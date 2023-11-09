package ms.graha.b2.freeswim1.simulation;

public class SimulationContext {

    private int width;
    private int height;
    private int numberOfParticles;
    private Particle[] particles;
    private int particleIndex = 0;



    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumberOfParticles() {
        return numberOfParticles;
    }

    public void setNumberOfParticles(int numberOfParticles) {
        this.numberOfParticles = numberOfParticles;
    }

    public Particle[] getParticles() {
        return particles;
    }

    public SimulationContext(int width, int height, int numberOfParticles) {
        this.height = height;
        this.width = width;
        this.numberOfParticles = numberOfParticles;
        this.particles = new Particle[numberOfParticles];
        this.particleIndex = 0;
    }

    public void emitParticles(int x, int y, float vx, float vy, float hue, int numberOfParticles) {
        for (int i = 0; i < numberOfParticles; i++) {
            particles[particleIndex] = new Particle(x, y,vx,vy,hue,this);
            particleIndex = (particleIndex + 1) % this.numberOfParticles; // this will rewrite older ones quickly!
        }
    }

    public void tick() {
        for (int i = 0; i < numberOfParticles; i++) {
            if (particles[i] != null) {
                particles[i].update();
            }
        }
    }
}
