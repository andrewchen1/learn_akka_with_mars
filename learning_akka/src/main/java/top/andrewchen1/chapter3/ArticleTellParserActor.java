package top.andrewchen1.chapter3;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;
import akka.util.Timeout;
import top.andrewchen1.chapter2.db.GetRequest;
import top.andrewchen1.chapter2.db.SetRequest;

import java.util.concurrent.TimeoutException;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class ArticleTellParserActor extends AbstractActor {
    private final ActorSelection cacheActor;
    private final ActorSelection httpClientActor;
    private final ActorSelection articleParseActor;
    private final Timeout timeout;

    public ArticleTellParserActor (String cacheActor,
                             String httpClientActor,
                             String articleParseActor,
                             Timeout timeout) {
        this.cacheActor = context().actorSelection(cacheActor);
        this.httpClientActor = context().actorSelection(httpClientActor);
        this.articleParseActor = context().actorSelection(articleParseActor);
        this.timeout = timeout;
    }
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ParseArticle.class, message -> {
                    ActorRef extraActor = buildExtraActor(sender(), message.getUrl());
                    cacheActor.tell(new GetRequest(message.getUrl()), extraActor);
                    httpClientActor.tell(message.getUrl(), extraActor);
                    context().system().scheduler().scheduleOnce(
                            timeout.duration(),
                            extraActor,
                            "timeout",
                            context().system().dispatcher(),
                            ActorRef.noSender()
                    );
                })
                .build();
    }
    private ActorRef buildExtraActor(ActorRef senderRef, String uri) {
        class MyActor extends AbstractActor {

            @Override
            public Receive createReceive() {
                return ReceiveBuilder.create().matchEquals(String.class, x ->  x.equals("timeout"), x -> {
                    senderRef.tell(new Status.Failure(new TimeoutException("timeout")), self());
                    context().stop(self());
                })
                        .match(HttpResponse.class, httpResponse -> {
                            articleParseActor.tell(new ParseHtmlArticle(uri, httpResponse.getBody()), self());
                        }).match(String.class, body -> {
                            senderRef.tell(body, self());
                            context().stop(self());
                        }).match(ArticleBody.class, articleBody -> {
                            cacheActor.tell(new SetRequest(articleBody.getUri(), articleBody.getBody()), self());
                            context().stop(self());
                        }).matchAny(t ->
                                System.out.println("ignoring msg" + t.getClass()))
                        .build();
            }
        }
        return context().actorOf(Props.create(MyActor.class, MyActor::new));
    }
}
