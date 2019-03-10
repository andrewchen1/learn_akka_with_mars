package top.andrewchen1.chapter1;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.junit.Test;
import top.andrew1.chapter1.AkkademyDb;
import top.andrew1.chapter1.SetRequest;

import static org.junit.Assert.assertEquals;

/**
 * @author dafuchen
 * 2019-03-10
 */
public class AkkademyDbTest {
    ActorSystem actorSystem = ActorSystem.create();
    @Test
    public void akkademyTest() {
        TestActorRef<AkkademyDb> akkademyDbTestActorRef = TestActorRef
                .create(actorSystem, Props.create(AkkademyDb.class));
        akkademyDbTestActorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());
        assertEquals(akkademyDbTestActorRef.underlyingActor().getValueByKey("key"), "value");
    }
}
