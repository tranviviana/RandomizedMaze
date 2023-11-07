package core;


import java.util.*;

public interface worldComponents {
    /* answers to whether we can place the component on the grid at that locaiton if the
    component is filling in from the left to the right upwards */

    public boolean placeable(int x, int y);
    /* depending on the random function and the grid size to decide whether to terminate calls to object creation */
    public boolean canCreateMore();
    /* for testing purposes but returns the width and length of the object */
    public List<Integer> dimensions();


}
