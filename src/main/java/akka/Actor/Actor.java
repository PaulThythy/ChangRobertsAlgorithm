package akka.Actor;

import javax.swing.AbstractAction;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.AbstractActor;

public class Actor extends AbstractActor {
    private ActorRef next;
    private int id;
    private boolean participant = false;
    private boolean leader = false;

    private Actor(int _id){
        this.id = _id;
        this.next = getContext().actorOf(Actor.props(++_id));
    }

    private Actor(int _id, ActorRef initiactor){
        this.id = _id;
        this.next = initiactor;
    }

    // actor creation
    public static Props props(int _id){
        return Props.create(Actor.class, _id);
    }

    public static Props props(int _id, ActorRef initiactor){
        return Props.create(Actor.class, _id, initiactor);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, message -> {
                System.out.println(message);
                String newMessage = "Message from actor "+this.id;
                this.next.forward(newMessage, getContext());
            })
            .build();
    }
}
