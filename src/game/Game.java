package game;

import actions.Action;
import adventurer.Adventurer;
import map.TreasureMap;

import java.util.List;
import java.util.Objects;

public class Game {
    private final TreasureMap map;
    private final Adventurer adventurer;
    private final List<Action> actions;

    public Game(TreasureMap map, Adventurer adventurer, List<Action> actions) {
        this.map = Objects.requireNonNull(map);
        this.adventurer = Objects.requireNonNull(adventurer);
        this.actions = List.copyOf(Objects.requireNonNull(actions));
    }

    public void run() {
        actions.forEach(action -> action.process(adventurer, map));
    }

    public TreasureMap getMap() {
        return map;
    }

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public List<Action> getActions() {
        return actions;
    }
}
