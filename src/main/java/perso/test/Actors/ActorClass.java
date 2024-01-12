package perso.test.Actors;
import akka.actor.AbstractActor;
import akka.actor.Props;
public class ActorClass extends AbstractActor {
    public interface Message {}

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Test01.class, message -> {
                System.out.println("Test01 recoit "+message.msg);
            })
            .match(Test02.class, message ->{ 
                System.out.println("Test02 recoit "+message.msg+" de valeur "+message.val);
            })
            .build();
    }


    public static class Test01 implements Message{
        public final String msg;

        public Test01(final String msg){
            this.msg=msg;
        }
    }

    public static class Test02 implements Message{
        public final int val;
        public final String msg;

        public Test02(final int val,final String msg){
            this.val=val;
            this.msg=msg;
        }
    }

    public static Props props(){
        return Props.create(ActorClass.class);
    }
}


