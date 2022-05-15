package map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreasureTest {
    Treasure treasure = new Treasure(new Position(0, 0), 0);

    @Test
    void decrementRemainingLoot() {
        treasure.setRemainingLoot(0);
        assertThrows(IllegalStateException.class, () -> treasure.decrementRemainingLoot());
    }

    @Test
    void setRemainingLoot() {
        assertThrows(IllegalArgumentException.class, () -> treasure.setRemainingLoot(-5));
    }
}