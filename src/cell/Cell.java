package cell;
import colors.ColorMap;

public class Cell {
    private int xPosition;
    private int yPosition;
    private final String symbol = "=";
    private final ColorMap color = ColorMap.YELLOW;
    public Cell(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    public String getSymbolFormated(){
        // Get the specific color of the symbol
        return this.color.getCode() + this.symbol + ColorMap.RESET.getCode();
    }

    //Getters and setters
    //Only used for agent class
    public int getXPosition() {
        return this.xPosition;
    }
    public int getYPosition() {
        return this.yPosition;
    }
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

}
