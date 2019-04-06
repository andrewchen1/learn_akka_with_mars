package top.andrewchen1.paper9.counter.repository;

import com.alibaba.fastjson.JSONObject;
import org.postgresql.util.PGobject;
import top.andrewchen1.paper9.order.*;
import top.andrewchen1.postgre.PostgresqlConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * @author dafuchen
 * 2019-03-30
 */
public class OrderRepository {
    private static void save(TradeOrder tradeOrder, Supplier<BigDecimal> priceSupplier) throws Exception {
        Connection connection = PostgresqlConnection.getNewInstance().getConnection();
        String sql = "insert into `order_flow` (`id`, `price`, `content`) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection
                .prepareStatement(sql);
        BigDecimal price = priceSupplier.get();

        preparedStatement.setLong(1, tradeOrder.getId());
        preparedStatement.setBigDecimal(2, price);
        var content = new HashMap<String, Object>();
        content.put("category",tradeOrder.getCategory());
        content.put("accountId", tradeOrder.getAccountId());
        content.put("quantity", tradeOrder.getQuantity());
        content.put("completed", tradeOrder.getCategory());
        content.put("symbol", tradeOrder.getSymbol());

        PGobject pGobject = new PGobject();
        pGobject.setType("json");
        preparedStatement.setObject(3, JSONObject.toJSONString(content));

        preparedStatement.execute();
    }

    public static void placeOrder(LimitAsk limitAsk) throws Exception {
        save(limitAsk, limitAsk::getPrice);
    }

    public static void placeOrder(LimitBid limitBid) throws Exception {
        save(limitBid, limitBid::getPrice);
    }

    public static void placeOrder(MarketAsk marketAsk) throws Exception {
        save(marketAsk, () -> null);
    }

    public static void placeOrder(MarketBid marketBid) throws Exception {
        save(marketBid, () -> null);
    }

    public static void placeOrder(MarketCancel marketCancel) throws Exception {
        save(marketCancel, () -> null);
    }
}
