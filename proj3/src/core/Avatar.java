package core;
import tileengine.TETile;

import java.awt.*;

public class Avatar {
    private static final Color COLOR = Color.RED;
    private static final TETile TILE =  new TETile('a', COLOR, Color.black, "Avatar", "core/images/avatar.jpg");
    private TETile[][] projWorld;
    private int xPos;
    private int yPos;
    private int ghostsBusted = 0;


    public Avatar(TETile[][] projWorld, int startX, int startY) {
        this.xPos = startX;
        this.yPos = startY;
        this.projWorld = projWorld;
        this.projWorld[startX][startY] = TILE;
    }
    // Does not check if canMove.
    // Have to change if avator is more than 1 pixel
    public void drawAvatar(int x, int y) {
        projWorld[x][y] = TILE;
    }

    private boolean canAvatarMove(int deltaX, int deltaY) {
        if (projWorld[xPos + deltaX][yPos + deltaY] == World.FLOORREP) {
            return true;
        } else if (projWorld[xPos + deltaX][yPos + deltaY] == World.GHOSTTILE) {
            ghostsBusted++;
            return true;
        }
        return false;
    }
    public int returnGhostBusted() {
        return ghostsBusted;
    }
    public void avatarMove(int deltaX, int deltaY) {
        if (canAvatarMove(deltaX, deltaY)) {
            drawAvatar(xPos + deltaX, yPos + deltaY);
            projWorld[xPos][yPos] = World.FLOORREP;
            xPos += deltaX;
            yPos += deltaY;
        }
    }
}
