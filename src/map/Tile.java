package map;

import java.util.Objects;

public abstract class Tile {
    private final Position position;

    public Tile(Position position) {
        this.position = Objects.requireNonNull(position);
    }

    public Position getPosition() {
        return position;
    }
}
