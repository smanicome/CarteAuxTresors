package actions;

import adventurer.Adventurer;
import map.Orientation;
import map.TreasureMap;

import java.util.List;
import java.util.Objects;

public class RotateLeft implements Action {
    @Override
    public void process(Adventurer adventurer, TreasureMap treasureMap, List<Adventurer> adventurers) {
        Objects.requireNonNull(adventurer);
        Objects.requireNonNull(treasureMap);
        Objects.requireNonNull(adventurers);

        switch (adventurer.getOrientation()) {
            case NORTH -> adventurer.setOrientation(Orientation.WEST);
            case EAST -> adventurer.setOrientation(Orientation.NORTH);
            case WEST -> adventurer.setOrientation(Orientation.SOUTH);
            case SOUTH -> adventurer.setOrientation(Orientation.EAST);
        }
    }
}
