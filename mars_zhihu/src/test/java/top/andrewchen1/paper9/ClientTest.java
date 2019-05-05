package top.andrewchen1.paper9;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import top.andrewchen1.paper9.actor.OrderAction;
import top.andrewchen1.paper9.order.*;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author dafuchen
 * 2019-03-26
 */
public class ClientTest {
    private static ActorSystem system;
    private static Random random;
    private static String ORDER_PATH = "akka.tcp://test@127.0.0.1:2552/user/order";

    static {
        random = new Random();
    }

    @BeforeClass
    public static void setup() throws Exception {
        Config config = ConfigFactory.load("application-9");
        String sequencePath = config.getString("sequence.address");
        system = ActorSystem.create("test", config);
        system.actorOf(OrderAction.props(sequencePath), "order");
    }

    @AfterClass
    public static void teardown() throws Exception {
        TestKit.shutdownActorSystem(system);
    }

    @Test
    public void testBasicFlow() {
        new TestKit(system) {{
            ActorSelection remote = system.actorSelection(ORDER_PATH);
            AtomicLong lastId = new AtomicLong();
            remote.tell(randOrder(), getRef());
            awaitCond(this::msgAvailable);
            expectMsgPF("check first id and save it", msg -> {
                Assert.assertTrue(msg instanceof Long);
                lastId.set((Long) msg);
                return msg;
            });

            for (int i = 0; i < 100; i++) {
                remote.tell(randOrder(), getRef());
                awaitCond(this::msgAvailable);
                expectMsgPF(String.format("checkout id %d times", i), msg -> {
                    Assert.assertEquals(lastId.incrementAndGet(), msg);
                    return msg;
                });
            }
        }};
    }

    private Order randOrder() {
        int decide = random.nextInt(5);
        switch (decide) {
            case 0:
                return randLimitAsk();
            case 1:
                return randLimitBid();
            case 2:
                return randMarketAsk();
            case 3:
                return randMarketBid();
            case 4:
                return randCancel();
            default:
                return null;
        }
    }

    private LimitAsk randLimitAsk() {
        LimitAsk result = new LimitAsk();
        result.setSymbol("btcusdt");
        result.setAccountId(random.nextLong());
        result.setPrice(BigDecimal.valueOf(random.nextDouble()));
        result.setQuantity(random.nextLong());
        return result;
    }

    private LimitBid randLimitBid() {
        LimitBid result = new LimitBid();
        result.setSymbol("btcusdt");
        result.setAccountId(random.nextLong());
        result.setPrice(BigDecimal.valueOf(random.nextDouble()));
        result.setQuantity(random.nextLong());
        return result;
    }

    private MarketAsk randMarketAsk() {
        MarketAsk result = new MarketAsk();
        result.setSymbol("btcusdt");
        result.setAccountId(random.nextLong());
        result.setQuantity(random.nextLong());
        return result;
    }

    private MarketBid randMarketBid() {
        MarketBid result = new MarketBid();
        result.setSymbol("btcusdt");
        result.setAccountId(random.nextLong());
        result.setQuantity(random.nextLong());
        return result;
    }

    private Cancel randCancel(){
        Cancel result = new Cancel();
        result.setSymbol("btcusdt");
        result.setAccountId(random.nextLong());
        return result;
    }
}
