package adventurer;

import map.Orientation;

import java.util.Objects;

public class AdventurerBuilder {
    private String name;
    private Integer x;
    private Integer y;
    private Orientation orientation;

    public AdventurerBuilder setName(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    public AdventurerBuilder setX(Integer x) {
        if(x < 0) throw new IllegalArgumentException();
        this.x = x;
        return this;
    }

    public AdventurerBuilder setY(Integer y) {
        if(y < 0) throw new IllegalArgumentException();
        this.y = y;
        return this;
    }

    public AdventurerBuilder setOrientation(Orientation orientation) {
        this.orientation = Objects.requireNonNull(orientation);
        return this;
    }

    public Adventurer build() {
        if(name == null || x == null || y == null || orientation == null) throw new IllegalStateException();
        return new Adventurer(name, x, y, orientation);
    }
}
