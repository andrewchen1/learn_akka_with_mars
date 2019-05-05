package top.andrewchen1.paper11_status.repository;

import com.alibaba.fastjson.JSONObject;
import org.postgresql.util.PGobject;
import top.andrewchen1.postgre.PostgresqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author dafuchen
 * 2019-05-04
 */
public class StatusRepository {
    private static final String INSERT_SQL = "insert into status(meta, content) values(?, ?)";

    private static final String QUERY_LATEST_SQL = "select * from status order by id desc limit 1";

    private static final String QUERY_LATEST_WITH_SYMBOL = "select * from status where content ->>'symbol' = ? order by id" +
            "desc limit 1";

    public static Boolean save(JSONObject meta, JSONObject content) throws Exception {
        Connection connection = PostgresqlConnection.getInstance().getConnection();
        PGobject metaPGObject = new PGobject();
        metaPGObject.setType("json");
        metaPGObject.setValue(meta.toJSONString());

        PGobject contentPGObject = new PGobject();
        contentPGObject.setType("json");
        contentPGObject.setValue(content.toJSONString());

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
        preparedStatement.setObject(1, metaPGObject);
        preparedStatement.setObject(2, contentPGObject);

        return preparedStatement.execute();
    }

    public static Boolean latest(JSONObject meta, JSONObject content) throws Exception {
        Connection connection = PostgresqlConnection.getInstance().getConnection();
        return null;
    }
}
