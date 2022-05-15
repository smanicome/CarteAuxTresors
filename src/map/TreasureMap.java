package map;

import java.util.List;
import java.util.Objects;

/**
 * Represents the map, holds width, height and a list of specific tiles
 */
public class TreasureMap {
    private final int width;
    private final int height;
    private final List<Tile> tiles;

    public TreasureMap(int width, int height, List<Tile> tiles) {
        if(width < 0 || height < 0) throw new IllegalArgumentException();
        this.width = width;
        this.height = height;
        this.tiles = List.copyOf(Objects.requireNonNull(tiles));
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

    @Override
    public String toString() {
        return "C - " + width + " - " + height;
    }
}
