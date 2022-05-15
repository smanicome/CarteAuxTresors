package game;

import exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameParserTest {

    @Test
    void noInput() {
        assertThrows(NullPointerException.class, () -> GameParser.load(null));
    }

    @Test
    void inputWithInvalidType() {
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/invalid_type.txt"));
    }

    @Test
    void inputWithInvalidMap() {
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/map/no_map.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/map/w_neg.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/map/w_nan.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/map/h_neg.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/map/h_nan.txt"));
    }

    @Test
    void inputWithInvalidMountain() {
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/mountain/w_neg.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/mountain/w_nan.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/mountain/h_neg.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/mountain/h_nan.txt"));
    }

    @Test
    void inputWithInvalidTreasure() {
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/treasure/w_neg.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/treasure/w_nan.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/treasure/h_neg.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/treasure/h_nan.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/treasure/t_neg.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/treasure/t_nan.txt"));
    }

    @Test
    void inputWithInvalidAdventurer() {
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/adventurer/w_neg.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/adventurer/w_nan.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/adventurer/h_neg.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/adventurer/h_nan.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/adventurer/action_unknown.txt"));
        assertThrows(InvalidDataException.class, () -> GameParser.load("test/game/adventurer/orientation_unknown.txt"));
    }
}