package ms.graha.b2.freeswim1.simulation_old;

public class Nest {
    private double x;
    private double y;
    private double diameter;

    public Nest(double x, double y, double diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDiameter() {
        return diameter;
    }
}
