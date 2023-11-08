package ms.graha.b2.freeswim1.simulation_old;

import java.util.List;

import ms.graha.b2.freeswim1.simulation_old.Ant.AntSeekingMode;

import java.util.ArrayList;


    public class SimulationContext {
        private int width;
        private int height;
        private List<Ant> ants;
        private final int antSpeed = 1;
        private float[][] foodPheromone;
        private float[][] nestPheromone;
        private float[][] blurKernel;
        private Food food;
        private Nest nest;

        public SimulationContext(int width, int height, int antCount) {
            this.width = width;
            this.height = height;
            this.nest = new Nest(width/2, height/2, 10);
            food = new Food((int) (Math.random() * width), (int) (Math.random() * height),20);

            this.ants = new ArrayList<Ant>();
            for (int i = 0; i < antCount; i++) {
                ants.add(new Ant(this));
            }
                this.foodPheromone = new float[width][height];
                this.nestPheromone =  new float[width][height];

                // 7x7 guassian blur kernel
                this.blurKernel = new float[][] {
            {0.00000067f, 0.00002292f, 0.00019117f, 0.00038771f, 0.00019117f, 0.00002292f, 0.00000067f},
            {0.00002292f, 0.00078634f, 0.00655965f, 0.01330373f, 0.00655965f, 0.00078634f, 0.00002292f},
            {0.00019117f, 0.00655965f, 0.05472157f, 0.11098164f, 0.05472157f, 0.00655965f, 0.00019117f},
            {0.00038771f, 0.01330373f, 0.11098164f, 0.22508352f, 0.11098164f, 0.01330373f, 0.00038771f},
            {0.00019117f, 0.00655965f, 0.05472157f, 0.11098164f, 0.05472157f, 0.00655965f, 0.00019117f},
            {0.00002292f, 0.00078634f, 0.00655965f, 0.01330373f, 0.00655965f, 0.00078634f, 0.00002292f},
            {0.00000067f, 0.00002292f, 0.00019117f, 0.00038771f, 0.00019117f, 0.00002292f, 0.00000067f}
        };

    }
        public void tick() {
            for (Ant ant : ants) {
                if (ant.getSeekingMode() == AntSeekingMode.NEST) {
                         // Add the blur kernel to the food matrix
                    for (int i = 0; i < blurKernel.length; i++) {
                        for (int j = 0; j < blurKernel[i].length; j++) {
                            int x = (int) (ant.getX() + i - blurKernel.length/2);
                            int y = (int) (ant.getY() + j - blurKernel[i].length/2);
                            if (x >= 0 && x < width && y >= 0 && y < height) {
                                foodPheromone[x][y] += blurKernel[i][j];
                            }
                        }
                    }
                    
                } else
                {
                    // Add the blur kernel to the nestPheromone matrix
                    for (int i = 0; i < blurKernel.length; i++) {
                        for (int j = 0; j < blurKernel[i].length; j++) {
                            int x = (int) (ant.getX() + i - blurKernel.length/2);
                            int y = (int) (ant.getY() + j - blurKernel[i].length/2);
                            if (x >= 0 && x < width && y >= 0 && y < height) {
                                nestPheromone[x][y] += blurKernel[i][j];
                            }
                        }
                    }
                
                }
                ant.tick();
            }
            // Decay the pheromone

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                   foodPheromone[i][j] *= 0.999;
                   nestPheromone[i][j] *= 0.999;
                }
            }
        }

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

        public List<Ant> getAnts() {
            return ants;
        }

        public void setAnts(List<Ant> ants) {
            this.ants = ants;
        }

        public int getAntSpeed() {
            return antSpeed;
        }

        public float[][] getFoodPheromone() {
            return foodPheromone;
        }
      public Food getFood() {
                return food;
            }

            public void setFood(Food food) {
                this.food = food;
            }

            public Nest getNest() {
                return nest;
            }

            public void setNest(Nest nest) {
                this.nest = nest;
            }
        public void setFoodPheromone(float[][] foodPheromone) {
            this.foodPheromone = foodPheromone;
        }

        public float[][] getNestPheromone() {
            return nestPheromone;
        }

    }

