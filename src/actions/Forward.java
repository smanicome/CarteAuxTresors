package actions;

import adventurer.Adventurer;
import map.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Forward implements Action {

    private Optional<Tile> getTileForCoordinates(Position position, List<Tile> tiles, List<Adventurer> adventurers) {
        if(isBlockedByAdventurer(position, adventurers)) return Optional.empty();
        return Objects.requireNonNull(tiles).stream()
                .filter(tile -> position.equals(tile.getPosition()))
                .findFirst()
                .or(() -> Optional.of(new Plains(position)));
    }

    private boolean isBlockedByAdventurer(Position position, List<Adventurer> adventurers) {
        return adventurers.stream().anyMatch(adventurer -> position.equals(adventurer.getPosition()));
    }

    @Override
    public void process(Adventurer adventurer, TreasureMap treasureMap, List<Adventurer> adventurers) {

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
