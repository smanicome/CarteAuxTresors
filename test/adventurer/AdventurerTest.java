package adventurer;

import map.Mountain;
import map.Orientation;
import map.Position;
import map.Treasure;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class AdventurerTest {
    Adventurer bob = new Adventurer("Bob", new Position(0, 0), Orientation.NORTH, Collections.emptyIterator());

    @Test
    void setPosition() {
        assertThrows(NullPointerException.class, () -> bob.setPosition(null));
    }

    @Test
    void setOrientation() {
        assertThrows(NullPointerException.class, () -> bob.setOrientation(null));
    }

    @Test
    void setLoots() {
        assertThrows(IllegalArgumentException.class, () -> bob.setLoots(-7));
    }

    @Test
    void moveIntoMountain() {
        var mountain = new Mountain(new Position(1, 2));
        var position = new Position(0, 0);
        bob.setPosition(position);
        bob.moveToTile(mountain);
        assertEquals(bob.getPosition(), position);
    }

    @Test
    void moveIntoTreasure() {
        var treasure = new Treasure(new Position(1, 2), 2);
        var position = new Position(0, 0);
        bob.setPosition(position);
        bob.moveToTile(treasure);
        assertEquals(bob.getPosition(), treasure.getPosition());
        assertEquals(bob.getLoots(), 1);
        assertEquals(treasure.getRemainingLoot(), 1);
    }
}