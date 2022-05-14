package map;

import map.Tile;
import map.TreasureMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public TreasureMapBuilder addTiles(List<Tile> tiles) {
        this.tiles.addAll(Objects.requireNonNull(tiles));
        return this;
    }

    public TreasureMapBuilder addTiles(Tile ...tiles) {
        return this.addTiles(Arrays.asList(Objects.requireNonNull(tiles)));
    }

    public TreasureMap build() {
        if(width == null || height == null) throw new IllegalStateException();
        return new TreasureMap(width, height, tiles);
    }
}
