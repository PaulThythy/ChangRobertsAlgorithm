package perso.test;


import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import perso.test.Actors.ActorClass;

public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        int nbActor = 5; //Limité par puissance de la machine

        ActorSystem acSystem = ActorSystem.create("MonSystem");
        ActorRef last = ActorClass.initCircle(acSystem, nbActor);
        last.tell(new ActorClass.signal(-1, null), ActorRef.noSender()); //Envoi du message de bouclage
        
        //Thread.sleep(2000);
        //acSystem.terminate();
    }
}
