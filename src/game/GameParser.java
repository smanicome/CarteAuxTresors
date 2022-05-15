package game;

import actions.Action;
import adventurer.Adventurer;
import adventurer.AdventurerBuilder;
import exceptions.InvalidDataException;
import map.*;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

abstract class GameParser {

    private static void validateData(TreasureMap treasureMap, Adventurer adventurer) throws InvalidDataException {
        try {
            Objects.checkIndex(adventurer.getX(), treasureMap.getWidth());
            Objects.checkIndex(adventurer.getY(), treasureMap.getHeight());
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidDataException("Adventurer out of map\n" + adventurer + "\n" + treasureMap);
        }

        for(Tile tile: treasureMap.getTiles()) {
            try {
                Objects.checkIndex(tile.getX(), treasureMap.getWidth());
                Objects.checkIndex(tile.getY(), treasureMap.getHeight());
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidDataException("Tile out of map\n" + tile + "\n" + treasureMap);
            }
        }
    }

    private static void parseTreasure(TreasureMapBuilder treasureMapBuilder, String[] parameters) throws InvalidDataException {
        if(parameters.length != 4) throw new InvalidDataException("Invalid data for treasure: " + String.join(" - ", parameters));
        try {
            var x = Integer.parseInt(parameters[1]);
            var y = Integer.parseInt(parameters[2]);
            var qty = Integer.parseInt(parameters[3]);
            treasureMapBuilder.addTile(new Treasure(x, y, qty));
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for treasure's position: " + String.join(" - ", parameters));
        }
    }
    private static void parseMountain(TreasureMapBuilder treasureMapBuilder, String[] parameters) throws InvalidDataException {
        if(parameters.length != 3) throw new InvalidDataException("Invalid data for mountain: " + String.join(" - ", parameters));
        try {
            var x = Integer.parseInt(parameters[1]);
            var y = Integer.parseInt(parameters[2]);
            treasureMapBuilder.addTile(new Mountain(x, y));
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for mountain's position: " + String.join(" - ", parameters));
        }
    }

    private static void parseTreasureMap(TreasureMapBuilder treasureMapBuilder, String[] parameters) throws InvalidDataException {
        if(parameters.length != 3) throw new InvalidDataException("Invalid data for map: " + String.join(" - ", parameters));
        try {
            var width = Integer.parseInt(parameters[1]);
            var height = Integer.parseInt(parameters[2]);
            treasureMapBuilder
                    .setWidth(width)
                    .setHeight(height);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for map's size: " + String.join(" - ", parameters));
        }
    }

    private static void parseAdventurer(AdventurerBuilder adventurerBuilder, String[] parameters) throws InvalidDataException {
        if(parameters.length != 6) throw new InvalidDataException("Invalid data for adventurer: " + String.join(" - ", parameters));
        try {
            var name = parameters[1];
            var x = Integer.parseInt(parameters[2]);
            var y = Integer.parseInt(parameters[3]);
            var orientation = Orientation.fromLetter(parameters[4]).orElseThrow(() -> new InvalidDataException("Unrecognized token for orientation: " + parameters[4]));

            adventurerBuilder
                .setName(name)
                .setX(x)
                .setY(y)
                .setOrientation(orientation);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for adventurer's position: " + String.join(" - ", parameters));
        }
    }

    private static void parseActions(ArrayList<Action> actions, String tokens) throws InvalidDataException {
        try {
            var parsedActions = Arrays.stream(tokens.split(""))
                .map(Action::fromToken)
                .map(Optional::orElseThrow)
                .toList();
            actions.addAll(parsedActions);
        } catch (NoSuchElementException e) {
            throw new InvalidDataException("Unrecognized token for adventurer's action: " + tokens);
        }
    }

    public static Game load(String inputFilePath) throws IOException, InvalidDataException {
        Scanner scanner = new Scanner(Path.of(inputFilePath));
        TreasureMapBuilder treasureMapBuilder = new TreasureMapBuilder();
        AdventurerBuilder adventurerBuilder = new AdventurerBuilder();
        ArrayList<Action> actions = new ArrayList<>();

        while(scanner.hasNextLine()) {
            var line = scanner.nextLine();
            if(line.startsWith("#")) continue;

            var parameters = line.split(" - ");
            switch (parameters[0]) {
                case "C" -> parseTreasureMap(treasureMapBuilder, parameters);
                case "M" -> parseMountain(treasureMapBuilder, parameters);
                case "T" -> parseTreasure(treasureMapBuilder, parameters);
                case "A" -> {
                    parseAdventurer(adventurerBuilder, parameters);
                    parseActions(actions, parameters[5]);
                }
                default -> throw new InvalidDataException("Unrecognized token for input data type");
            }
        }

        var map = treasureMapBuilder.build();
        var adventurer = adventurerBuilder.build();
        validateData(map, adventurer);

        return new Game(map, adventurer, actions);
    }

    private static void save(Game game, PrintStream printStream) {
        printStream.println(game.getMap());
        game.getMap().getTiles().forEach(printStream::println);
        printStream.print(game.getAdventurer());
    }

    public static void save(Game game, String outputFilePath) throws IOException {
        var file = new File(outputFilePath);
        var pw = new PrintStream(file);

        save(game, pw);
    }
    public static void save(Game game) throws IOException {
        save(game, System.out);
    }
}
