package actions;

import adventurer.Adventurer;
import map.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Forward implements Action {

    private Optional<Tile> getTileForCoordinates(int x, int y, List<Tile> tiles) {
        return Objects.requireNonNull(tiles).stream()
                .filter(tile -> tile.getX() == x && tile.getY() == y)
                .findFirst()
                .or(() -> Optional.of(new Plains(x, y)));
    }

    @Override
    public void process(Adventurer adventurer, TreasureMap treasureMap) {
        Optional<Tile> optionalTile = switch (adventurer.getOrientation()) {
            case NORTH -> {
                if (adventurer.getY() == 0) yield Optional.empty();
                yield getTileForCoordinates(adventurer.getX(), adventurer.getY() - 1, treasureMap.getTiles());
            }
            case EAST -> {
                if (adventurer.getX() == treasureMap.getWidth() - 1) yield Optional.empty();
                yield getTileForCoordinates(adventurer.getX() + 1, adventurer.getY(), treasureMap.getTiles());
            }
            case WEST -> {
                if (adventurer.getX() == 0) yield Optional.empty();
                yield getTileForCoordinates(adventurer.getX() - 1, adventurer.getY(), treasureMap.getTiles());
            }
            case SOUTH -> {
                if (adventurer.getY() == treasureMap.getHeight() - 1) yield Optional.empty();
                yield getTileForCoordinates(adventurer.getX(), adventurer.getY() + 1, treasureMap.getTiles());
            }
        };

        optionalTile.ifPresent(adventurer::moveToTile);
    }
}
