package top.andrewchen1.paper9;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import top.andrewchen1.paper9.actor.OrderAction;

/**
 * @author dafuchen
 * 2019-04-13
 */
public class OrderMain {
    public static void main(String[] args) {
        Config config = ConfigFactory.load("application-9");
        ActorSystem actorSystem = ActorSystem.create("counter", config);
        String sequence = config.getString("order.address");
        actorSystem.actorOf(OrderAction.props(sequence), "order");
    }
}
