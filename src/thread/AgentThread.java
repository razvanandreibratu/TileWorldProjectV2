package thread;

import agent.Agent;
import communication.Message;
import communication.MessageQueue;
import communication.Operation;
import environment.Environment;

import java.util.Map;

public class AgentThread extends Thread {
    private Environment environment;
    private MessageQueue[] agentsMessageBoxes;
    private MessageQueue environmentMessageBox;
    private MessageQueue negotiationMessageBox;
    private Ticker ticker;
    private String agentName;

    public AgentThread(MessageQueue[] agentsMessageBoxes, MessageQueue environmentMessageBox, MessageQueue negotiationMessageBox,
                       Environment environment, Ticker ticker, String agentName) {
        this.agentsMessageBoxes = agentsMessageBoxes;
        this.environmentMessageBox = environmentMessageBox;
        this.negotiationMessageBox = negotiationMessageBox;
        this.environment = environment;
        this.ticker = ticker;
        this.agentName = agentName;
    }

    @Override
    public void run() {
        while (ticker.running()) {
            ticker.action();

            // Compute distances to holes and tiles
            int[] position = getPosition();

            // TODO BASED ON position make the negotiantion
            // Change from Null to actuall message
            // Based on this distances the AGENTS should
            // negotiate
            Message negotiationMessage = null;
            notifyPrincipal(negotiationMessage);

            negotiationMessageBox.checkMessageList(agentName);
            negotiationMessageBox.isMessageListProcessed();

            processMessageList();

            environmentMessageBox.checkMessageList(agentName);
            environmentMessageBox.isMessageListProcessed();

            processMessageList();
        }

        System.out.println(agentName + ": ended.");
    }

    private int[] getPosition() {
        for (Agent agent : environment.getAgentList()) {
            if (agentName.equalsIgnoreCase(agent.getName())) {
                return new int[] { agent.getXPosition(), agent.getYPosition() };
            }
        }
        return null;
    }

    private synchronized void processMessageList() {
        for (MessageQueue agentMessageBox : agentsMessageBoxes) {
            if (agentName.equalsIgnoreCase(agentMessageBox.getOwner())) {
                for (Message message : agentMessageBox.getMessageList()) {
                    System.out.println(agentName + ": " + message);
                    Operation operation = message.getOperation();
                    Message responseMessage;
                    switch (operation.getOperation()) {
                        case "MOVE":
                            responseMessage = move(operation.getPosition());
                            break;
                        case "PICK":
                            responseMessage = pickTile(operation.getPosition());
                            break;
                        case "DROP":
                            responseMessage = dropTile(operation.getPosition());
                            break;
                        case "USE":
                            responseMessage = useTile(operation.getDirection());
                            break;
                        case "TRANSFER":
                            responseMessage = transferPoints(operation.getToAgent(), operation.getTransferPoints());
                            break;
                        default:
                            continue;
                    }
                    notifyEnvironment(responseMessage);
                }
                agentMessageBox.getMessageList().clear();
                break;
            }
        }
    }

    private Message move(Map<String, Integer> newPosition) {
        System.out.println("move(): " + agentName + ": position=" + newPosition);
        Operation operation = new Operation("MOVE", null, 0, null, null, newPosition);
        return new Message(agentName, null, "OPERATION_SUCCESS_CODE", operation, null);
    }

    private Message pickTile(Map<String, Integer> position) {
        System.out.println("pickTile(): " + agentName + ": position=" + position);
        Operation operation = new Operation("PICK", null, 0, null, null, position);
        return new Message(agentName, null, "OPERATION_SUCCESS_CODE", operation, null);
    }

    private Message dropTile(Map<String, Integer> position) {
        System.out.println("dropTile(): " + agentName + ": position=" + position);
        Operation operation = new Operation("DROP", null, 0, null, null, position);
        return new Message(agentName, null, "OPERATION_SUCCESS_CODE", operation, null);
    }

    private Message useTile(String direction) {
        System.out.println("useTile(): " + agentName + ": direction=" + direction);
        Operation operation = new Operation("USE", null, 0, direction, null, null);
        return new Message(agentName, null, "OPERATION_SUCCESS_CODE", operation, null);
    }

    private Message transferPoints(String toAgent, int transferPoints) {
        System.out.println("transferPoints(): " + agentName + ": toAgent=" + toAgent + "; transferPoints=" + transferPoints);
        Operation operation = new Operation("TRANSFER", toAgent, transferPoints, null, null, null);
        return new Message(agentName, null, "OPERATION_SUCCESS_CODE", operation, null);
    }

    private Message negotiate(Map<String, Integer> distances) {
        System.out.println("negotiate(): " + agentName + ": distances=" + distances);
        Operation operation = new Operation("NEGOTIATION", null, 0, null, distances, null);
        return new Message(agentName, null, "NEGOTIATION_RESULT", operation, null);
    }

    private Map<String, Object> computeDistances(int[] position) {
        //TODO Compute distance
        return null;
    }

    private void notifyPrincipal(Message message) {
        negotiationMessageBox.addMessage(message);
    }

    private void notifyEnvironment(Message message) {
        environmentMessageBox.addMessage(message);
    }
}
