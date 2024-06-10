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

    public Operation getOperation() {
        return this.operation;
    }
    public String getSender() {
        return this.sender;
    }
    public String getInReplayTo() {
        return this.inReplayTo;
    }
    public String getReplayWith() {
        return this.replayWith;
    }
    public String getSuccessCode() {
        return this.successCode;
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
