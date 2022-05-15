package actions;

import adventurer.Adventurer;
import map.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Attempt to move an adventurer forward to its orientation
 */
public class Forward implements Action {

    /**
     * Returns the tile at the given position
     * @param position Position of the tile
     * @param tiles List of specific tiles
     * @param adventurers All the adventurers on the map
     * @return The tile wrapped in an optional, if an adventurer is at the given position return an empty optional instead
     */
    private Optional<Tile> getTileForCoordinates(Position position, List<Tile> tiles, List<Adventurer> adventurers) {
        Objects.requireNonNull(position);
        Objects.requireNonNull(tiles);
        Objects.requireNonNull(adventurers);

        if(isBlockedByAdventurer(position, adventurers)) return Optional.empty();
        return Objects.requireNonNull(tiles).stream()
                .filter(tile -> position.equals(tile.getPosition()))
                .findFirst()
                .or(() -> Optional.of(new Plains(position)));
    }

    /**
     * Checks if an adventurer is at the given position on the map
     * @param position Position where to check
     * @param adventurers All the adventurers on the map
     * @return True if an adventurer is at the given position, false otherwise
     */
    private boolean isBlockedByAdventurer(Position position, List<Adventurer> adventurers) {
        Objects.requireNonNull(position);
        Objects.requireNonNull(adventurers);

        return adventurers.stream().anyMatch(adventurer -> position.equals(adventurer.getPosition()));
    }

    /**
     * Checks the boundaries before attempting to move
     * @param adventurer  The adventurer that makes the action
     * @param treasureMap The map on which the action takes place
     * @param adventurers All the users on the map
     */
    @Override
    public void process(Adventurer adventurer, TreasureMap treasureMap, List<Adventurer> adventurers) {
        Objects.requireNonNull(adventurer);
        Objects.requireNonNull(treasureMap);
        Objects.requireNonNull(adventurers);

        Optional<Position> optionalPosition = switch (adventurer.getOrientation()) {
            case NORTH -> {
                if (adventurer.getPosition().getY() == 0) yield Optional.empty();
                yield Optional.of(adventurer.getPosition().shiftTo(Orientation.NORTH));
            }
            case EAST -> {
                if (adventurer.getPosition().getX() == treasureMap.getWidth() - 1) yield Optional.empty();
                yield Optional.of(adventurer.getPosition().shiftTo(Orientation.EAST));
            }
            case WEST -> {
                if (adventurer.getPosition().getX() == 0) yield Optional.empty();
                yield Optional.of(adventurer.getPosition().shiftTo(Orientation.WEST));
            }
            case SOUTH -> {
                if (adventurer.getPosition().getY() == treasureMap.getHeight() - 1) yield Optional.empty();
                yield Optional.of(adventurer.getPosition().shiftTo(Orientation.SOUTH));
            }
        };

        optionalPosition.ifPresent(position -> {
            var optionalTile = getTileForCoordinates(position, treasureMap.getTiles(), adventurers);
            optionalTile.ifPresent(adventurer::moveToTile);
        });
    }
}
