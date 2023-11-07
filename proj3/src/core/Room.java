package core;

import java.util.*;

public class Room implements worldComponents{
    private int length;
    private int width;

    public Room(Random worldSeed) {
        length = worldSeed.nextInt();
        width = worldSeed.nextInt();

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
        return List.of(width,length);
    }
}
