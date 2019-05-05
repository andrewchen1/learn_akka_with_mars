package top.andrewchen1.paper10.repository;

import com.alibaba.fastjson.JSONObject;
import top.andrewchen1.paper9.order.*;

import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 * @author dafuchen
 * 2019-05-04
 */
public class LoadOrderRepository {

    public static LimitAsk loadLimitAsk(Long queryId, QueryOrder queryOrder) throws Exception {
        ResultSet result = queryOrder.findOrder(queryId, OrderTypeEnum.LIMIT_ASK.toString());
        long id = result.getLong("id");
        BigDecimal price = result.getBigDecimal("price");
        long completed = result.getLong("completed");

        JSONObject content = result.getObject("content", JSONObject.class);
        String symbol = content.getString("symbol");
        long quantity = content.getLong("quantity");

        Long accountId = content.getLong("user_id");

        LimitAsk limitAsk = new LimitAsk();
        limitAsk.setId(id);
        limitAsk.setPrice(price);
        limitAsk.setSymbol(symbol);
        limitAsk.setQuantity(quantity);
        limitAsk.setCompleted(completed);
        limitAsk.setAccountId(accountId);
        return limitAsk;
    }

    public static LimitBid loadLimitbid(Long queryId, QueryOrder queryOrder) throws Exception {
        ResultSet result = queryOrder.findOrder(queryId, OrderTypeEnum.LIMIT_BID.toString());
        long id = result.getLong("id");
        BigDecimal price = result.getBigDecimal("price");

        JSONObject content = result.getObject("content", JSONObject.class);
        String symbol = content.getString("symbol");

        long quantity = content.getLong("quantity");
        long completed = result.getLong("completed");
        Long accountId = content.getLong("user_id");

        LimitBid limitBid = new LimitBid();
        limitBid.setId(id);
        limitBid.setPrice(price);
        limitBid.setSymbol(symbol);
        limitBid.setQuantity(quantity);
        limitBid.setCompleted(completed);
        limitBid.setAccountId(accountId);

        return limitBid;
    }

    public static MarketAsk loadMarketAsk(Long queryId, QueryOrder queryOrder) throws Exception {
        ResultSet result = queryOrder.findOrder(queryId, OrderTypeEnum.MARKET_ASK.toString());
        long id = result.getLong("id");

        JSONObject content = result.getObject("content", JSONObject.class);
        String symbol = content.getString("symbol");

        long quantity = content.getLong("quantity");
        long completed = result.getLong("completed");
        Long accountId = content.getLong("user_id");

        MarketAsk marketAsk = new MarketAsk();
        marketAsk.setId(id);
        marketAsk.setSymbol(symbol);
        marketAsk.setQuantity(quantity);
        marketAsk.setCompleted(completed);
        marketAsk.setAccountId(accountId);
        return marketAsk;
    }

    public static MarketBid loadMarketBid(Long queryId, QueryOrder queryOrder) throws Exception {
        ResultSet result = queryOrder.findOrder(queryId, OrderTypeEnum.MARKET_BID.toString());
        long id = result.getLong("id");

        JSONObject content = result.getObject("content", JSONObject.class);
        String symbol = content.getString("symbol");

        long quantity = content.getLong("quantity");
        long completed = result.getLong("completed");
        Long accountId = content.getLong("user_id");

        MarketBid marketBid = new MarketBid();
        marketBid.setId(id);
        marketBid.setSymbol(symbol);
        marketBid.setQuantity(quantity);
        marketBid.setCompleted(completed);
        marketBid.setAccountId(accountId);
        return marketBid;
    }

    public static Cancel  loadCancel(Long queryId, QueryOrder queryOrder) throws Exception {
        ResultSet result = queryOrder.findOrder(queryId, OrderTypeEnum.CANCEL.toString());
        long id = result.getLong("id");
        JSONObject content = result.getObject("content", JSONObject.class);
        String symbol = content.getString("symbol");

        Long accountId = content.getLong("user_id");

        Cancel cancel = new Cancel();
        cancel.setId(id);
        cancel.setSymbol(symbol);
        cancel.setAccountId(accountId);

        return cancel;
    }
}
