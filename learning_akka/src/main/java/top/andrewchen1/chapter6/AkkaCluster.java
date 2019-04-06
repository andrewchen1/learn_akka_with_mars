package top.andrewchen1.chapter6;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author dafuchen
 * 2019-04-06
 */
public class AkkaCluster extends AbstractActor {
    private  final LoggingAdapter log = Logging.getLogger(context().system(), this);
    private final Cluster cluster = Cluster.get(context().system());

    @Override
    public void preStart() {
        cluster.subscribe(self(),
                ClusterEvent.initialStateAsEvents(),
                ClusterEvent.MemberEvent.class,
                ClusterEvent.UnreachableMember.class);
    }

    @Override
    public void postStop() {
        cluster.unsubscribe(self());
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ClusterEvent.MemberEvent.class,
                        (msg) -> log.info("MemberEvent {}", msg))
                .match(ClusterEvent.UnreachableMember.class,
                        (msg) -> log.info("UnreachableMemberEvent {}", msg))
                .build();
    }
}
