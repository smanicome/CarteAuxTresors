package actions;

import adventurer.Adventurer;
import map.Orientation;
import map.TreasureMap;

import java.util.List;

public class RotateRight implements Action {
    @Override
    public void process(Adventurer adventurer, TreasureMap treasureMap, List<Adventurer> adventurers) {
        switch (adventurer.getOrientation()) {
            case NORTH -> adventurer.setOrientation(Orientation.EAST);
            case EAST -> adventurer.setOrientation(Orientation.SOUTH);
            case WEST -> adventurer.setOrientation(Orientation.NORTH);
            case SOUTH -> adventurer.setOrientation(Orientation.WEST);
        }
    }
}
