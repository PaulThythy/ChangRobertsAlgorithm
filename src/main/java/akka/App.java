package akka;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.Actor.ActorClass;
import java.time.Duration;
import java.time.LocalDateTime;

public class App {

    public static int nb_actors = 25;
    public static ActorRef initiactor;
    public static void main(String[] args) {
        
        while(nb_actors<=250000000){
            LocalDateTime start;
            LocalDateTime end;
            ActorSystem actorSystem = ActorSystem.create("ChangRobertsAlgorithm");

            initiactor = actorSystem.actorOf(ActorClass.props(nb_actors, true));
            start=LocalDateTime.now();
            initiactor.tell("starting...", ActorRef.noSender());
            end=LocalDateTime.now();

            System.out.println("nb_actors ="+nb_actors+", Durée(nanos) = "+Duration.between(start,end).toNanos());
            System.out.println("nb_actors ="+nb_actors+", Durée(millis) = "+(double)Duration.between(start,end).toMillis());
            System.out.println("nb_actors ="+nb_actors+", Durée(seconds) = "+(double)Duration.between(start,end).toMillis()/1000);
            try {
                Thread.sleep(5000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            actorSystem.terminate();
            nb_actors=nb_actors*10;
        }
    }
}