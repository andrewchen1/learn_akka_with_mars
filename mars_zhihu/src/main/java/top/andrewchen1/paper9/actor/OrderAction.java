package top.andrewchen1.paper9.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import akka.util.Timeout;
import scala.concurrent.Await;
import top.andrewchen1.paper4.dto.NextValue;
import top.andrewchen1.paper9.counter.repository.OrderRepository;
import top.andrewchen1.paper9.order.*;

import java.time.Duration;

import static akka.pattern.Patterns.ask;

/**
 * @author dafuchen
 * 2019-03-30
 */
public class OrderAction extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);
    private String url;
    private ActorSelection actorSelection;

    private OrderAction(String url) {
        this.url = url;
    }

    public static Props props(String url) {
        return Props.create(OrderAction.class, url);
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(LimitAsk.class, msg -> {
                    log.info("limitAsk the message is {}", msg);
                    try {
                        sender().tell(save(msg), self());
                    } catch (Exception e) {
                        log.error("limitAsk the error {}" , e);
                    }
                })
                .match(LimitBid.class, msg -> {
                    log.info("limitBid the message is {}", msg);
                    try {
                        sender().tell(save(msg), self());
                    } catch (Exception e) {
                        log.error("limitBid the error is {}", e);
                    }
                })
                .match(MarketAsk.class, msg -> {
                    log.info("MarketAsk the message is {}", msg);
                    try {
                        sender().tell(save(msg), self());
                    } catch (Exception e) {
                        log.error("MarketAsk the error is {}", e);
                    }
                })
                .match(MarketBid.class, msg -> {
                    log.info("MarketBid the message is {}", msg);
                    try {
                        sender().tell(save(msg), self());
                    } catch (Exception e) {
                        log.error("MarketBid the error is {}", e);
                    }
                })
                .match(MarketCancel.class, msg -> {
                    log.info("MarketCancel the message is {}", msg);
                    try {
                        sender().tell(save(msg), self());
                    } catch (Exception e) {
                        log.error("MarketCancel the error is {}", e);
                    }
                })
                .match(Cancel.class, msg -> {
                    log.info("Cancel the message is {}", msg);
                    try {
                        sender().tell(save(msg), self());
                    } catch (Exception e) {
                        log.error("Cancel the error is {}", e);
                    }
                }).matchAny(msg -> {
                    log.error("send unknown message {}", msg);
                })
                .build();
    }

    private Long save(TradeOrder order) throws Exception {
        NextValue nextValue = new NextValue();
        nextValue.setName("order_id");
        Timeout timeout = Timeout.create(Duration.ofSeconds(1));
        var future = ask(actorSelection, nextValue, timeout);
        Long sequence = (Long)Await.result(future, timeout.duration());
        order.setId(sequence);
        OrderRepository.save(order);
        return sequence;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        actorSelection = context().system().actorSelection(url);
    }
}
