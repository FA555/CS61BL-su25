package PlusWorld;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Arrays;
import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    public static void main(String[] args) {
        Board board = new Board();
        board.drawTilingPluses(3);

        TERenderer ter = new TERenderer();
        ter.initialize(board.getWidth(), board.getHeight());
        ter.renderFrame(board.getTiles());
    }

    static class Board {
        private static final int WIDTH = 150;
        private static final int HEIGHT = 80;
        private static final TETile[] TILES = new TETile[]{Tileset.AVATAR, Tileset.WALL, Tileset.FLOOR, Tileset.GRASS, Tileset.WATER, Tileset.FLOWER, Tileset.LOCKED_DOOR, Tileset.UNLOCKED_DOOR, Tileset.SAND, Tileset.MOUNTAIN, Tileset.TREE, Tileset.CELL};

        private static final TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        private final int width = WIDTH;
        private final int height = HEIGHT;

        public Board() {
            fillWith(Tileset.NOTHING);
        }

        public TETile[][] getTiles() {
            return tiles;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void setTile(int x, int y, TETile tile) {
            if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
                // simply ignore it
                return;
            }

            tiles[x][y] = tile;
        }

        public void fillWith(TETile tile) {
            for (int x = 0; x < WIDTH; ++x) {
                Arrays.fill(tiles[x], tile);
            }
        }

        public void drawPlus(int x, int y, int size, TETile tile) {
            for (int dx = -size; dx < size * 2; ++dx) {
                for (int dy = 0; dy < size; ++dy) {
                    setTile(x + dx, y + dy, tile);
                }
            }
            for (int dy = -size; dy < size * 2; ++dy) {
                for (int dx = 0; dx < size; ++dx) {
                    setTile(x + dx, y + dy, tile);
                }
            }
        }

        public void drawManyPluses() {
            Random rand = new Random();
            int plusAmount = rand.nextInt(5000) + 5;
            for (int i = 0; i < plusAmount; ++i) {
                int x = rand.nextInt(WIDTH);
                int y = rand.nextInt(HEIGHT);
                int size = rand.nextInt(4) + 2; // Random size between 2 and 5
                TETile tile = TILES[rand.nextInt(TILES.length)];
                drawPlus(x, y, size, tile);
            }
        }

        public void drawTilingPluses(int size) {
            // center: (0, 0), (-2size, size), (2size, size), (-2size, -size), (2size, -size), ...
            Random rand = new Random();
            for (int x = -WIDTH / size / 2; x < WIDTH / size / 2; ++x) {
                for (int y = 0; y < HEIGHT / size; ++y) {
                    int biasedX = size * (y + 2 * x);
                    int biasedY = size * (2 * y - x);
                    drawPlus(biasedX, biasedY, size, TILES[rand.nextInt(TILES.length)]);
                }
            }
        }
    }
}
