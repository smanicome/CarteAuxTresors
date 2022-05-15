package game;

import actions.Action;
import adventurer.Adventurer;
import exceptions.InvalidDataException;
import map.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.*;

/**
 * Utility class used to parse the input file into a game
 */
abstract class GameParser {

    /**
     * Checks that nothing is out of the map's bounds, if there is throws an exception
     * @param treasureMap Parsed map
     * @param adventurers Parsed adventurers
     * @throws InvalidDataException if either the adventurers or the tiles are out of bounds
     */
    private static void validateData(TreasureMap treasureMap, List<Adventurer> adventurers) throws InvalidDataException {
        Objects.requireNonNull(treasureMap);
        Objects.requireNonNull(adventurers);

        for (Adventurer adventurer : adventurers) {
            try {
                Objects.checkIndex(adventurer.getPosition().getX(), treasureMap.getWidth());
                Objects.checkIndex(adventurer.getPosition().getY(), treasureMap.getHeight());
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidDataException("Adventurer out of map\n" + adventurer + "\n" + treasureMap);
            }
        }

        for(Tile tile: treasureMap.getTiles()) {
            try {
                Objects.checkIndex(tile.getPosition().getX(), treasureMap.getWidth());
                Objects.checkIndex(tile.getPosition().getY(), treasureMap.getHeight());
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidDataException("Tile out of map\n" + tile + "\n" + treasureMap);
            }
        }
    }

    /**
     * @param parameters Array of string expected to hold all the information defining a Treasure
     * @return The parsed treasure
     * @throws InvalidDataException if the parameters could not be used to parse a treasure
     */
    private static Treasure parseTreasure(String[] parameters) throws InvalidDataException {
        Objects.requireNonNull(parameters);

        if(parameters.length != 4) throw new InvalidDataException("Invalid data for treasure: " + String.join(" - ", parameters));
        try {
            var x = Integer.parseInt(parameters[1]);
            var y = Integer.parseInt(parameters[2]);
            var qty = Integer.parseInt(parameters[3]);
            if(x < 0 || y < 0 || qty < 0) throw new InvalidDataException("Treasure's data invalid: position and treasure quantity must not be less than zero");
            return new Treasure(new Position(x, y), qty);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for treasure's position: " + String.join(" - ", parameters));
        }
    }

    /**
     * @param parameters Array of string expected to hold all the information defining a Mountain
     * @return The parsed mountain
     * @throws InvalidDataException if the parameters could not be used to parse a mountain
     */
    private static Mountain parseMountain(String[] parameters) throws InvalidDataException {
        Objects.requireNonNull(parameters);

        if(parameters.length != 3) throw new InvalidDataException("Invalid data for mountain: " + String.join(" - ", parameters));
        try {
            var x = Integer.parseInt(parameters[1]);
            var y = Integer.parseInt(parameters[2]);
            if(x < 0 || y < 0) throw new InvalidDataException("Mountain's position invalid: x and y must not be less than zero");
            return new Mountain(new Position(x, y));
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for mountain's position: " + String.join(" - ", parameters));
        }
    }

    /**
     * @param treasureMapBuilder Using a builder in order to add tiles later
     * @param parameters Array of string expected to hold the width and height of the map
     * @throws InvalidDataException if the parameters could not be used to parse a map
     */
    private static void parseTreasureMap(TreasureMapBuilder treasureMapBuilder, String[] parameters) throws InvalidDataException {
        Objects.requireNonNull(treasureMapBuilder);
        Objects.requireNonNull(parameters);

        if(parameters.length != 3) throw new InvalidDataException("Invalid data for map: " + String.join(" - ", parameters));
        try {
            var width = Integer.parseInt(parameters[1]);
            var height = Integer.parseInt(parameters[2]);
            if(width < 0 || height < 0) throw new InvalidDataException("Map width and height must be greater than or equal to zero");

            treasureMapBuilder
                    .setWidth(width)
                    .setHeight(height);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for map's size: " + String.join(" - ", parameters));
        }
    }

    /**
     * @param parameters Array of string expected to hold all the information defining an Adventurer
     * @return The parsed adventurer
     * @throws InvalidDataException if the parameters could not be used to parse an adventurer
     */
    private static Adventurer parseAdventurer(String[] parameters) throws InvalidDataException {
        Objects.requireNonNull(parameters);

        if(parameters.length != 6) throw new InvalidDataException("Invalid data for adventurer: " + String.join(" - ", parameters));
        try {
            var name = parameters[1];
            var x = Integer.parseInt(parameters[2]);
            var y = Integer.parseInt(parameters[3]);

            if(x < 0 || y < 0) throw new InvalidDataException("Adventurer's position invalid: x and y must not be less than zero");

            var orientation = Orientation.fromLetter(parameters[4]).orElseThrow(() -> new InvalidDataException("Unrecognized token for orientation: " + parameters[4]));
            var actions = parseActions(parameters[5]);

            return new Adventurer(name, new Position(x, y), orientation, actions.iterator());
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for adventurer's position: " + String.join(" - ", parameters));
        }
    }

    /**
     * @param tokens A sequence of letters to be split and then parsed into several actions
     * @return A list of the parsed actions
     * @throws InvalidDataException If any token could not be parsed into an action
     */
    private static List<Action> parseActions(String tokens) throws InvalidDataException {
        Objects.requireNonNull(tokens);

        try {
            return Arrays.stream(tokens.split(""))
                .map(Action::fromToken)
                .map(Optional::orElseThrow)
                .toList();
        } catch (NoSuchElementException e) {
            throw new InvalidDataException("Unrecognized token for adventurer's action: " + tokens);
        }
    }

    /**
     * @param inputFilePath Input file used to parse the game
     * @return The parsed Game
     * @throws InvalidDataException When even one line could not be successfully parsed, except comments
     */
    public static Game load(String inputFilePath) throws IOException, InvalidDataException {
        Objects.requireNonNull(inputFilePath);

        Scanner scanner = new Scanner(Path.of(inputFilePath));
        TreasureMapBuilder treasureMapBuilder = new TreasureMapBuilder();
        ArrayList<Adventurer> adventurers = new ArrayList<>();

        while(scanner.hasNextLine()) {
            var line = scanner.nextLine();
            if(line.startsWith("#")) continue;

            var parameters = line.split(" - ");
            switch (parameters[0]) {
                case "C" -> parseTreasureMap(treasureMapBuilder, parameters);
                case "M" -> treasureMapBuilder.addTile(parseMountain(parameters));
                case "T" -> treasureMapBuilder.addTile(parseTreasure(parameters));
                case "A" -> adventurers.add(parseAdventurer(parameters));
                default -> throw new InvalidDataException("Unrecognized token for input data type");
            }
        }

        try {
            var map = treasureMapBuilder.build();
            validateData(map, adventurers);

            return new Game(map, adventurers);
        } catch (IllegalStateException e) {
            throw new InvalidDataException("Map is missing");
        }
    }

    /**
     * Saves the game into a file, its format is almost exactly identical to the output file, although it cannot directly be used as an input file
     * @param game Game to save
     * @param printStream PrintStream used to write into the selected file
     */
    private static void save(Game game, PrintStream printStream) {
        Objects.requireNonNull(game);
        Objects.requireNonNull(printStream);

        printStream.println(game.getMap());
        game.getMap().getTiles().forEach(printStream::println);
        game.getAdventurers().forEach(printStream::println);
    }

    /**
     * Save the game using a preferred output file
     * @param game Game to save
     * @param outputFilePath Preferred file to save in
     */
    public static void save(Game game, String outputFilePath) throws IOException {
        Objects.requireNonNull(game);
        Objects.requireNonNull(outputFilePath);

        var file = new File(outputFilePath);
        var pw = new PrintStream(file);

        save(game, pw);
    }

    /**
     * Prints the game's state on the standard output
     * @param game Game to save
     */
    public static void save(Game game) throws IOException {
        Objects.requireNonNull(game);

        save(game, System.out);
    }
}
