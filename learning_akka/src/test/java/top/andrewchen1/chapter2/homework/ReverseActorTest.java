package top.andrewchen1.chapter2.homework;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class ReverseActorTest {
    private static ActorSystem actorSystem = ActorSystem.create();
    private static ActorRef actorRef  = actorSystem.actorOf(Props.create(ReverseActor.class));

    @Test
    public void succeedSendMessage() throws Exception{
        CompletionStage stage = toJava(ask(actorRef, "message", 1000L));
        CompletableFuture<String> future = (CompletableFuture<String>)stage;
        String result = future.get();
        Assert.assertEquals("egassem", result);
    }

    @Test(expected = ClassNotFoundException.class)
    public void failedSendMessage() throws Exception {
        CompletionStage stage = toJava(ask(actorRef, 7777L, 1000L));
        CompletableFuture<String> future = (CompletableFuture<String>)stage;
        future.get();
    }
 }
