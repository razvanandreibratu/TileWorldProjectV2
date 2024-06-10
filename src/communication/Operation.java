package communication;
import java.util.HashMap;
import java.util.Map;

public class Operation {
    private String operation; // PICK/DROP/USE/MOVE/TRANSFER/NEGOTIATE
    private String toAgent; // used for Transfer
    private int transferPoints = 0; //used for Transfer
    private String direction; // Left/Right/Up/Down - used for USE
    private Map<String, Integer> position; // used for MOVE/PICK/DROP
    /*
        //Do they need to be close?
        //Find a way to compute distance in units
     */
    private Map<String, Integer> distances; // used for the negotation Process

    // Constructor with all params
    public Operation(String operation, String toAgent, int transferPoints, String direction, Map<String, Integer> distances, Map<String, Integer> position) {
        this.operation = operation;
        this.toAgent = toAgent;
        this.transferPoints = transferPoints;
        this.direction = direction;
        this.position = position;
        this.distances = distances;
    }
    public String getOperation() {
        return operation;
    }
    public Map<String, Integer> getPosition() {
        return position;
    }
    public String getDirection(){
        return direction;
    }
    public int getTransferPoints() {
        return transferPoints;
    }
    public String getToAgent() {
        return toAgent;
    }
    @Override
    public String toString() {
        return "Operation{" +
                "operation='" + operation + '\'' +
                ", toAgent='" + toAgent + '\'' +
                ", transferPoints=" + transferPoints +
                ", direction='" + direction + '\'' +
                ", position=" + position +
                ", distances=" + distances +
                '}';
    }
}
