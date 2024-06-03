package cell;

import colors.ColorMap;

public class Tile extends Cell{

    // Color and symbol used for format
    private final String symbol = "T";
    private final ColorMap color;

    private int numberOfTiles;

    public Tile(int xPosition, int yPosition, ColorMap color, int numberOfTiles) {
        super(xPosition, yPosition);
        this.color = color;
        this.numberOfTiles = numberOfTiles;
    }
    // Get the specific color of the symbol
    public String getSymbolFormated(){
        return this.color.getCode() + this.symbol + ColorMap.RESET.getCode();
    }
    public void decrementNumberOfTiles() {
        if(this.numberOfTiles > 0) {
            this.numberOfTiles--;
        }
    }
    public int getNumberOfTiles() {
        return this.numberOfTiles;
    }
}
