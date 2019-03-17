package top.andrewchen1.chapter2.db;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;

import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class Client {
    private final ActorSelection actorSelection;

    public Client(String remoteAddress, Config config) {
        ActorSystem actorSystem = ActorSystem.create("LocalSystem", config);
        actorSelection = actorSystem.actorSelection("akka.tcp://akkademy@"+ remoteAddress + "/user/akkademy-db");
    }

    public Client(String remoteAddress) {
        ActorSystem actorSystem = ActorSystem.create("LocalSystem");
        actorSelection = actorSystem.actorSelection("akka.tcp://akkademy@"+ remoteAddress + "/user/akkademy-db");
    }

    public CompletionStage<Object> set(String key, Object value) {
        return toJava(ask(actorSelection, new SetRequest(key, value), 20000L));
    }

    public CompletionStage<Object> get(String key) {
        return toJava(ask(actorSelection, new GetRequest(key), 20000L));
    }
}
