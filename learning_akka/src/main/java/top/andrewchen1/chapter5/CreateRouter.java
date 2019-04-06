package top.andrewchen1.chapter5;

import akka.actor.AbstractActor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author dafuchen
 * 2019-04-06
 */
public class CreateRouter extends AbstractActor {

    @Override
    public Receive createReceive() {
        Executor executor = context().system().dispatchers().lookup("blocking-io-dispatcher");
        CompletableFuture<String> completableFuture = CompletableFuture
                .supplyAsync(() -> "analyse something", executor);
        return null;
    }
}
