package utils;

import java.io.*;

import colors.ColorMap;
import environment.Environment;
import agent.Agent;
import cell.*;

public class InputParser {
    private final File file;

    public InputParser(File file) {
        this.file = file;
    }

    public Environment initializaEnvironment() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        // Read the first line with general configuration
        line = reader.readLine();
        String[] config = line.trim().split("\\s+");
        int numAgents = Integer.parseInt(config[0]);
        int tickTime = Integer.parseInt(config[1]);
        int totalTime = Integer.parseInt(config[2]);
        int width = Integer.parseInt(config[3]);
        int height = Integer.parseInt(config[4]);

        Environment environment = new Environment(width, height);
        environment.setTickTime(tickTime);
        environment.setTotalTime(totalTime);

        // Parse agent colors
        String[] colors = new String[numAgents];
        System.arraycopy(config, 5, colors, 0, numAgents);

        // Parse agent initial positions
        int positionIndex = 5 + numAgents;
        for (int i = 0; i < numAgents; i++) {
            int x = Integer.parseInt(config[positionIndex + 2 * i]);
            int y = Integer.parseInt(config[positionIndex + 2 * i + 1]);
            ColorMap color = ColorMap.valueOf(colors[i].toUpperCase());
            Agent agent = new Agent(x, y, color);
            environment.addAgent(agent);
        }
        // Move to the next sections
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("OBSTACLES")) {
                String[] parts = line.split("\\s+");
                for (int i = 1; i < parts.length; i += 2) {
                    int x = Integer.parseInt(parts[i]);
                    int y = Integer.parseInt(parts[i + 1]);
                    Obstacle obstacle = new Obstacle(x, y);
                    environment.addObstacle(obstacle);
                }
            } else if (line.startsWith("TILES")) {
                String[] parts = line.split("\\s+");
                for (int i = 1; i < parts.length; i += 4) {
                    int numTiles = Integer.parseInt(parts[i]);
                    ColorMap color = ColorMap.valueOf(parts[i + 1].toUpperCase());
                    int x = Integer.parseInt(parts[i + 2]);
                    int y = Integer.parseInt(parts[i + 3]);
                    Tile tile = new Tile(x, y, color, numTiles);
                    environment.addTile(tile);
                }
            } else if (line.startsWith("HOLES")) {
                String[] parts = line.split("\\s+");
                for (int i = 1; i < parts.length; i += 4) {
                    int depth = Integer.parseInt(parts[i]);
                    ColorMap color = ColorMap.valueOf(parts[i + 1].toUpperCase());
                    int x = Integer.parseInt(parts[i + 2]);
                    int y = Integer.parseInt(parts[i + 3]);
                    Hole hole = new Hole(x, y, color, depth);
                    environment.addHole(hole);
                }
            }
        }
        reader.close();
        return environment;
    }
}
