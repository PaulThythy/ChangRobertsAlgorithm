package perso.test;


import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import perso.test.Actors.ActorClass;

public class App 
{
    public static void main( String[] args )
    {
        ActorSystem acSystem = ActorSystem.create();
        ActorRef acRef = acSystem.actorOf(ActorClass.props());

        acRef.tell(new ActorClass.Test02(42, "Ceci est un test2"), ActorRef.noSender());

        acSystem.terminate();
    }
}
