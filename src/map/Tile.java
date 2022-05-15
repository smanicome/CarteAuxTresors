package map;

import java.util.Objects;

/**
 * Represents a tile of the map. Obviously holds its position
 */
public abstract class Tile {
    private final Position position;

    public Tile(Position position) {
        this.position = Objects.requireNonNull(position);
    }

    public Position getPosition() {
        return position;
    }
}
