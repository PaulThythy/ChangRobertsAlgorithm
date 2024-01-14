package akka.Actor;

import akka.App;
import akka.Message.*;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.AbstractActor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class ActorClass extends AbstractActor {
    private ActorRef next;
    private int id;
    private boolean verbose=false; //Pour le debug. Active l'affichage des messages
    private int maxActor;
    private boolean isInitiactor;
    private boolean participant = false;
    private boolean leader = false;

    private ActorClass(int nbActorsRemaining, boolean _isInitiactor) {
        Random rand = new Random();
        int max = Integer.MAX_VALUE;
        this.id = rand.nextInt(max);
        this.isInitiactor = _isInitiactor;

        if(nbActorsRemaining > 1) {
            this.next = getContext().actorOf(ActorClass.props(--nbActorsRemaining, false));
        } 
        else{
            this.next = App.initiactor;
        }
    }

    public static Props props(int nbActorsRemaining, boolean _isInitiactor) {
        return Props.create(ActorClass.class, nbActorsRemaining, _isInitiactor);
    }  

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, message -> {
                // case only for the initiation message
                if(verbose)
                    System.out.println("Lack of leader detected by " + this.id + ", initiation message");

                // marks itself as a participant
                this.participant = true;

                Message electionMsg = new Message(true, this.id,LocalDateTime.now());
                // sends the message to the next node
                this.next.tell(electionMsg, getSelf());
            })

            .match(Message.class, message -> {

                // prints the received message
                if(verbose)
                    System.out.println(message.toString());

                if(message.isElectionMsg()) {
                    
                    if(message.getMsgActorId() > this.id){ //Passage du message sans modification
                        this.participant = true;
                        this.next.tell(message, getSelf());
                    }

                    else if(message.getMsgActorId() < this.id && !this.participant) { //Passage du message avec mon id
                        this.participant = true;
                        Message electionMsg = new Message(true, this.id,message.getStartDateTime());
                        this.next.tell(electionMsg, getSelf());
                    }

                    else if(message.getMsgActorId() < this.id && this.participant) {
                        this.next.tell(message, getSelf());
                    }

                    else if(message.getMsgActorId() == this.id) { //Je suis le leader
                        // become leader
                        this.participant = false;
                        this.leader = true;
                        if(verbose)
                            System.out.println(this.id + " is the new leader. Transmitting the elected message to the next node...");
                        Message electedMsg = new Message(false, this.id,message.getStartDateTime());
                            //Affichage du temps d'election
                        System.out.println("\tLeader elue en : "+Duration.between(message.getStartDateTime(), LocalDateTime.now()).toNanos()+"\n");
                        this.next.tell(electedMsg, getSelf());
                    }

                }else{

                    if(message.getMsgActorId() == this.id) {
                        if(verbose)
                            System.out.println("Leader elected. End of the program...");
                    }else{
                        this.participant = false;
                        this.next.tell(message, getSelf());
                    }
                
                }

            })
            .build();
    }
}
