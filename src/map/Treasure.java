package map;

public class Treasure implements Tile {
    private final int x;
    private final int y;
    private int remainingLoot;

    public Treasure(int x, int y, int remainingLoot) {
        this.x = x;
        this.y = y;
        this.remainingLoot = remainingLoot;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getRemainingLoot() {
        return remainingLoot;
    }

    public void decrementRemainingLoot() {
        if(remainingLoot == 0) throw new IllegalStateException();
        setRemainingLoot(remainingLoot - 1);
    }
    public void setRemainingLoot(int remainingLoot) {
        if(remainingLoot < 0) throw new IllegalArgumentException();
        this.remainingLoot = remainingLoot;
    }

    @Override
    public String toString() {
        return "T - " + x + " - " + y + " - " + remainingLoot;
    }
}
