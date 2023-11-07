package core;
import java.util.*;

public class Hallway implements worldComponents {
    private Random randomGenerator;
    private int length;
    private static final int width = 1;
    public Hallway(Random worldSeed) {
        randomGenerator = worldSeed;
        length = randomGenerator.nextInt();
    }
    @Override
    public boolean placeable() {
        return false;
    }



    @Override
    public List<Integer> dimensions() {
        return List.of(width, length);
    }
}
