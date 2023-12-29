package akka.Message;

public class Message {
    // if false, it's an electedMsg
    private boolean isElectionMsg;
    private int actorId;

    public Message(boolean _isElectionMsg, int _actorId) {
        this.isElectionMsg = _isElectionMsg;
        this.actorId = _actorId;
    }

    public boolean isElectionMsg() { return this.isElectionMsg; }

    public int getMsgActorId() { return this.actorId; }

    public String toString() {
        String str = "";

        if(this.isElectionMsg) {
            str += "Election message sent containing the id : " + this.actorId;
        }
        else {
            str += "Transmitting the elected message with the leader's id : " + this.actorId;
        }

        return str;
    }
}
