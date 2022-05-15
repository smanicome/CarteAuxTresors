package map;

public class Plains implements Tile {
    private final int x;
    private final int y;

    public Plains(int x, int y) {
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
