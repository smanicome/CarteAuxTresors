package map;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Used to build a map, all the data must be specified to successfully build a map
 */
public class TreasureMapBuilder {
    private Integer width;
    private Integer height;
    private final ArrayList<Tile> tiles = new ArrayList<>();

    public TreasureMapBuilder setWidth(int width) {
        if(width < 0) throw new IllegalArgumentException();
        this.width = width;
        return this;
    }

    public TreasureMapBuilder setHeight(int height) {
        if(height < 0) throw new IllegalArgumentException();
        this.height = height;
        return this;
    }

    public TreasureMapBuilder addTile(Tile tile) {
        this.tiles.add(Objects.requireNonNull(tile));
        return this;
    }

    public TreasureMap build() {
        if(width == null || height == null) throw new IllegalStateException();
        return new TreasureMap(width, height, tiles);
    }
}
