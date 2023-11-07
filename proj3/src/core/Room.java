package core;

import java.util.*;

public class Room implements worldComponents{
    private int length;
    private int width;
    private int xLocation;
    private int yLocation;

    public Room(Random worldSeed, World projWorld, int x, int y) {
        length = worldSeed.nextInt();
        width = worldSeed.nextInt();
        xLocation = x;
        yLocation = y;

    }
    @Override
    public boolean placeable() {
        return false;
    }

    @Override
    public List<Integer> dimensions() {
        return List.of(width,length);
    }
}
