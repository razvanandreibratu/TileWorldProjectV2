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
    private long remainingTime;

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
        // Because we can't overwrite a tile
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
        //We set also the remaining time with totalTime
        this.remainingTime = totalTime;
    }
    public long getTotalTime(){return this.totalTime;}
    public long getRemainingTime(){return this.remainingTime;}
    public void decreaseRemainingTime(long amount) {
        this.remainingTime -= amount;
    }
    public long getTickTime(){return this.tickTime;}
    // Methods to retrieve data
    public List<Agent> getAgentList() {
        return agentList;
    }

    //OPERATIONS
    public Boolean executeOperation(String sender, Operation operation){
        switch (operation.getOperation()){
            case "PICK":
                return pick(sender, operation);
            case "MOVE":
                return move(sender, operation);
            case "USE":
                return use(sender, operation);
            case "DROP":
                return drop(sender, operation);
            case "TRANSFER":
                return transfer(sender, operation);
            default:
                System.out.println("Invalid operation");
                return false;

        }
    }
    public boolean pick(String sender, Operation operation){
        Map<String, Integer> position = operation.getPosition();
        int posX = position.get("x");
        int posY = position.get("y");

        if (!(map[posX][posY] instanceof Tile)) {
            System.out.println("Invalid position");
            return false;
        }
        for (Agent agent : agentList) {
            // Find the agent wich started the oepration
            if (sender.equalsIgnoreCase(agent.getName())){
                for(Tile tile : tileList) {
                    if (tile.getXPosition() == posX && tile.getYPosition() == posY) {
                        if (tile.getNumberOfTiles() > 1) {
                            Tile agentTile = new Tile(posX, posY, tile.getColor(), 1);
                            agent.setTile(agentTile);
                            tile.decrementNumberOfTiles();
                        } else {
                            Tile agentTile = new Tile(posX, posY, tile.getColor(), 1);
                            agent.setTile(agentTile);
                            tile.decrementNumberOfTiles();
                            // TILE IS COVERED
                            tileList.remove(tile);
                        }
                        initializeMap();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean move(String sender, Operation operation){
        Map<String, Integer> position = operation.getPosition();
        int posX = position.get("x");
        int posY = position.get("y");
        if(!canMoveTo(operation.getPosition())) return false;

        for (Agent agent : agentList) {
            if (sender.equalsIgnoreCase(agent.getName())){
                agent.setXPosition(posX);
                agent.setYPosition(posY);
                return true;
            }
        }
        return false;
    }
    public boolean use(String sender, Operation operation){
        for (Agent agent : agentList) {
            if (sender.equalsIgnoreCase(agent.getName())){
                int holeX = operation.getPosition().get("x");
                int holeY = operation.getPosition().get("y");

                switch (operation.getDirection()){
                    case "LEFT":
                        holeY -= 1;
                        break;
                    case "RIGHT":
                        holeY += 1;
                        break;
                    case "DOWN":
                        holeX += 1;
                        break;
                    case "UP":
                        holeY -= 1;
                        break;
                    default:
                        System.out.println("Invalid direction");
                        return false;
                }
                if(!(map[holeX][holeY] instanceof Hole)) return false;
                for(Hole hole: holeList){
                    if(hole.getXPosition() == holeX && hole.getYPosition() == holeY){
                        hole.decrementDepth();
                        if(hole.getDepth() > 0){
                            if(agent.getTile().getColor().equals(agent.getColor())){
                                agent.setPoints(agent.getPoints() + 10);
                            } else {
                                agent.setPoints(agent.getPoints() + 40);
                            }
                            agent.setTile(null);
                        } else {
                            holeList.remove(hole);
                        }
                        initializeMap();
                        return true;
                    }
                }

            }
        }
        return false;
    }
    public boolean drop(String sender, Operation operation){
        Map<String, Integer> position = operation.getPosition();
        int posX = position.get("x");
        int posY = position.get("y");
        if(map[posX][posY] instanceof Hole || map[posX][posY] instanceof Obstacle){
            System.out.println("Cannot drop on Hole or obstacle");
            return false;
        }
        for (Agent agent : agentList) {
            if (sender.equalsIgnoreCase(agent.getName())){
                Tile tile = agent.getTile();
                // Be carefull TO UPDATE each time
                // Agent positions
                tile.setXPosition(agent.getXPosition());
                tile.setYPosition(agent.getYPosition());
                tileList.add(tile);
                initializeMap();
                return true;
            }
        }
        return false;
    }
    public boolean transfer(String sender, Operation operation){
        for (Agent agent: agentList){
            if(sender.equalsIgnoreCase(agent.getName())){
                if(agent.getPoints() - operation.getTransferPoints() < 0)
                {
                    return false;
                }
                agent.setPoints(agent.getPoints() - operation.getTransferPoints());
            }
        }
        for (Agent agent: agentList){
            if (operation.getToAgent().equalsIgnoreCase(agent.getName())) {
                agent.setPoints(agent.getPoints() + operation.getTransferPoints());
                return true;
            }
        }
        return false;
    }

//     Auxilar methods
    private boolean canMoveTo(Map<String, Integer> position) {
        int posX = position.get("x");
        int posY = position.get("y");
        if( posX >= 0 && posX < height &&
                posY >= 0 && posY < width &&
                !(map[posX][posY] instanceof Hole) &&
                !(map[posX][posY] instanceof Obstacle) &&
                !(map[posX][posY] instanceof Agent)) {
            return true;
        }
        return false;
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
