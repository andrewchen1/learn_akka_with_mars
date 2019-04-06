package top.andrewchen1.chapter4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;
import top.andrewchen1.chapter4.homework.ClientActor;
import top.andrewchen1.chapter4.homework.Ping;
import top.andrewchen1.chapter4.homework.ServerActor;

/**
 * @author dafuchen
 * 2019-03-23
 */
public class ServerClientTest {
    public static void main(String[] args) {
        Config config = ConfigFactory.load("application.conf");
        ActorSystem actorSystem = ActorSystem.create("server", config);
        ActorRef clientRef = actorSystem.actorOf(Props.create(ClientActor.class));
        ActorRef serverRef = actorSystem.actorOf(Props.create(ServerActor.class));
        clientRef.tell(new Ping(), serverRef);

    }
}
