package map;

public class Treasure extends Tile {
    private int remainingLoot;

    public Treasure(Position position, int remainingLoot) {
        super(position);
        if(remainingLoot < 0) throw new IllegalArgumentException();
        this.remainingLoot = remainingLoot;
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
        return "T - " + getPosition() + " - " + remainingLoot;
    }
}
