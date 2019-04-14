package top.andrewchen1.chapter1.actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import top.andrewchen1.chapter1.model.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dafuchen
 * 2019-04-13
 */
public class TicketSellerActor extends AbstractActor {
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public Receive createReceive() {
        return null;
    }
}
