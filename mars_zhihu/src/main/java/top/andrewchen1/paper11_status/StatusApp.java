package top.andrewchen1.paper11_status;

import akka.actor.AbstractActorWithTimers;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author dafuchen
 * 2019-05-04
 */
public class StatusApp extends AbstractActorWithTimers {
    private final LoggingAdapter log = Logging.getLogger(context().system(), StatusApp.class);
    @Override
    public Receive createReceive() {
        return null;
    }
}
