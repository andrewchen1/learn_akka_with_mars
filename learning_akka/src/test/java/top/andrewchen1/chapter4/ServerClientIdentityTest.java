package top.andrewchen1.chapter4;

import akka.actor.ActorSystem;
import akka.actor.Props;
import top.andrewchen1.chapter4.homework.identity.ClientIdentityActor;
import top.andrewchen1.chapter4.homework.identity.ServerIdentityActor;

/**
 * @author dafuchen
 * 2019-03-23
 */
public class ServerClientIdentityTest {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create();
        actorSystem.actorOf(Props.create(ServerIdentityActor.class), "serverIdentifyActor");
        actorSystem.actorOf(Props.create(ClientIdentityActor.class), "clientIdentifyActor");

    }
}
