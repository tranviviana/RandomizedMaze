package core;

import java.util.List;

public class Hallway implements worldComponents {
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
        return null;
    }
}