package game;

import actions.Action;
import adventurer.AdventurerBuilder;
import exceptions.InvalidDataException;
import map.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

abstract class GameParser {

    private static void validateData() throws InvalidDataException {

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
            var optionalActions = Arrays.stream(tokens.split(""))
                .map(Action::fromToken)
                .map(Optional::orElseThrow)
                .toList();
            actions.addAll(optionalActions);
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

        return new Game(treasureMapBuilder.build(), adventurerBuilder.build(), actions);
    }
}
