package akka;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.Actor.Actor;

public class App {

    public static int NB_ACTORS = 10;
    public static void main(String[] args){
        ActorSystem actorSystem = ActorSystem.create();

        ActorRef initiactor = actorSystem.actorOf(Actor.props(0));
        ActorRef lastActor = actorSystem.actorOf(Actor.props(NB_ACTORS-1, initiactor));
        initiactor.tell("Message from actor 0", ActorRef.noSender());

        actorSystem.terminate();
    }
}