package akka.Message;

import java.time.LocalDateTime;

public class Message {
    // if false, it's an electedMsg
    private boolean isElectionMsg;
    private int actorId;
    private final LocalDateTime startDateTime;

    public Message(boolean _isElectionMsg, int _actorId,LocalDateTime _startDateTime) {
        this.isElectionMsg = _isElectionMsg;
        this.actorId = _actorId;
        startDateTime=_startDateTime;
    }

    public boolean isElectionMsg() { return this.isElectionMsg; }

    public int getMsgActorId() { return this.actorId; }

    public LocalDateTime getStartDateTime(){return this.startDateTime;}

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
