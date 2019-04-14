package top.andrewchen1.paper4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import top.andrewchen1.paper4.actor.SequencesActor;

/**
 * @author dafuchen
 * 2019-04-13
 */
public class PostgresqlMain {
    public static void main(String[] args) {
        Config config = ConfigFactory.load("application-4");
        ActorSystem actorSystem = ActorSystem.create("postgresql", config);
        ActorRef sequence = actorSystem.actorOf(SequencesActor.props(), "sequences");
    }
}
