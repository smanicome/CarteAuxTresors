package game;

import exceptions.InvalidDataException;

import java.io.IOException;

public class TreasureHunt {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: <input file> <output file (optional)>");
        }

        try {
            var game = GameParser.load(args[0]);
            game.run();
            if(args.length >= 2) {
                GameParser.save(game, args[1]);
            } else {
                GameParser.save(game);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }
}
