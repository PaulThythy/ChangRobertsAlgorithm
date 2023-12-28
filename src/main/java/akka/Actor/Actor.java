package akka.Actor;

import akka.App;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.AbstractActor;
import java.util.Random;

public class Actor extends AbstractActor {
    private ActorRef next;
    private int id;
    private boolean isInitiactor;
    private boolean participant = false;
    private boolean leader = false;

    private Actor(int nbActorsRemaining, boolean _isInitiactor) {
        Random rand = new Random();
        int max = Integer.MAX_VALUE;
        this.id = rand.nextInt(max);
        this.isInitiactor = _isInitiactor;

        if(nbActorsRemaining > 1) {
            this.next = getContext().actorOf(Actor.props(--nbActorsRemaining, false));
        } 
        else{
            this.next = App.initiactor;
        }
    }

    public static Props props(int nbActorsRemaining, boolean _isInitiactor) {
        return Props.create(Actor.class, nbActorsRemaining, _isInitiactor);
    }  

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, message -> {
                System.out.println(message);
                String newMessage = "";

                if(this.isInitiactor) {
                    newMessage += "----------------------------\n";
                    newMessage += "Message from actor "+this.id;
                }else{
                    newMessage += "Message from actor "+this.id;
                }
                
                this.next.tell(newMessage, getSelf());
            })
            .build();
    }
}
