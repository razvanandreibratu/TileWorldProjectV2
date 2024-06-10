package agent;
import cell.Cell;
import cell.Tile;
import colors.ColorMap;

public class Agent extends Cell {
    // Identifier for thread
    private int points = 0;
    private boolean principal = false;
    private Tile carriedTile = null;
    private String name;
    // Symbol and color for representation
    private final String symbol = "A";
    private final ColorMap color;

    public Agent(int startPositionX, int startPositionY, ColorMap color) {
        super(startPositionX, startPositionY);
        this.color = color;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    // Set tile can be used FOR PICK AND DROP
    public void setTile(Tile carriedTile){
        this.carriedTile = carriedTile;
    }
    public Tile getTile(){
        return carriedTile;
    }
    public ColorMap getColor(){
        return color;
    }
    public void setPoints(int points){
        this.points = points;
    }
    public int getPoints(){
        return points;
    }

    // Inherited methods
    @Override
    public String getSymbolFormated(){
        return this.color.getCode() + this.symbol + ColorMap.RESET.getCode();
    }
    @Override
    public int getXPosition() {
        return super.getXPosition();
    }
    @Override
    public int getYPosition() {
        return super.getYPosition();
    }
    @Override
    public void setXPosition(int x) {
        super.setXPosition(x);
    }
    @Override
    public void setYPosition(int y) {
        super.setYPosition(y);
    }
}
