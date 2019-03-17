package top.andrewchen1.chapter2;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Assert;
import org.junit.Test;
import top.andrewchen1.chapter2.db.Client;

import java.util.concurrent.CompletableFuture;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class ClientTest {
    @Test
    public void itShouldRecord() throws Exception {
        Config config = ConfigFactory.load("application-client1.conf");
        Client client = new Client("127.0.0.1:2552", config);
        client.set("123", 123);
        Integer result = (Integer) ((CompletableFuture<Object>) client.get("123")).get();
        Assert.assertEquals(result, Integer.valueOf(123));
    }
}
