package actions;

import adventurer.Adventurer;
import map.Orientation;
import map.TreasureMap;

import java.util.List;

public class RotateLeft implements Action {
    @Override
    public void process(Adventurer adventurer, TreasureMap treasureMap, List<Adventurer> adventurers) {
        switch (adventurer.getOrientation()) {
            case NORTH -> adventurer.setOrientation(Orientation.WEST);
            case EAST -> adventurer.setOrientation(Orientation.NORTH);
            case WEST -> adventurer.setOrientation(Orientation.SOUTH);
            case SOUTH -> adventurer.setOrientation(Orientation.EAST);
        }
    }
}
