package top.andrewchen1.chapter2;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Assert;
import org.junit.Test;
import scala.concurrent.Future;
import top.andrewchen1.chapter2.JavaPongActor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;


/**
 * @author dafuchen
 * 2019-03-17
 */

public class PongActorTest {
    private static final ActorSystem actorSystem = ActorSystem.create();

    @Test
    public void shouldReplayToPingWithPong() throws Exception {
        ActorRef actorPing = actorSystem.actorOf(Props.create(JavaPongActor.class));
        Future future = ask(actorPing, "Ping", 1000);
        CompletionStage<String> completionStage = toJava(future);
        CompletableFuture<String> completableFuture = (CompletableFuture<String>) completionStage;
        Assert.assertEquals(completableFuture.get(1000L, TimeUnit.SECONDS), "Pong");
    }

    @Test(expected = ExecutionException.class)
    public void shouldReplayToUnknownMessage() throws Exception {
        ActorRef actorPing = actorSystem.actorOf(Props.create(JavaPongActor.class));
        Future sFuture = ask(actorPing, "unknow", 1000);
        CompletionStage<String> stage = toJava(sFuture);
        CompletableFuture<String> future = (CompletableFuture<String>) stage;
        String result = future.get(1000L, TimeUnit.SECONDS);

    }

    @Test
    public void printTo() throws Exception {
        ActorRef actorPing = actorSystem.actorOf(Props.create(JavaPongActor.class));
        Future future = ask(actorPing, "Ping", 1000);
        CompletionStage<String> completionStage = toJava(future);
        completionStage.thenAccept(System.out::println);
    }
}
