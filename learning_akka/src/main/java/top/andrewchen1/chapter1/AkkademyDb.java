package top.andrewchen1.chapter1;
import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dafuchen
 * 2019-03-10
 */
public class AkkademyDb extends AbstractActor {
    private  final LoggingAdapter log = Logging.getLogger(context().system(), this);
    private final Map<String, Object> map = new ConcurrentHashMap<>();

    /**
     * 不是很好的例子, 仅供演示
     * 在Actor里面的属性应该是 immutable 不可变的
     * @return
     */
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(SetRequest.class, message -> {
            log.info("received Set request {}", message);
            map.put(message.getKey(), message.getValue());
        }).matchAny( o -> {
            log.info("received unknown message {}", o);
        }).build();
    }

    public Object getValueByKey(String key) {
        return map.get(key);
    }
}
