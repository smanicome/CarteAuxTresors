package actions;

import adventurer.Adventurer;
import map.TreasureMap;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface Action {
    static Optional<Action> fromToken(String token) {
        Objects.requireNonNull(token);
        return switch(token) {
            case "A" -> Optional.of(new Forward());
            case "G" -> Optional.of(new RotateLeft());
            case "D" -> Optional.of(new RotateRight());
            default -> Optional.empty();
        };
    }

    void process(Adventurer adventurer, TreasureMap treasureMap, List<Adventurer> adventurers);
}
