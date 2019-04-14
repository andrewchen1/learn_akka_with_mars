package top.andrewchen1.paper5;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import top.andrewchen1.paper4.actor.SequencesActor;
import top.andrewchen1.paper4.dto.CreateSequence;
import top.andrewchen1.paper4.dto.DropSequence;
import top.andrewchen1.paper4.dto.NextValue;


import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dafuchen
 * 2019-04-13
 */
public class ClientTest {
    private static ActorSystem system;
    private static ActorRef server;
    private static final String LOCAL_PATH = "akka://test/user/sequences";

    /**
     * 在每个test结束之后都会run的部分
     * 得到system 和 actor的reference
     * @throws Exception
     */
    @BeforeClass
    public static void step() throws Exception {
        System.out.println("try to build sequence");
        system = ActorSystem.create("test");
        server = system.actorOf(SequencesActor.props(), "sequences");
        var message = new CreateSequence();
        message.setName("test");
        server.tell(message, server);
        Thread.sleep(1000);
    }

    /**
     * 在结束的时候要做的事情
     * 结束 akka的环境
     * @throws Exception
     */
    @AfterClass
    public static void teardown() throws Exception {
        System.out.println("try to drop sequence ");
        var message = new DropSequence();
        message.setName("test");
        server.tell(message, server);
        Thread.sleep(1000);
        TestKit.shutdownActorSystem(system);
        server = null;
        system = null;
    }

    @Test
    public void testNextValue() {
        /**
         * 重写 TestKit
         * 1 得到test sequence 目前的值 供后续做asset
         * 2 每次请求提供序号的 actor 把得到的序号 和 在step 1 中得到值进行对比
         * 3 testKit中线程 使用的线程只有1个 所有的步骤都是串行的
         */
        TestKit testKit = new TestKit(system) {{
            var lastId = new AtomicLong();
            var message = new NextValue();
            message.setName("test");
            ActorSelection selection = system.actorSelection(LOCAL_PATH);
            selection.tell(message, getRef());
            awaitCond(this::msgAvailable);
            expectMsgPF("init test data", msg -> {
                lastId.set((Long) msg);
                return msg;
            });

            within(Duration.ofSeconds(5), () -> {
                for (int i = 0; i < 5; i++) {
                    server.tell(message, getRef());
                    awaitCond(this::msgAvailable);
                    expectMsgPF("expect get next and next values", msg -> {
                        Assert.assertEquals(lastId.get() + 1, msg);
                        lastId.set((Long) msg);
                        return msg;
                    });
                }
                return null;
            });
        }};
    }

}
