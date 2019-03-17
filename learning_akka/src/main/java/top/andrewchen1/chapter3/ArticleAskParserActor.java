package top.andrewchen1.chapter3;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import akka.util.Timeout;
import top.andrewchen1.chapter2.db.GetRequest;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class ArticleAskParserActor extends AbstractActor {
    private final ActorSelection cacheActor;
    private final ActorSelection httpClientActor;
    private final ActorSelection articleParseActor;
    private final Timeout timeout;

    public ArticleAskParserActor(String cacheActor,
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
        final ActorRef senderRef = sender();

        return ReceiveBuilder.create().match(ParseArticle.class, msg -> {
            toJava(ask(cacheActor, new GetRequest(msg.getUrl()), timeout)).handle((x, t) ->
                    ! Objects.isNull(x)
                            ? CompletableFuture.completedFuture(x)
                            : toJava(ask(httpClientActor, msg.getUrl(), timeout))
                            .thenCompose(rawArticle -> toJava(ask(articleParseActor, new ParseHtmlArticle(msg.getUrl(),
                                    ((HttpResponse) rawArticle).getBody()), timeout)))
            ).handle((x, t) -> {
                if (Objects.isNull(x)) {
                    if (x instanceof ArticleBody) {
                        String body = ((ArticleBody) x).getBody();

                        cacheActor.tell(body, self());
                        senderRef.tell(body, self());
                    } else {
                        senderRef.tell(new Status.Failure((Throwable)t), self());
                        return null;
                    }
                }
                return null;
            });
        }).build();
    }
}
