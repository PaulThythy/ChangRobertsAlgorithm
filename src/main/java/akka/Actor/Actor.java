package akka.Actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.AbstractActor;
import java.util.Random;

public class Actor extends AbstractActor {
    private ActorRef next;
    private int id;
    //private boolean participant = false;
    //private boolean leader = false;

    private Actor(int nbActorsRemaining) {
        Random rand = new Random();
        int max = Integer.MAX_VALUE;
        this.id = rand.nextInt(max);
        this.next = getContext().actorOf(Actor.props(--nbActorsRemaining)); 
    }

    private Actor(ActorRef initiactor) {
        Random rand = new Random();
        int max = Integer.MAX_VALUE;
        this.id = rand.nextInt(max);
        this.next = initiactor;
    }

    public static Props props(int nbActorsRemaining) {
        if(nbActorsRemaining > 1) {
            return Props.create(Actor.class, nbActorsRemaining);
        }
        else {
            return null;
        }
    }  

    public static Props props(ActorRef initiactor) {
        return Props.create(Actor.class, initiactor);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, message -> {
                System.out.println(message);
                String newMessage;
                newMessage = "Message from actor "+this.id;
                this.next.tell(newMessage, getSelf());
            })
            .build();
    }
}
