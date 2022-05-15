package actions;

import adventurer.Adventurer;
import map.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForwardTest {
    Adventurer bob = new Adventurer("Bob", new Position(0, 0), Orientation.NORTH, Collections.emptyIterator());
    Adventurer alice = new Adventurer("Alice", new Position(1, 2), Orientation.NORTH, Collections.emptyIterator());
    Forward forward = new Forward();
    TreasureMap map = new TreasureMap(5, 5, List.of(
        new Mountain(new Position(2, 0)),
        new Treasure(new Position(0, 1), 3)
    ));

    @Test
    void nullSafe() {
        assertThrows(NullPointerException.class, () -> forward.process(null, map, List.of(alice)));
        assertThrows(NullPointerException.class, () -> forward.process(bob, null, List.of(alice)));
        assertThrows(NullPointerException.class, () -> forward.process(bob, map, null));
    }

    @Test
    void intoMountain() {
        bob.setOrientation(Orientation.EAST);
        forward.process(bob, map, List.of(alice));
        forward.process(bob, map, List.of(alice));
        forward.process(bob, map, List.of(alice));
        assertEquals(bob.getPosition(), new Position(1, 0));
    }

    @Test
    void throughTreasure() {
        bob.setOrientation(Orientation.SOUTH);
        forward.process(bob, map, List.of(alice));
        forward.process(bob, map, List.of(alice));
        forward.process(bob, map, List.of(alice));
        assertEquals(bob.getPosition(), new Position(0, 3));
        assertEquals(bob.getLoots(), 1);
    }

    @Test
    void intoAdventurer() {
        bob.setOrientation(Orientation.EAST);
        forward.process(bob, map, List.of(alice));
        bob.setOrientation(Orientation.SOUTH);
        forward.process(bob, map, List.of(alice));
        forward.process(bob, map, List.of(alice));
        assertEquals(bob.getPosition(), new Position(1, 1));
    }
}