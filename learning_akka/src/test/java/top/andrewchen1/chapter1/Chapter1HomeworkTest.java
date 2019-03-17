package top.andrewchen1.chapter1;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.junit.Assert;
import org.junit.Test;
import top.andrewchen1.chapter1.homework.Chapter1Homework;

/**
 * @author dafuchen
 * 2019-03-10
 */
public class Chapter1HomeworkTest {
    @Test
    public void chapter1HomeworkTest() {
        ActorSystem actorSystem = ActorSystem.create();
        TestActorRef<Chapter1Homework> chapter1HomeworkTestActorRef = TestActorRef.create(actorSystem,
                Props.create(Chapter1Homework.class));
        chapter1HomeworkTestActorRef.tell("hello", ActorRef.noSender());
        chapter1HomeworkTestActorRef.tell("world", ActorRef.noSender());
        Assert.assertTrue(chapter1HomeworkTestActorRef.underlyingActor().ifExists("hello"));
        Assert.assertTrue(chapter1HomeworkTestActorRef.underlyingActor().ifExists("world"));
    }
}
