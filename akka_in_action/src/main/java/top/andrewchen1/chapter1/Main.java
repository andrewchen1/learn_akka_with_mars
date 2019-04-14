package top.andrewchen1.chapter1;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.ExecutionContextExecutor;

/**
 * @author dafuchen
 * 2019-04-13
 */
public class Main {
    public static void main(String[] args) {
        Config config = ConfigFactory.load();
        ActorSystem actorSystem = ActorSystem.create("booking", config);
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);

//        final Http http = Http.get(actorSystem).bindAndHandleAsync(
//                request -> {
//                    if (request.getUri().pa)
//                },
//                ConnectHttp.toHost("127.0.0.1", 8080),
//                materializer
//        );



        int port = config.getInt("http.port");
        String host = config.getString("http.host");
        ExecutionContextExecutor executor = actorSystem.dispatcher();

    }
}
