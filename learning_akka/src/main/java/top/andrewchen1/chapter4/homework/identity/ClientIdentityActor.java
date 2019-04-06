package top.andrewchen1.chapter4.homework.identity;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.concurrent.Future;
import scala.runtime.BoxedUnit;
import top.andrewchen1.chapter4.dto.Disconnected;
import top.andrewchen1.chapter4.homework.Connected;
import top.andrewchen1.chapter4.homework.Ping;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;


/**
 * @author dafuchen
 * 2019-03-23
 */
public class ClientIdentityActor extends AbstractActorWithStash {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);
    private ActorRef serverRef;
    private Integer lostConnectionTime;
    private static final Integer MAX_FAIL_NUMBER = 2;

    public ClientIdentityActor() {
        context().system().scheduler().schedule(Duration.ofSeconds(0), Duration.ofSeconds(2),
                () -> {
                    try {
                        getServerRef();
                        lostConnectionTime = 0;
                    } catch (Exception e) {
                        lostConnectionTime ++;
                        log.error("error occurred while try to get heart beat to server {}" , e);
                    } finally {
                        if (lostConnectionTime >= MAX_FAIL_NUMBER) {
                            context().parent().tell(new Status.Failure(new ConnectedFailedException()), self());
                        }
                    }
                },
                context().system().dispatcher());
    }

    @Override
    public void preStart() {
        try {
            serverRef = getServerRef();
            log.info("get server succeed the info is {}", serverRef);
        } catch (Exception e) {
            log.info("the message is {}", e);
        }
    }
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(Ping.class, x-> {
                    serverRef = getServerRef();
                    serverRef.tell(new Ping(), self());
                })
                .match(Connected.class, x -> {
                    context().become(serverRef());
                })
                .match(Disconnected.class, x-> {
                    context().unbecome();
                })
                .build();
    }

     private PartialFunction<Object, BoxedUnit> serverRef() {
        return ReceiveBuilder.create()
                .match(Ping.class, x -> {
                    serverRef.tell(new Ping(), self());
                })
                .build().onMessage();
    }

    private ActorRef getServerRef() throws Exception {
        ActorSelection selection = context().system().actorSelection("akka.tcp://default@127.0.0.1:2552/user/serverIdentifyActor");
        Identify identify = new Identify(1);
        Future future = ask(selection, identify, 1000);
        CompletionStage<ActorIdentity> stage = toJava(future);
        CompletableFuture<ActorIdentity> completableFuture = (CompletableFuture<ActorIdentity>) stage;
        ActorIdentity actorIdentity = completableFuture.get();
        if (actorIdentity.getActorRef().isPresent()) {
            return actorIdentity.getActorRef().get();
        } else {
            throw new ConnectedFailedException();
        }
    }
}
