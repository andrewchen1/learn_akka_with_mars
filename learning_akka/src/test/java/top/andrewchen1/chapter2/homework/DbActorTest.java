package top.andrewchen1.chapter2.homework;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.Assert;
import org.junit.Test;
import top.andrewchen1.chapter2.actor.DbActor;
import top.andrewchen1.chapter2.db.Delete;
import top.andrewchen1.chapter2.db.GetRequest;
import top.andrewchen1.chapter2.db.KeyNotFoundException;
import top.andrewchen1.chapter2.db.SetIfNotExists;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

/**
 * @author dafuchen
 * 2019-03-17
 */

public class DbActorTest {
    private ActorSystem actorSystem = ActorSystem.create();
    private ActorRef actorRef = actorSystem.actorOf(Props.create(DbActor.class));

    @Test
    public void setIfAbsent() throws Exception {
        CompletionStage completionStage = toJava(ask(actorRef, new SetIfNotExists("123", "321"), 100L));
        CompletableFuture<String> future = (CompletableFuture<String>)completionStage;
        String value = future.get();
        Assert.assertEquals("321", value);

        completionStage = toJava(ask(actorRef, new SetIfNotExists("123", "123"), 100L));
        future = (CompletableFuture<String>)completionStage;
        value = future.get();
        Assert.assertEquals("321", value);

        completionStage = toJava(ask(actorRef, new Delete("123"), 100L));
        future = (CompletableFuture<String>)completionStage;
        value = future.get();
        Assert.assertEquals("321", value);


        completionStage = toJava(ask(actorRef, new GetRequest("123"), 100L));
        future = (CompletableFuture<String>)completionStage;
        future.get();
        future.exceptionally(x -> {
            Assert.assertEquals(x.getClass(), KeyNotFoundException.class);
            return null;
        });
    }
}
