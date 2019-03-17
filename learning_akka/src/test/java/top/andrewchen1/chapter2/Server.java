package top.andrewchen1.chapter2;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import top.andrewchen1.chapter2.actor.DbActor;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class Server {
    public static void main(String[] args) {
        Config config = ConfigFactory.load("application.conf");
        ActorSystem actorSystem = ActorSystem.create("akkademy", config);
        actorSystem.actorOf(Props.create(DbActor.class), "akkademy-db");
    }
}
