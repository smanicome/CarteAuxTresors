package map;

public class Mountain implements Tile {
    private final int x;
    private final int y;

    public Mountain(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
