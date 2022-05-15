package actions;

import adventurer.Adventurer;
import map.TreasureMap;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Specifies how actions should be built so that every information can be accessible
 */
public interface Action {
    /**
     * @param token Letter used in the input file to identify the action
     * @return The action wrapped in an optional if the token matches an action, or an empty optional otherwise
     */
    static Optional<Action> fromToken(String token) {
        Objects.requireNonNull(token);
        return switch(token) {
            case "A" -> Optional.of(new Forward());
            case "G" -> Optional.of(new RotateLeft());
            case "D" -> Optional.of(new RotateRight());
            default -> Optional.empty();
        };
    }

    /**
     * @param adventurer The adventurer that makes the action
     * @param treasureMap The map on which the action takes place
     * @param adventurers All the users on the map
     */
    void process(Adventurer adventurer, TreasureMap treasureMap, List<Adventurer> adventurers);
}
