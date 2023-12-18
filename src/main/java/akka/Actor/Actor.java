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

    private Actor(int nbActorsRemaining, ActorRef initiactor) {
        System.out.println(initiactor);
        Random rand = new Random();
        int max = Integer.MAX_VALUE;
        this.id = rand.nextInt(max);

        if(nbActorsRemaining > 1) {
            this.next = getContext().actorOf(Actor.props(--nbActorsRemaining, initiactor)); 
        }else{
            this.next = initiactor;
        }
    }

    public static Props props(int nbActorsRemaining, ActorRef initiactor) {
        return Props.create(Actor.class, nbActorsRemaining, initiactor);
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
