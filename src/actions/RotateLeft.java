package actions;

import adventurer.Adventurer;
import map.Orientation;
import map.TreasureMap;

public class RotateLeft implements Action {
    @Override
    public void process(Adventurer adventurer, TreasureMap treasureMap) {
        switch (adventurer.getOrientation()) {
            case NORTH -> adventurer.setOrientation(Orientation.WEST);
            case EAST -> adventurer.setOrientation(Orientation.NORTH);
            case WEST -> adventurer.setOrientation(Orientation.SOUTH);
            case SOUTH -> adventurer.setOrientation(Orientation.EAST);
        }
    }
}
