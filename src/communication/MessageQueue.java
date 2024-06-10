package communication;
import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private String owner;
    private Queue<Message> messageQueue;

    public MessageQueue(String owner) {
        this.owner = owner;
        this.messageQueue = new LinkedList<>();
    }

    // Getters
    public String getOwner() {
        return owner;
    }
    public Queue<Message> getMessageList() {
        return messageQueue;
    }
    public synchronized void addMessage(Message message) {
        messageQueue.add(message);
        notifyAll(); // Notify all waiting threads
    }

    // Retrieve first item in queue or wait
    public synchronized Message getNextOperation() {
        while (messageQueue.isEmpty()) {
            System.out.println("The message queue is empty");
            try {
                // If the queue is empty pause the thread
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return messageQueue.poll();
    }
    public synchronized void checkMessageList(String agentName) {
        while (messageQueue.isEmpty()) {
            System.out.println(agentName + ": Waiting for messages");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public synchronized void isMessageListProcessed() {
        while (!messageQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
