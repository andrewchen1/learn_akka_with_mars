package top.andrewchen1.paper10.repository.impl;

import top.andrewchen1.paper10.repository.QueryOrder;
import top.andrewchen1.postgre.PostgresqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author dafuchen
 * 2019-05-04
 */
public class QueryCurrentOrder implements QueryOrder {
    @Override
    public ResultSet findOrder(Long queryId, String category) throws Exception {
        Connection connection = PostgresqlConnection.getInstance().getConnection();
        String sql = "select * from order_flow where id = ? ";
        PreparedStatement statement =connection.prepareStatement(sql);
        statement.setLong(1, queryId);

        ResultSet resultSet = statement.executeQuery();
        connection.close();
        return resultSet;
    }
}
