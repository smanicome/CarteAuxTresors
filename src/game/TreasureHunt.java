package game;

import exceptions.InvalidDataException;

import java.io.IOException;

public class TreasureHunt {
    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Usage: <input file> <output file (optional)>");
        }

        try {
            var game = GameParser.load(args[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }
}
