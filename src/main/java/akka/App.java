package akka;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.Actor.Actor;

public class App {

    public static int NB_ACTORS = 10;

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("ChangRobertsAlgorithm");

        ActorRef initiactor = actorSystem.actorOf(Actor.props(0, NB_ACTORS));
        ActorRef lastActor = actorSystem.actorOf(Actor.props(initiactor));

        initiactor.tell("Lack of leader, initiation message", ActorRef.noSender());

        actorSystem.terminate();
    }
}