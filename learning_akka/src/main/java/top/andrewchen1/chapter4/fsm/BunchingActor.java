package top.andrewchen1.chapter4.fsm;

import akka.actor.AbstractFSM;
import akka.actor.SupervisorStrategy;
import akka.io.Tcp;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import top.andrewchen1.chapter2.db.FlushMsg;
import top.andrewchen1.chapter2.db.GetRequest;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author dafuchen
 * 2019-03-23
 */
public class BunchingActor extends AbstractFSM<Status,
        LinkedBlockingQueue<GetRequest>> {
    public BunchingActor() {
        startWith(Status.DISCONNECTED, null);

        when(Status.DISCONNECTED,
                matchEvent(FlushMsg.class, (msg, container) -> stay())
                .event(GetRequest.class, (msg, container) -> {
                    container.add(msg);
                    return stay();
                })
                .event(Tcp.Connected.class, (msg, container) -> {
                    if (Objects.isNull(container.peek())) {
                        return goTo(Status.CONNECTED);
                    } else {
                        return goTo(Status.CONNECTED_PENDING);
                    }
                })
        );

        when(Status.CONNECTED,
                matchEvent(FlushMsg.class, (msg, container) -> stay())
                        .event(GetRequest.class, (msg, container) -> {
                            container.add(msg);
                            return goTo(Status.CONNECTED_PENDING);
                        })
                        .event(String.class, (msg, container) -> {
                            System.out.println(msg);
                            return stay();
                        }));

        when(Status.CONNECTED_PENDING,
                matchEvent(FlushMsg.class, (msg, container) -> {
                    container.clear();
                    return stay();
                }).event(GetRequest.class, (msg, container) -> {
                    container.add(msg);
                    return goTo(Status.CONNECTED_PENDING);
                }));
    }
}
