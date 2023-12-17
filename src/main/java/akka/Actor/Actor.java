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
    private boolean nextIsInitiactor = false;

    private Actor(int _id, int nbActorsRemaining) {
        this.id = _id;

        if(nbActorsRemaining > 1) {                        
            Random rand = new Random();
            int max = Integer.MAX_VALUE;
            int id_next = rand.nextInt(max) + 1;
            this.next = getContext().actorOf(Actor.props(id_next, --nbActorsRemaining));
        }
    }

    private Actor(ActorRef initiactor) {
        this.next = initiactor;
        Random rand = new Random();
        int max = Integer.MAX_VALUE;
        this.id = rand.nextInt(max) + 1; 
        this.nextIsInitiactor = true;
    }

    // creation of the initiactor and others actors
    public static Props props(int _id, int nbActorsRemaining) {
        return Props.create(Actor.class, _id, nbActorsRemaining);
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
