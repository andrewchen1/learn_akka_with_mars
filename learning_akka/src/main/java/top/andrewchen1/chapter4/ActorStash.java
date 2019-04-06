package top.andrewchen1.chapter4;

import akka.actor.AbstractActorWithStash;
import akka.japi.pf.ReceiveBuilder;
import top.andrewchen1.chapter2.db.GetRequest;
import top.andrewchen1.chapter4.dto.Connected;
import top.andrewchen1.chapter4.dto.Disconnected;
import top.andrewchen1.chapter4.dto.ProcessMessageUtil;

/**
 * @author dafuchen
 * 2019-03-23
 */
public class ActorStash extends AbstractActorWithStash {
    private Boolean online = false;

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(Connected.class, x-> {
                    online = true;
                    unstash();
                })
                .match(Disconnected.class, x -> {
                    online = false;
                })
                .match(GetRequest.class, x-> {
                    if(online) {
                        ProcessMessageUtil.processMessage(x);
                    } else {
                        stash();
                    }
                }).build();

    }
}
