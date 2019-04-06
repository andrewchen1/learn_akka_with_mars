package top.andrewchen1.paper4.repository;

import top.andrewchen1.postgre.PostgresqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dafuchen
 * 2019-03-24
 */
public class SequenceRepository {
    /**
     * 创建序列
     * @param sequenceName
     * @return
     * @throws SQLException
     */
    public static Boolean createSequence (String sequenceName) throws SQLException {
        Connection connection = PostgresqlConnection.getNewInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute("create sequence  " + sequenceName);
        return true;
    }

    public static List<String> listSequence() throws Exception {
        Connection connection = PostgresqlConnection.getNewInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select sequencename, COALESCE(last_value, start_value) as last_value from pg_sequences");
        List<String> sequenceList = new ArrayList<>();
        while (resultSet.next()) {
            String sequenceName = resultSet.getString(1);
            sequenceList.add(sequenceName);
        }
        return sequenceList;
    }

    public static Integer getNextValue(String sequenceName) throws SQLException {
        Connection connection = PostgresqlConnection.getNewInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select nextval(?)");
        preparedStatement.setString(1, sequenceName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public static Boolean dropSequence(String sequenceName) throws Exception{
        Connection connection = PostgresqlConnection.getNewInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.execute("drop sequence " + sequenceName);
        return true;
    }
}
