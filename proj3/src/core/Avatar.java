package core;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;

public class Avatar {
    private static final Color color = Color.RED;
    private static final TETile tile =  new TETile('a', color, Color.black, "Avatar");
    private TETile[][] projWorld;
    private static final int width = 1;
    private static final int height = 1;
    public int xPos;
    public int yPos;

    Point pos;
    public Avatar(TETile[][] projWorld, int startX, int startY) {
        this.xPos = startX;
        this.yPos = startY;
        this.projWorld = projWorld;
        projWorld[startX][startY] = tile;
    }

     // Does not check if canMove.
     // Have to change if avator is more than 1 pixel
    public void drawAvatar(TETile[][] projWorld, int x, int y) {
        projWorld[x][y] = tile;
    }

    private boolean canAvatarMove(int deltaX, int deltaY) {
        if (projWorld[xPos + deltaX][yPos + deltaY] == World.FLOORREP) {
            return true;
        }
        return false;
    }
    public void avatarMove(int deltaX, int deltaY) {
        if (canAvatarMove(deltaX, deltaY)) {
            drawAvatar(projWorld, xPos + deltaX, yPos + deltaY);
            projWorld[xPos][yPos] = World.FLOORREP;
            xPos += deltaX;
            yPos += deltaY;
        }
    }

//    private void userInputHandler(char c) {
//        switch (c) {
//            case 'a':
//                movement.tryMove(-1, 0);
//                break;
//            case 's':
//                movement.tryMove(0, -1);
//                break;
//            case 'd':
//                movement.tryMove(1, 0);
//                break;
//            case 'q':
//                movement.rotateLeft();
//                break;
//            case 'w':
//                movement.rotateRight();
//                break;
//        }
//
//    }
}
