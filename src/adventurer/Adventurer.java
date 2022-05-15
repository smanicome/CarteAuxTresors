package adventurer;

import actions.Action;
import map.*;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Adventurer {

    public Adventurer(String name, Position position, Orientation orientation, Iterator<Action> actions) {
        this.name = Objects.requireNonNull(name);
        this.position = Objects.requireNonNull(position);
        this.orientation = Objects.requireNonNull(orientation);
        this.actions = Objects.requireNonNull(actions);
    }


    private final String name;

    private Position position;

    private Orientation orientation;

    private final Iterator<Action> actions;

    private int loots;


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = Objects.requireNonNull(position);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = Objects.requireNonNull(orientation);
    }

    public void incrementLoot() {
        setLoots(loots + 1);
    }
    public void setLoots(int loots) {
        if(loots < 0) throw new IllegalArgumentException();
        this.loots = loots;
    }

    public int getLoots() {
        return loots;
    }

    public void moveToTile(Tile tile) {
        Objects.requireNonNull(tile);
        switch (tile) {
            case Plains plains -> setPosition(plains.getPosition());
            case Treasure treasure -> {
                setPosition(treasure.getPosition());

                if(treasure.getRemainingLoot() == 0) return;
                treasure.decrementRemainingLoot();
                incrementLoot();
            }
            default -> {} // ignore
        }
    }

    public boolean hasActionsLeft() {
        return actions.hasNext();
    }

    public void act(TreasureMap map, List<Adventurer> adventurers) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(adventurers);

        if(actions.hasNext())
            actions.next().process(this, map, adventurers);
    }

    @Override
    public String toString() {
        return "A - " + name + " - " + position + " - " + orientation + " - " + loots;
    }
}
