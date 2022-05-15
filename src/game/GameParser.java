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

abstract class GameParser {

    private static void validateData(TreasureMap treasureMap, List<Adventurer> adventurers) throws InvalidDataException {
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

    private static Treasure parseTreasure(String[] parameters) throws InvalidDataException {
        if(parameters.length != 4) throw new InvalidDataException("Invalid data for treasure: " + String.join(" - ", parameters));
        try {
            var x = Integer.parseInt(parameters[1]);
            var y = Integer.parseInt(parameters[2]);
            var qty = Integer.parseInt(parameters[3]);
            return new Treasure(new Position(x, y), qty);
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for treasure's position: " + String.join(" - ", parameters));
        }
    }

    private static Mountain parseMountain(String[] parameters) throws InvalidDataException {
        if(parameters.length != 3) throw new InvalidDataException("Invalid data for mountain: " + String.join(" - ", parameters));
        try {
            var x = Integer.parseInt(parameters[1]);
            var y = Integer.parseInt(parameters[2]);
            return new Mountain(new Position(x, y));
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

    private static Adventurer parseAdventurer(String[] parameters) throws InvalidDataException {
        if(parameters.length != 6) throw new InvalidDataException("Invalid data for adventurer: " + String.join(" - ", parameters));
        try {
            var name = parameters[1];
            var x = Integer.parseInt(parameters[2]);
            var y = Integer.parseInt(parameters[3]);
            var orientation = Orientation.fromLetter(parameters[4]).orElseThrow(() -> new InvalidDataException("Unrecognized token for orientation: " + parameters[4]));
            var actions = parseActions(parameters[5]);

            return new Adventurer(name, new Position(x, y), orientation, actions.iterator());
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Unrecognized token for adventurer's position: " + String.join(" - ", parameters));
        }
    }

    private static List<Action> parseActions(String tokens) throws InvalidDataException {
        try {
            return Arrays.stream(tokens.split(""))
                .map(Action::fromToken)
                .map(Optional::orElseThrow)
                .toList();
        } catch (NoSuchElementException e) {
            throw new InvalidDataException("Unrecognized token for adventurer's action: " + tokens);
        }
    }

    public static Game load(String inputFilePath) throws IOException, InvalidDataException {
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

        var map = treasureMapBuilder.build();
        validateData(map, adventurers);

        return new Game(map, adventurers);
    }

    private static void save(Game game, PrintStream printStream) {
        printStream.println(game.getMap());
        game.getMap().getTiles().forEach(printStream::println);
        game.getAdventurers().forEach(printStream::println);
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
