package top.andrewchen1.paper9;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import top.andrewchen1.paper4.actor.SequencesActor;
import top.andrewchen1.paper4.dto.CreateSequence;
import top.andrewchen1.paper4.dto.DropSequence;
import top.andrewchen1.paper4.dto.NextValue;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicLong;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;


/**
 * @author dafuchen
 * 2019-03-26
 */
public class ClientTest {
    private static ActorSystem actorSystem;
    private static ActorRef server;

    @BeforeClass
    public void setup() throws Exception {
        actorSystem = ActorSystem.create("test");
        server = actorSystem.actorOf(SequencesActor.props(), "sequences");
        var message = new CreateSequence();
        message.setName("test");
        CompletionStage stage = toJava(ask(server, message, 1000));
        CompletableFuture<Boolean> future = (CompletableFuture<Boolean>)stage;
        Assert.assertTrue(future.get());
    }

    @AfterClass
    public void teardown() throws Exception {
        var message = new DropSequence();
        message.setName("test");
        CompletionStage stage = toJava(ask(server, message, 1000));
        CompletableFuture<Boolean> future = (CompletableFuture<Boolean>)stage;
        Assert.assertTrue(future.get());
    }

    @Test
    public void testNextValue() {
        new TestKit(actorSystem) {{
            var lastId = new AtomicLong();
            ActorSelection actorSelection = actorSystem.actorSelection("akka://test/user/test/sequences");
            var message = new NextValue();
            message.setName("test");
            server.tell(message, getRef());
            awaitCond(this::msgAvailable);
            expectMsgPF("init test data", msg -> {
                lastId.set((Long) msg);
                return msg;
            });

            within(Duration.ofSeconds(5), () -> {
                for (int i = 0; i < 10; i++) {
                    server.tell(message, getRef());
                    awaitCond(this::msgAvailable);
                    expectMsgPF("expect get next and next values", msg -> {
                        Assert.assertEquals(lastId.get()+1, msg);
                        lastId.set((Long)msg);
                        return msg;
                    });
                }
                return null;
            });
        }};
    }
}
