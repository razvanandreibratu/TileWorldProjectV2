package agent;
import cell.Cell;
import cell.Tile;
import colors.ColorMap;

public class Agent extends Cell {
    // Identifier for thread
    private int points = 0;
    private boolean principal = false;
    private Tile carriedTile = null;

    // Symbol and color for representation
    private final String symbol = "A";
    private final ColorMap color;

    public Agent(int startPositionX, int startPositionY, ColorMap color) {
        super(startPositionX, startPositionY);
        this.color = color;
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
