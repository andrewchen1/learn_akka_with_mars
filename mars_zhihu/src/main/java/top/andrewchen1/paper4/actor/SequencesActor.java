package top.andrewchen1.paper4.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.typesafe.sslconfig.util.LoggerFactory;
import top.andrewchen1.paper4.dto.CreateSequence;
import top.andrewchen1.paper4.dto.DropSequence;
import top.andrewchen1.paper4.dto.ListSequences;
import top.andrewchen1.paper4.dto.NextValue;
import top.andrewchen1.paper4.repository.SequenceRepository;

import java.util.Objects;

/**
 * @author dafuchen
 * 2019-03-26
 */
public class SequencesActor extends AbstractActor {
    private  LoggingAdapter log = Logging.getLogger(context().system(), this);
    public static Props props() {
        return Props.create(SequencesActor.class);
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(CreateSequence.class, msg -> {
                    log.info("create sequence the message is {}", msg);
                    try {
                        sender().tell(SequenceRepository.createSequence(msg.getName()), self());
                    } catch (Exception e) {
                        log.error("create sequence fail the {}", e);
                    }
                })
                .match(NextValue.class, msg -> {
                    log.info("next value the message is {}" , msg);
                    try {
                        sender().tell(SequenceRepository.getNextValue(msg.getName()), self());
                    } catch (Exception e) {
                        log.error("next value fail the {}", e);
                    }
                })
                .match(ListSequences.class, msg -> {
                    log.info("list sequence message is {}", msg);
                    try {
                        sender().tell(SequenceRepository.listSequence(), self());
                    } catch (Exception e) {
                        log.error("list sequence message is {}", e);
                    }
                })
                .match(DropSequence.class, msg -> {
                    log.info("drop sequence message is {}", msg);
                    try {
                        sender().tell(SequenceRepository.dropSequence(msg.getName()), self());
                    } catch (Exception e) {
                        log.error("drop sequence message is {}", e);
                    }
                }).matchAny(msg -> {
                    log.info("get other msg {}", msg);
                })
                .build();
    }
}
