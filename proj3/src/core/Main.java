package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;

import java.awt.*;


public class Main {
    public static final int CANVASWIDTH = 1200;
    public static final int CANVASHEIGHT = 800;
    public static final int TITLEFONT = 60;
    public static final int REGULARFONT = 30;
    public static final double CENTER = 0.5;
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
        StdDraw.setCanvasSize(CANVASWIDTH, CANVASHEIGHT);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font font = new Font("Poppins", Font.BOLD, TITLEFONT);
        StdDraw.setFont(font);
        StdDraw.text(CENTER, CENTER + 0.3, "CS 61B Ghost Busters");
        font = new Font("Poppins", Font.BOLD, REGULARFONT);
        StdDraw.setFont(font);
        StdDraw.text(CENTER, CENTER - 0.2, "New Game (N)");
        StdDraw.text(CENTER, CENTER - 0.3, "Load Game (L)");
        StdDraw.text(CENTER, CENTER - 0.4, "Quit (Q)");
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
