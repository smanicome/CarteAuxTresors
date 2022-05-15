package adventurer;

import map.*;

import java.util.Objects;

public class Adventurer {

    public Adventurer(String name, int x, int y, Orientation orientation) {
        if(x < 0 || y < 0) throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
        this.name = Objects.requireNonNull(name);
        this.orientation = Objects.requireNonNull(orientation);
    }

    private int x;
    private int y;
    private final String name;
    private Orientation orientation;

    private int loots;

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

    public int getLoots() {
        return loots;
    }

    public void incrementLoot() {
        setLoots(loots + 1);
    }
    public void setLoots(int loots) {
        if(loots < 0) throw new IllegalArgumentException();
        this.loots = loots;
    }

    @Override
    public String toString() {
        return "A - " + name + " - " + x + " - " + y + " - " + orientation + " - " + loots;
    }

    public void moveToTile(Tile tile) {
        switch (tile) {
            case Mountain ignored -> {}
            case Plains plains -> {
                setX(plains.getX());
                setY(plains.getY());
            }
            case Treasure treasure -> {
                setX(treasure.getX());
                setY(treasure.getY());

                if(treasure.getRemainingLoot() == 0) return;
                treasure.decrementRemainingLoot();
                incrementLoot();
            }
            default -> throw new IllegalStateException("Unexpected value: " + tile);
        }
    }
}
