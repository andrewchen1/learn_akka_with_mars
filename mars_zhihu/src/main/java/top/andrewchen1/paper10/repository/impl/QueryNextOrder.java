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
public class QueryNextOrder implements QueryOrder {
    @Override
    public ResultSet findOrder(Long id, String category) throws Exception {
        Connection connection = PostgresqlConnection.getInstance().getConnection();
        String sql = "select * from order_flow where id = ? and content::json ->> ? order by id limit 1";
        PreparedStatement statement =connection.prepareStatement(sql);
        statement.setLong(1, id);
        statement.setString(2, category);

        ResultSet resultSet = statement.executeQuery();
        connection.close();
        return resultSet;
    }
}
