package communication;
import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private String owner;
    private Queue<Message> messageList;
    private int expectedMessages;

    public MessageQueue(String owner, int expectedMessages) {
        this.owner = owner;
        this.expectedMessages = expectedMessages;
        this.messageList = new LinkedList<>();
    }


    public String getOwner() {
        return owner;
    }
    public Queue<Message> getMessageList() {
        return messageList;
    }
}
