package perso.test.Actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class ActorClass extends AbstractActor {
    public interface Message {}
    final int MAX_ID=9999999;
    final int id;
    ActorRef refAp;

    public ActorClass(){
        id=-1;
        refAp=null;
    }
    public ActorClass(ActorSystem as, int _nbActor){
        id=(int)Math.floor(Math.random()*MAX_ID);
        refAp=null;
        if(_nbActor>0){
            refAp= as.actorOf(ActorClass.props(as,_nbActor-1));
        }
        System.out.println("Création d'un acteur : =>"+refAp);
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, text ->{
                if(text.equals("connect") && refAp==null){ //Si pas de suivant
                    refAp=sender();
                }else if(text.equals("connect")&&refAp!=sender()){ //Si a un suivant different de l'expéditeur alors l'appel
                    refAp.tell(text, sender());
                }
            })
            .match(signal.class, signal -> {
                if(signal.code<0){ //Connection des bornes de l'anneau
                    System.out.println(""+signal.code+"Mon suivant est "+refAp);
                    refAp.tell(new ActorClass.signal(signal.code-1, null), ActorRef.noSender());
                }else if(signal.code>0){ //transmet id
                    
                }else{ //Arrêt

                }
            })
            .build();
    }

    /**
     * Initialise un anneau d'acteur et retourne le dernier acteur crée (recursif)
     * @param as ActorSystem
     * @param nbActor Nombre max d'acteur
     * @return Un acteur pour intéragire avec l'anneau (le dernier crée)
     */
    public static ActorRef initCircle(ActorSystem as, int _nbActor){
        ActorRef last=null;
        if(_nbActor>0){
            last = as.actorOf(ActorClass.props(as, _nbActor-1), "Actor"+_nbActor);
        }
        last.tell("connect",last);
        return last;
    }

    /**
     * [X]
     * @return
     */
    public static Props props(){
        return Props.create(ActorClass.class);
    }

    private static Props props(ActorSystem as,int nb){
        return Props.create(ActorClass.class, as, nb);
    }

    public static class signal implements Message{
        final int code;
        final ActorSystem as;

        public signal(int _code, ActorSystem _as){
            code=_code;
            as=_as;
        }
    }

}


