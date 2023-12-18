package akka;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.Actor.Actor;
public class App {

    public static int nb_actors = 10;

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("ChangRobertsAlgorithm");

        //ActorRef[] actors = new ActorRef[nb_actors];

        /*for(int i = 0; i < nb_actors; i++) {
            actors[i] = actorSystem.actorOf(Actor.props(actors[(i+1)%nb_actors]));
        }*/

        //actors[0].tell("Lack of leader, initiation message", ActorRef.noSender());

        ActorRef initiactor = actorSystem.actorOf(Actor.props(nb_actors));
        ActorRef lastActor = actorSystem.actorOf(Actor.props(initiactor));

        initiactor.tell("Lack of leader, initiation message", ActorRef.noSender());

        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        actorSystem.terminate();
    }
}