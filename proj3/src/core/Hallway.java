package core;
import java.util.*;

public class Hallway implements worldComponents {
    private Random randomGenerator;
    private int HEIGHT;
    private static final int WIDTH = 1;
    public Hallway(Random worldSeed) {
        randomGenerator = worldSeed;
        HEIGHT = randomGenerator.nextInt();
    }
    @Override
    public boolean placeable() {
        return false;
    }




}