package top.andrewchen1.chapter4;

import akka.actor.AbstractActorWithStash;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;
import top.andrewchen1.chapter2.db.GetRequest;
import top.andrewchen1.chapter4.dto.Connected;
import top.andrewchen1.chapter4.dto.Disconnected;
import top.andrewchen1.chapter4.dto.ProcessMessageUtil;

/**
 * @author dafuchen
 * 2019-03-23
 */
public class ActorBecome extends AbstractActorWithStash {

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(GetRequest.class, x -> stash())
                .match(Connected.class, x -> {
                    context().become(online());
                    unstash();
                })
                .match(Disconnected.class, x-> context().unbecome())
                .build();
    }

    final private PartialFunction<Object, BoxedUnit> online() {
        return ReceiveBuilder.create()
                .match(GetRequest.class, x -> {
                    ProcessMessageUtil.processMessage(x);
                })
                .build().onMessage();
    }
}
