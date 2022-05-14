package map;

import java.util.List;

public class TreasureMap {
    private final int width;
    private final int height;
    private final List<Tile> tiles;

    public TreasureMap(int width, int height, List<Tile> tiles) {
        this.width = width;
        this.height = height;
        this.tiles = List.copyOf(tiles);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
