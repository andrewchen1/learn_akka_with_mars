package top.andrewchen1.paper9.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.Props;
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
                    Long sequence = getSequence();
                    msg.setId(sequence);
                    OrderRepository.placeOrder(msg);
                    sender().tell(sequence, self());
                })
                .match(LimitBid.class, msg -> {
                    Long sequence = getSequence();
                    msg.setId(sequence);
                    OrderRepository.placeOrder(msg);
                    sender().tell(sequence, self());
                })
                .match(MarketAsk.class, msg -> {
                    Long sequence = getSequence();
                    msg.setId(sequence);
                    OrderRepository.placeOrder(msg);
                    sender().tell(sequence, self());
                })
                .match(MarketBid.class, msg -> {
                    Long sequence = getSequence();
                    msg.setId(sequence);
                    OrderRepository.placeOrder(msg);
                    sender().tell(sequence, self());
                })
                .match(MarketCancel.class, msg -> {
                    Long sequence = getSequence();
                    msg.setId(sequence);
                    OrderRepository.placeOrder(msg);
                    sender().tell(sequence, self());
                })
                .build();
    }

    private Long getSequence() throws Exception {
        NextValue nextValue = new NextValue();
        nextValue.setName("order_id");
        Timeout timeout = Timeout.create(Duration.ofSeconds(1));
        var future = ask(actorSelection, nextValue, timeout);
        return (Long)Await.result(future, timeout.duration());
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        actorSelection = context().system().actorSelection(url);
    }
}
