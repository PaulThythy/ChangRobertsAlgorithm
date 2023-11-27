package akka.Actor;

import javax.swing.AbstractAction;
import akka.actor.ActorRef;

public class Actor extends AbstractActor {
    private ActorRef acteur;

    private Actor(){

    }

    // actor creation
    public static Props props(){
        return Props.create(Actor.class, );
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match()
            .build();
    }
}
