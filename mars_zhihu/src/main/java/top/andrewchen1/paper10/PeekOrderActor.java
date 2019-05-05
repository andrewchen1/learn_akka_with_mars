package top.andrewchen1.paper10;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import top.andrewchen1.paper10.model.QueryOrderEventTypeEnum;
import top.andrewchen1.paper10.model.dto.PeekOrderDTO;
import top.andrewchen1.paper10.repository.LoadOrderRepository;
import top.andrewchen1.paper10.repository.QueryOrder;
import top.andrewchen1.paper9.order.OrderTypeEnum;
import top.andrewchen1.paper9.order.TradeOrder;

import java.util.concurrent.TimeoutException;

/**
 * @author dafuchen
 * 2019-05-04
 */
public class PeekOrderActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), LoggingAdapter.class);

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(PeekOrderDTO.class, msg -> {
            try {
                QueryOrderEventTypeEnum queryOrderEventType = msg.getQueryOrderEventType();
                String eventClassPath = queryOrderEventType.getClassPath();
                Class<QueryOrder> queryOrderClass = (Class<QueryOrder>)Class.forName(eventClassPath);
                QueryOrder queryOrder = queryOrderClass.getConstructor().newInstance();
                Long id = msg.getId();
                OrderTypeEnum orderType = msg.getOrderType();
                TradeOrder tradeOrder = null;

                switch (orderType) {
                    case LIMIT_ASK:
                        tradeOrder = LoadOrderRepository.loadLimitAsk(id, queryOrder);
                        break;
                    case LIMIT_BID:
                        tradeOrder = LoadOrderRepository.loadLimitbid(id, queryOrder);
                        break;
                    case MARKET_ASK:
                        tradeOrder = LoadOrderRepository.loadMarketAsk(id, queryOrder);
                        break;
                    case MARKET_BID:
                        tradeOrder = LoadOrderRepository.loadMarketBid(id, queryOrder);
                        break;
                    case CANCEL:
                        tradeOrder = LoadOrderRepository.loadCancel(id, queryOrder);
                        break;
                    default:
                        log.error("error occurred cannot recognized order type {}", orderType);
                        break;
                }
                sender().tell(tradeOrder, self());
            } catch (Exception e) {
                log.error("error occurred the message is {} the stack is {}", e.getMessage(),
                        e.getStackTrace());
                sender().tell(new Status.Failure(new TimeoutException(e.getMessage())), self());
            }
        }).build();
    }
}
