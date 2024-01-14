package akka;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;

import akka.Actor.ActorClass;

public class App {

    public static final int nb_actors = 50;
    public static ActorRef initiactor;

    public static void main(String[] args) throws InterruptedException {
        //testVitesseRange(5000, 15000, 1000);
        testVitesseNbActor(32000);
    }

    /**
     * Test sur echelle de nb d'acteurs
     * @param debut Première valeur d'acteur a tester
     * @param fin Limite d'acteurs
     * @param increment Incrementation
     * @throws InterruptedException
     */
    public static void testVitesseRange(int debut, int fin, int increment) throws InterruptedException{
        if(debut<fin){
            int nb_actors_copy=debut;
            while(nb_actors_copy<=fin){
                testVitesseNbActor(nb_actors_copy);
                nb_actors_copy+=increment;
            }
        }
        
    }
    /**
     * Dependant nb_actors en variable global
     * Calcule le temps d'attente selon .0035*(nbActeursDansLeTest/nbActeurDeBase)+1 secondes
     * @param _nb_actors Nombre d'acteurs dans le reseau a tester
     * @throws InterruptedException
     */
    public static void testVitesseNbActor(int _nb_actors) throws InterruptedException{
        System.out.println("Test de vitesse pour "+_nb_actors+" acteurs");
        ActorSystem actorSystem = ActorSystem.create("ChangRobertsAlgorithm"+_nb_actors);
        initiactor = actorSystem.actorOf(ActorClass.props(_nb_actors, true));
        initiactor.tell("starting...", ActorRef.noSender());

        int sleepTime = (int)Math.ceil(.0035*(_nb_actors/nb_actors)+1)*1000;
        System.out.println("Sleep for "+sleepTime);
        Thread.sleep(sleepTime);
        actorSystem.terminate();
    }
}