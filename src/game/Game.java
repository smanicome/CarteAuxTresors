package game;

import adventurer.Adventurer;
import map.TreasureMap;

import java.util.List;
import java.util.Objects;

/**
 * Class used to represent a game, it holds the map and the adventurers
 */
public class Game {
    private final TreasureMap map;
    private final List<Adventurer> adventurers;

    public Game(TreasureMap map, List<Adventurer> adventurers) {
        this.map = Objects.requireNonNull(map);
        this.adventurers = Objects.requireNonNull(adventurers);
    }

    /**
     * Run each adventurers' actions on a turn-base rotation, stops when no action is left to be processed
     */
    public void run() {
        while (adventurers.stream().anyMatch(Adventurer::hasActionsLeft)) {
            adventurers.forEach(adventurer -> adventurer.act(map, adventurers));
        }
    }

    public TreasureMap getMap() {
        return map;
    }

    public List<Adventurer> getAdventurers() {
        return adventurers;
    }
}
