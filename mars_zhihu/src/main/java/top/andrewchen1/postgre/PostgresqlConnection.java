package top.andrewchen1.postgre;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.java.Log;

import java.sql.Connection;
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
    private DruidDataSource druidDataSource;
    private Properties properties;
    private static PostgresqlConnection postgresqlConnection;

    private PostgresqlConnection() {
        try {
            properties = new Properties();
            properties.load(Objects.requireNonNull(this.getClass().getClassLoader()
                    .getResourceAsStream("jdbc.properties")));

            druidDataSource = new DruidDataSource();
            String userName = properties.getProperty("username");
            String password = properties.getProperty("password");
            String url = properties.getProperty("jdbcUrl");
            druidDataSource.setUsername(userName);
            druidDataSource.setPassword(password);
            druidDataSource.setUrl(url);
            druidDataSource.setMaxActive(20);
            druidDataSource.setDefaultAutoCommit(true);
        } catch(Exception e) {
            log.log(Level.INFO, e.getMessage());
        }
    }

    public static PostgresqlConnection getInstance() {
        if (Objects.isNull(postgresqlConnection)) {
            synchronized (PostgresqlConnection.class) {
                if (Objects.isNull(postgresqlConnection)) {
                    postgresqlConnection = new PostgresqlConnection();
                }
            }
        }
        return postgresqlConnection;
    }

    /**
     * 为什么要使用连接池
     * 因为要缓慢劣化
     * 在没有使用连接池的时候
     * 直接连接数据库 然后就直接too many connection 了
     * 在使用连接池之后会等待而不是直接爆炸
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return druidDataSource.getConnection();
    }
}
