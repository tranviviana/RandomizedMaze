package core;

import java.util.List;
import java.util.Random;

public class Hallway implements worldComponents {
    private Random randomGenerator;
    private int length;
    private static final int width = 1;
    public Hallway() {
        randomGenerator = new Random();
        length = randomGenerator.nextInt();
    }
    @Override
    public boolean placeable(int x, int y) {
        return false;
    }

    @Override
    public boolean canCreateMore() {
        return false;
    }

    @Override
    public List<Integer> dimensions() {
        return List.of(width, length);
    }
}
