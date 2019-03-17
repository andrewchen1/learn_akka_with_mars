package top.andrewchen1.chapter1.homework;
import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dafuchen
 * 2019-03-10
 */
public class Chapter1Homework extends AbstractActor {
    public static Set<String> message = new HashSet<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(String.class, (msg) ->  message.add(msg)).build();
    }

    public Boolean ifExists(String message) {
        return message.contains(message);
    }


}
