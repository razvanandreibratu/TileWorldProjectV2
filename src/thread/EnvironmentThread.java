package thread;

import agent.Agent;
import communication.MessageQueue;
import environment.Environment;
import communication.*;


public class EnvironmentThread extends Thread {
    private Environment environment;
    private MessageQueue messageQueue = new MessageQueue("Environment");
    private Ticker ticker;
    private MessageQueue[] agentsMessageBoxes;

    public EnvironmentThread(Environment env, Ticker ticker, MessageQueue[] agentsMessageBoxes) {
        this.environment = env;
        this.ticker = ticker;
        this.agentsMessageBoxes = agentsMessageBoxes;
    }

    @Override
    public void run() {
        while (environment.getRemainingTime() > 0) {
            long startTime = System.currentTimeMillis();

            ticker.tick(this.getName());
            processMessageList();

            // Update user interface
            environment.printMap();

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            // Decrease the remaining time by the actual elapsed time or tick time, whichever is less
            environment.decreaseRemainingTime(Math.min(elapsedTime, environment.getTickTime()));

            try {
                // If the operation finished faster than the tick time, sleep for the remaining tick time
                long sleepTime = environment.getTickTime() - elapsedTime;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(this.getName() + ": ended.");
        ticker.stop();
    }

    private void processMessageList() {
        Message message;
        while ((message = messageQueue.getNextOperation()) != null) {
            processMessage(message);
        }
    }

    private synchronized void processMessage(Message message) {
        Operation operation = message.getOperation();
        boolean success = environment.executeOperation(message.getSender(), operation);
        Message confirmation = new Message("environment", message.getSender(), message.getReplayWith(), operation, success ? "SUCCESS" : "ERROR");

        for (MessageQueue agentMessageBox : agentsMessageBoxes) {
            if (message.getSender().equalsIgnoreCase(agentMessageBox.getOwner())) {
                agentMessageBox.addMessage(confirmation);
                break;
            }
        }
    }
}
