package environment;

import cell.*;
import agent.Agent;
import communication.Operation;

import java.util.*;

public class Environment {
    private final int width;
    private final int height;
    private Cell[][] map;
    //Keep track of operations

    //specific times
    private long tickTime;
    private long totalTime;

    // Keep track of the
    // Cells on the map
    List<Agent> agentList = new ArrayList<>();
    List<Tile> tileList = new ArrayList<>();
    List<Obstacle> obstacleList = new ArrayList<>();
    List<Hole> holeList = new ArrayList<>();

    public Environment(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Cell[width][height]; // compute the map when the env is created
        initializeMap();
    }
    public synchronized void initializeMap() {
        // First we populate the grid with empty cells
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = new Cell(i, j);
            }
        }
        // Order matter when we initialize the map
        // Because we can overwrite a tile
        // If we overwrite a tile
        // The agents cannot see it
        for (Obstacle obstacle : obstacleList) {
            map[obstacle.getXPosition()][obstacle.getYPosition()] = obstacle;
        }
        for (Agent agent : agentList) {
            map[agent.getXPosition()][agent.getYPosition()] = agent;
        }
        for (Tile tile : tileList) {
            map[tile.getXPosition()][tile.getYPosition()] = tile;
        }
        for (Hole hole : holeList) {
            map[hole.getXPosition()][hole.getYPosition()] = hole;
        }
    }
    // Methods for populating the map
    public void addAgent(Agent agent) {
        agentList.add(agent);
    }
    public void addTile(Tile tile) {
        tileList.add(tile);
    }
    public void addObstacle(Obstacle obstacle) {
        obstacleList.add(obstacle);
    }
    public void addHole(Hole hole) {
        holeList.add(hole);
    }
    // Methods for time
    public void setTickTime(long tickTime){
        this.tickTime = tickTime;
    }
    public void setTotalTime(long totalTime){
        this.totalTime = totalTime;
    }
    // Methods to retrieve data
    public List<Agent> getAgentList() {
        return agentList;
    }
    // Printing the state of the map
    public void printMap() {
        for (Cell[] row : map) {
            for (Cell c : row) {
                System.out.print(c.getSymbolFormated());
            }
            System.out.println();
        }
    }
}
