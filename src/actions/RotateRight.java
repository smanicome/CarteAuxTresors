package actions;

import adventurer.Adventurer;
import map.Orientation;
import map.TreasureMap;

import java.util.List;
import java.util.Objects;

/**
 * Rotates the adventurer clockwise
 */
public class RotateRight implements Action {
    @Override
    public void process(Adventurer adventurer, TreasureMap treasureMap, List<Adventurer> adventurers) {
        Objects.requireNonNull(adventurer);
        Objects.requireNonNull(treasureMap);
        Objects.requireNonNull(adventurers);

        switch (adventurer.getOrientation()) {
            case NORTH -> adventurer.setOrientation(Orientation.EAST);
            case EAST -> adventurer.setOrientation(Orientation.SOUTH);
            case WEST -> adventurer.setOrientation(Orientation.NORTH);
            case SOUTH -> adventurer.setOrientation(Orientation.WEST);
        }
    }
}
