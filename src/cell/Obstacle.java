package cell;

import colors.ColorMap;

public class Obstacle extends Cell {
    private final String symbol = "O";
    private final ColorMap color = ColorMap.WHITE;
    public Obstacle(int xPosition, int yPosition) {
        super(xPosition, yPosition);
    }
    // Get the specific color of the symbol
    @Override
    public String getSymbolFormated(){
        return this.color.getCode() + this.symbol + ColorMap.RESET.getCode();
    }
}
