package top.andrewchen1.chapter2.homework;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class ReverseActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(String.class, message -> {
                    int length = message.length();
                    char[] content = new char[length];
                    for (int i = length; i > 0 ; i--) {
                        content[length - i] = message.charAt(i - 1);
                    }
                    sender().tell(new String(content), self());
                }).matchAny(x -> sender().tell(new Status.Failure(new ClassNotFoundException()), self()))
                .build();
    }
}
