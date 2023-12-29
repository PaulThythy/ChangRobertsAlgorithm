package akka;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.Actor.Actor;
public class App {

    public static int nb_actors = 25;
    public static ActorRef initiactor;

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("ChangRobertsAlgorithm");

        initiactor = actorSystem.actorOf(Actor.props(nb_actors, true));

        initiactor.tell("starting...", ActorRef.noSender());

        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        actorSystem.terminate();
    }
}