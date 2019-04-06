package top.andrewchen1.chapter4.homework;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author dafuchen
 * 2019-03-23
 */
public class ServerActor extends AbstractActor {
    private  final LoggingAdapter log = Logging.getLogger(context().system(), this);
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(Connected.class, x ->  {
                    log.info(x.getClass().toString());
                    sender().tell(new Ping(), self());
                })
                .build();
    }
}
