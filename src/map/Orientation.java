package map;

import java.util.Optional;

public enum Orientation {
    NORTH, EAST, WEST, SOUTH;

    public static Optional<Orientation> fromLetter(String letter) {
        return switch (letter) {
            case "N" -> Optional.of(NORTH);
            case "E" -> Optional.of(EAST);
            case "W" -> Optional.of(WEST);
            case "S" -> Optional.of(SOUTH);
            default -> Optional.empty();
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "N";
            case EAST -> "E";
            case WEST -> "W";
            case SOUTH -> "S";
        };
    }
}