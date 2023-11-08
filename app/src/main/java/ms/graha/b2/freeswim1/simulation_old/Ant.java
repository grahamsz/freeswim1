package ms.graha.b2.freeswim1.simulation_old;

import java.util.Random;

public class Ant {
    private float x;
    private float y;
    private int direction;
    private AntSeekingMode seekingMode;
    
    public AntSeekingMode getSeekingMode() {
        return seekingMode;
    }

    public void setSeekingMode(AntSeekingMode seekingMode) {
        this.seekingMode = seekingMode;
    }

    private SimulationContext context;

       static Random rand = new Random();
    public enum AntSeekingMode {
        FOOD,
        NEST
    }

    public Ant(SimulationContext context) {
        this.context = context;
        this.x = (float) context.getNest().getX();
        this.y = (float)  context.getNest().getY();

        this.direction = rand.nextInt(360);

            this.seekingMode = AntSeekingMode.FOOD;
  
    }

    public void tick() {



        // check for pheromones
        int leftX = (int) (x + 2 * Math.cos(Math.toRadians(direction - 45)));
        int leftY = (int) (y + 2 * Math.sin(Math.toRadians(direction - 45)));
        int rightX = (int) (x + 2 * Math.cos(Math.toRadians(direction + 45)));
        int rightY = (int) (y + 2 * Math.sin(Math.toRadians(direction + 45)));

        if (seekingMode == AntSeekingMode.FOOD) {
           
            // test if the ant is on food
            if (context.getFood().getX() - context.getFood().getDiameter()/2 < x && x < context.getFood().getX() + context.getFood().getDiameter()/2 &&
                    context.getFood().getY() - context.getFood().getDiameter()/2 < y && y < context.getFood().getY() + context.getFood().getDiameter()/2) {
                // the ant is on food, switch to nest seeking mode
                seekingMode = AntSeekingMode.NEST;
            } else{

            if (context.getFoodPheromone()[leftX][ leftY] > context.getFoodPheromone()[rightX][rightY]) {
                direction -= 5;
            } else if (context.getFoodPheromone()[leftX][ leftY] < context.getFoodPheromone()[rightX][rightY]) {
                direction += 5;
            } 
        }

        } else {
           //test if the ant is on nest

            if (context.getNest().getX() - context.getNest().getDiameter()/2 < x && x < context.getNest().getX() + context.getNest().getDiameter()/2 &&
                    context.getNest().getY() - context.getNest().getDiameter()/2 < y && y < context.getNest().getY() + context.getNest().getDiameter()/2) {
                // the ant is on nest, switch to food seeking mode
                seekingMode = AntSeekingMode.FOOD;
            } else
            {
            if (context.getNestPheromone()[leftX][ leftY] > context.getNestPheromone()[rightX][rightY]) {
                direction -= 5;
            } else if (context.getNestPheromone()[leftX][ leftY] < context.getNestPheromone()[rightX][rightY]) {
                direction += 5;
            } 
            } 
        }


        int randomAngle = rand.nextInt(11) - 5; // random angle between -10 and 10 degrees
        direction += randomAngle;
        x +=  (Math.cos(Math.toRadians(direction)) * context.getAntSpeed());
        y +=  (Math.sin(Math.toRadians(direction)) * context.getAntSpeed());

        // bounce on th edges
        if (x < 5) {
            x = 5;
            direction = 180 - direction;
        } else if ((int) x >= context.getWidth()-5) {
            x = context.getWidth()-6;
            direction = 180 - direction;
        }

        if (y < 5) {
            y = 5;
            direction = 360 - direction;
        } else if ((int) y >= context.getHeight()-5) {
            y = context.getHeight()-6;
            direction = 360 - direction;
        }


    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }
}