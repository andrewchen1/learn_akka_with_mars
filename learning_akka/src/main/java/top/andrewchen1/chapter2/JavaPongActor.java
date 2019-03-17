package top.andrewchen1.chapter2;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class JavaPongActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().matchEquals("Ping", s -> sender().tell("Pong", ActorRef.noSender()))
                .matchAny(x -> sender().tell(new Status.Failure(new Exception("unknow message")), self()))
                .build();
    }
}
