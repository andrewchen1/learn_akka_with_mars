package top.andrewchen1.paper9.counter.repository;

import com.alibaba.fastjson.JSONObject;
import org.postgresql.util.PGobject;
import top.andrewchen1.paper9.order.*;
import top.andrewchen1.postgre.PostgresqlConnection;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author dafuchen
 * 2019-03-30
 */
public class OrderRepository {
    private static Boolean save(TradeOrder tradeOrder, BigDecimal price) throws Exception {
        Connection connection = PostgresqlConnection.getInstance().getConnection();
        String sql = "insert into order_flow (id, user_id, price, content) values (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection
                .prepareStatement(sql);

        preparedStatement.setLong(1, tradeOrder.getId());
        preparedStatement.setLong(2, tradeOrder.getAccountId());
        preparedStatement.setBigDecimal(3, price);
        var content = new HashMap<String, Object>();
        content.put("category",tradeOrder.getCategory());
        content.put("quantity", tradeOrder.getQuantity());
        content.put("completed", tradeOrder.getCategory());
        content.put("symbol", tradeOrder.getSymbol());

        PGobject pGobject = new PGobject();
        pGobject.setType("json");
        pGobject.setValue(JSONObject.toJSONString(content));
        preparedStatement.setObject(4, pGobject);

        boolean result = preparedStatement.execute();
        connection.close();
        return result;
    }

    public static Boolean save(TradeOrder order) throws Exception {
        BigDecimal price = null;
        for (Method method : order.getClass().getMethods()) {
            if (Objects.equals(method.getName(), "getPrice")) {
                price = (BigDecimal)method.invoke(order);
            }
        }
        return save(order, price);
    }
}
