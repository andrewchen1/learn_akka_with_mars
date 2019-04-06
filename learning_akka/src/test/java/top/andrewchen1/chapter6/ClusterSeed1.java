package top.andrewchen1.chapter6;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author dafuchen
 * 2019-04-06
 */
public class ClusterSeed1 {
    public static void main(String[] args) {
        Config config = ConfigFactory.load("application-cluster-1");
        ActorSystem actorSystem = ActorSystem.create("Akkademy", config);
        actorSystem.actorOf(Props.create(AkkaCluster.class));
    }
}
