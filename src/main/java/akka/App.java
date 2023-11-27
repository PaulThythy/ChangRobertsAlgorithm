package akka;

import akka.actor.ActorSystem;

public class App {
    public static int NB_ACTORS = 10;
    public static void main(String[] args){
        ActorSystem actorSystem = ActorSystem.create();

        

        actorSystem.terminate();
    }
}