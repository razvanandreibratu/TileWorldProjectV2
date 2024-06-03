package communication;

public class Message {
    private String sender;
    private String inReplayTo;
    private String replayWith;
    private Operation operation;
    // @Only 2 strings accepted
    // ERROR and SUCCESS
    private String successCode;

    public Message(String sender, String inReplayTo, String replayWith, Operation operation, String successCode) {
        this.sender = sender;
        this.inReplayTo = inReplayTo;
        this.replayWith = replayWith;
        this.operation = operation;
        this.successCode = successCode;
    }


    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", receiver='" + inReplayTo + '\'' +
                ", message='" + replayWith + '\'' +
                ", operation=" + operation +
                ", successCode='" + successCode + '\'' +
                '}';
    }
}
