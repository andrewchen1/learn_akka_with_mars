package top.andrewchen1.postgre;

import lombok.extern.java.Log;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;

/**
 * @author dafuchen
 * 2019-03-24
 */
@Log
public class PostgresqlConnection {
    private static PostgresqlConnection postgreConnection;
    private Properties properties;

    private PostgresqlConnection() {
        try {
            properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
        } catch(Exception e) {
            log.log(Level.INFO, e.getMessage());
        }
    }

    public static PostgresqlConnection getNewInstance() {
        if (Objects.isNull(postgreConnection)) {
            synchronized (PostgresqlConnection.class) {
                if (Objects.isNull(postgreConnection)) {
                    postgreConnection = new PostgresqlConnection();
                }
            }
        }
        return postgreConnection;
    }

    public Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        String url = properties.getProperty("jdbcUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

}
