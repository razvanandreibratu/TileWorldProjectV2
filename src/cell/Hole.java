package cell;

import colors.ColorMap;

public class Hole extends Cell {
    private final String symbol = "H";
    private final ColorMap color;
    private int depth;
    public Hole(int xPosition, int yPosition, ColorMap color, int depth) {
        super(xPosition, yPosition);
        this.color = color;
        this.depth = depth;
    }

    public void covorHole() {
        if (depth > 0) {
            depth--;
        }
    }
    public int getDepth() {
        return depth;
    }


    // Get the specific color of the symbol
    @Override
    public String getSymbolFormated(){
        return this.color.getCode() + this.symbol + ColorMap.RESET.getCode();
    }
}
