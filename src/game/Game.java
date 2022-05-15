package game;

import adventurer.Adventurer;
import map.TreasureMap;

import java.util.List;
import java.util.Objects;

public class Game {
    private final TreasureMap map;
    private final List<Adventurer> adventurers;

    public Game(TreasureMap map, List<Adventurer> adventurers) {
        this.map = Objects.requireNonNull(map);
        this.adventurers = Objects.requireNonNull(adventurers);
    }

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
