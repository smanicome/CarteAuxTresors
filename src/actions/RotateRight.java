package actions;

import adventurer.Adventurer;
import map.Orientation;
import map.TreasureMap;

public class RotateRight implements Action {
    @Override
    public void process(Adventurer adventurer, TreasureMap treasureMap) {
        switch (adventurer.getOrientation()) {
            case NORTH -> adventurer.setOrientation(Orientation.EAST);
            case EAST -> adventurer.setOrientation(Orientation.SOUTH);
            case WEST -> adventurer.setOrientation(Orientation.NORTH);
            case SOUTH -> adventurer.setOrientation(Orientation.WEST);
        }
    }
}
