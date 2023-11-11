package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    //doesnt process and doesnt account for pressing random letters in between
    public static void processingSeedStrokes() throws Exception {
        StringBuilder seedType = new StringBuilder();
        while (StdDraw.hasNextKeyTyped()) {
            char c = StdDraw.nextKeyTyped();
            if (c == 's' || c == 'S') {
                break;
            }
            seedType.append(c);
        }
        World testingWorld = new World(Long.parseLong(String.valueOf(seedType)));
        TETile[][] tiles = testingWorld.worldState();
        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
    }

    public static void main(String[] args) throws Exception {
        StdDraw.setCanvasSize(1200, 800);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font font = new Font("Poppins", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.8, "CS 61B Ghost Busters");
        font = new Font("Poppins", Font.BOLD, 35);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.3, "New Game (N)");
        StdDraw.text(0.5, 0.2, "Load Game (L)");
        StdDraw.text(0.5, 0.1, "Quit (Q)");
        while (StdDraw.hasNextKeyTyped()) {
            char c = StdDraw.nextKeyTyped();
            if (c == 'N' || c == 'n') {
                processingSeedStrokes();
                break;
            }
        }
        TERenderer ter = new TERenderer();

    }

}
