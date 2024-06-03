import environment.Environment;
import utils.InputParser;

import java.io.File;
import java.io.IOException;

public class TileWorldMain {
    public static void main(String[] args) {

        File inputFile = new File("./src/Utils/input.txt");
        InputParser parser = new InputParser(inputFile);

        Environment env;
        try {
            env = parser.initializaEnvironment();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        env.initializeMap();
        env.printMap();
    }
}
