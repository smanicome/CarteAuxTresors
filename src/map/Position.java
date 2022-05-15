package map;

public class Position {
    private final int x;
    private final int y;
    
    public Position(int x, int y) {
        if(x < 0 || y < 0) throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
    }

    public Position shiftTo(Orientation orientation) {
        return switch (orientation) {
            case NORTH -> new Position(x, y - 1);
            case EAST -> new Position(x + 1, y);
            case WEST -> new Position(x - 1, y);
            case SOUTH -> new Position(x, y + 1);
        };
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position other)) return false;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return x + " - " + y;
    }
}
