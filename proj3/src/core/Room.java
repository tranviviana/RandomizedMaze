package core;

import java.util.*;

public class Room implements worldComponents{
    private int HEIGHT;
    private int WIDTH;
    private int xLocation;
    private int yLocation;

    public Room (Random worldSeed, World projWorld, int x, int y) {
        HEIGHT = worldSeed.nextInt();
        WIDTH = worldSeed.nextInt();
        xLocation = x;
        yLocation = y;

    }
    @Override
    public boolean placeable() {
        return false;
    }

    @Override
    public List<Integer> dimensions() {
        return List.of(WIDTH,HEIGHT);
    }
}
