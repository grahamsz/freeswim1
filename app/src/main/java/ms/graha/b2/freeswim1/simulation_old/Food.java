package ms.graha.b2.freeswim1.simulation_old;

public class Food {
    private double x;
    private double y;
    private double diameter;

    public Food(double x, double y, double diameter) {
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }
}
    

