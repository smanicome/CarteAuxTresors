package map;

public class Mountain extends Tile {

    public Mountain(Position position) {
        super(position);
    }

    @Override
    public String toString() {
        return "M - " + getPosition();
    }
}
