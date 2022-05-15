package map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreasureMapBuilderTest {
    TreasureMapBuilder treasureMapBuilder = new TreasureMapBuilder();

    @Test
    void setWidth() {
        assertThrows(IllegalArgumentException.class, () -> treasureMapBuilder.setWidth(-1));
    }

    @Test
    void setHeight() {
        assertThrows(IllegalArgumentException.class, () -> treasureMapBuilder.setHeight(-1));
    }

    @Test
    void addTile() {
        assertThrows(NullPointerException.class, () -> treasureMapBuilder.addTile(null));
    }

    @Test
    void build() {
        assertThrows(IllegalStateException.class, () -> new TreasureMapBuilder().build());
    }
}