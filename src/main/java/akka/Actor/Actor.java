package akka.Actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.App;
import akka.actor.AbstractActor;

public class Actor extends AbstractActor {
    private ActorRef next;
    private int id;
    private boolean participant = false;
    private boolean leader = false;
    private boolean nextIsInitiactor = false;

    private Actor(int _id) {
        this.id = _id;

        int newId = App.genNewId();
        this.next = getContext().actorOf(Actor.props(newId));
        
        App.LST_ID_ATTRIBUTED.add(newId);
        App.LST_ACTORS.add(this.next);
    }

    private Actor(ActorRef initiactor) {
        this.next = initiactor;

        int newId = App.genNewId();
        this.id = newId;
        App.LST_ID_ATTRIBUTED.add(newId);

        this.nextIsInitiactor = true;
    }

    // creation of the initiactor and others actors
    public static Props props(int _id) {
        return Props.create(Actor.class, _id);
    }

    // creation of the last actor
    public static Props props(ActorRef initiactor) {
        return Props.create(Actor.class, initiactor);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, message -> {
                System.out.println(message);
                String newMessage;
                if(this.nextIsInitiactor){
                    newMessage = "Message from actor "+this.id+"\n-----------------";
                }else{
                    newMessage = "Message from actor "+this.id;
                }
                this.next.forward(newMessage, getContext());
            })
            .build();
    }
}
