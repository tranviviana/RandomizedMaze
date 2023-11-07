package core;


import java.util.*;

public interface worldComponents {
    /* answers to whether we can place the component on the grid at that location if the
    component is filling in from the left to the right upwards */
    public boolean placeable();
    /* for testing purposes but returns the width then length of the object */

}
