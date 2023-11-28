package akka;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.Actor.Actor;
import java.util.ArrayList;

public class App {

    public static int NB_ACTORS = 10;
    public static ArrayList<Integer> LST_ID_ATTRIBUTED = new ArrayList<>();
    public static ArrayList<ActorRef> LST_ACTORS = new ArrayList<>();

    public static boolean alreadyAttributed(int id) {
        return LST_ID_ATTRIBUTED.contains(id);
    }

    public static int getNbActors() {
        return LST_ACTORS.size();
    }

    public static int genNewId() {
        int rand, minBoundary = 1, maxBoundary = NB_ACTORS;
        do {
            rand = (int) ((Math.random() * (maxBoundary - minBoundary)) + minBoundary);
        }while(alreadyAttributed(rand));
        return rand;
    }
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create();

        ActorRef initiactor = actorSystem.actorOf(Actor.props(0));
        LST_ID_ATTRIBUTED.add(0);
        LST_ACTORS.add(initiactor);

        ActorRef lastActor = actorSystem.actorOf(Actor.props(initiactor));
        LST_ACTORS.add(lastActor);
        initiactor.tell("Message initiation", ActorRef.noSender());

        actorSystem.terminate();
    }
}