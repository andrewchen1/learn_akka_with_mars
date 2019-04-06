package top.andrewchen1.paper4.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import top.andrewchen1.paper4.dto.CreateSequence;
import top.andrewchen1.paper4.dto.DropSequence;
import top.andrewchen1.paper4.dto.ListSequences;
import top.andrewchen1.paper4.dto.NextValue;
import top.andrewchen1.paper4.repository.SequenceRepository;

/**
 * @author dafuchen
 * 2019-03-26
 */
public class SequencesActor extends AbstractActor {
    public static Props props() {
        return Props.create(SequencesActor.class);
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(CreateSequence.class, msg -> {
                    sender().tell(SequenceRepository.createSequence(msg.getName()), self());
                }).match(NextValue.class, msg -> {
                    sender().tell(SequenceRepository.getNextValue(msg.getName()), self());
                }).match(ListSequences.class, msg -> {
                    sender().tell(SequenceRepository.listSequence(), self());
                }).match(DropSequence.class, msg -> {
                    sender().tell(SequenceRepository.dropSequence(msg.getName()),self());
                }).build();
    }
}
