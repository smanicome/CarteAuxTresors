package adventurer;

import map.Orientation;

import java.util.Objects;

public class Adventurer {

    public Adventurer(String name, int x, int y, Orientation orientation) {
        if(x < 0 || y < 0) throw new IllegalArgumentException();
        this.name = Objects.requireNonNull(name);
        this.orientation = Objects.requireNonNull(orientation);
    }

    private int x;
    private int y;
    private final String name;
    private Orientation orientation;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if(x < 0) throw new IllegalArgumentException("x position must be greater than 0");
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if(y < 0) throw new IllegalArgumentException("y position must be greater than 0");
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = Objects.requireNonNull(orientation);
    }
}
