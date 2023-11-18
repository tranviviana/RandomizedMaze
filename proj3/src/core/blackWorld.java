//package core;
//import tileengine.TERenderer;
//import tileengine.TETile;
//import tileengine.Tileset;
//import edu.princeton.cs.algs4.StdDraw;
//import java.awt.*;
//import java.util.*;
//import java.util.List;
//import static edu.princeton.cs.algs4.StdDraw.hasNextKeyTyped;
//import static edu.princeton.cs.algs4.StdDraw.nextKeyTyped;
//
//public class blackWorld {
//
//    private static final int WIDTH = World.WIDTH;
//    private static final int HEIGHT = World.HEIGHT;
//
//
//    TETile[][] secondWorld;
//    TETile[][] darkWorld;
//
//
//    public blackWorld() {
//        secondWorld = new TETile[WIDTH][HEIGHT];
//        darkWorld = new TETile[WIDTH][HEIGHT];
//        fillWorld();
//        int startX = World.character.xPos;
//        int startY = World.character.yPos;
//        copySpaceToBlackWorld(startX, startY);
//        blackWorldMaker();
//
//    }
//
//    public void copySpaceToBlackWorld(int xPos, int yPos) {
//        // Starting from left bottom corner of 5x5
//        for (int x = xPos - 2; x < xPos + 2; x++) {
//            for (int y = yPos - 2; y < yPos + 2; y++) {
//                secondWorld[x][y] = World.projWorld[x][y];
//            }
//        }
//    }
//
//    public void resetSecondWorld() {
//        secondWorld = darkWorld;
//    }
//
//    public TETile[][] worldState() {
//        return secondWorld;
//    }
//
//    public void blackWorldMaker() {
//        while (World.isGameOver()) {
//            int xPos = World.character.xPos;
//            int yPos = World.character.yPos;
//            copySpaceToBlackWorld(xPos, yPos);
//
//        }
//    }
//    public void fillWorld() {
//        for (int x = 0; x < WIDTH; x++) {
//            for (int y = 0; y < HEIGHT; y++) {
//                secondWorld[x][y] = Tileset.NOTHING;
//            }
//        }
//    }
//}
