package top.andrewchen1.chapter2.actor;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import top.andrewchen1.chapter2.db.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static akka.pattern.Patterns.ask;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class DbActor extends AbstractActor {
    private  final LoggingAdapter log = Logging.getLogger(context().system(), this);
    private final Map<String, Object> map = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(SetRequest.class, message -> {
                    log.info("Received set request  {}", message);
                    map.put(message.getKey(), message.getValue());
                    ask(context().sender(), new Status.Success(message.getKey()), 1000L);
                })
                .match(GetRequest.class, message -> {
                    log.info("Received get Request {}", message);
                    Object value = map.get(message.getKey());
                    if (Objects.isNull(value)) {
                        ask(context().sender(), new Status.Failure(new KeyNotFoundException(message.getKey())), 1000L);
                    } else {
                        ask(context().sender(), value, 1000L);
                    }
                })
                .match(Delete.class, message -> {
                    log.info("Received get Request {}", message);
                    Object value = map.remove(message.getKey());
                    log.info("Delete the key info is {} the value info is {}", message.getKey(), value);
                    sender().tell(value, self());
                })
                .match(SetIfNotExists.class, message -> {
                    log.info("Received get Request {}", message);
                    Object value = map.get(message.getKey());
                    if (Objects.isNull(value)) {
                        map.put(message.getKey(), message.getValue());
                        value = message.getValue();
                    }
                    sender().tell(value, self());
                })
                .matchAny(message -> {
                    ask(context().self(), new Status.Failure(new ClassNotFoundException()), 1000L);
                }).build();
    }
}
