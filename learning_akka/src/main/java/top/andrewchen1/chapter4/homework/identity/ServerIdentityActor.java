package top.andrewchen1.chapter4.homework.identity;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import top.andrewchen1.chapter4.homework.Ping;

/**
 * @author dafuchen
 * 2019-03-23
 */
public class ServerIdentityActor extends AbstractActor {
    private ActorRef actorRef;

    @Override
    public void preStart() {

    }
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(Ping.class, x-> {
                    System.out.println(x.getClass().toString());
                })
                .build();
    }
}
