package top.andrewchen1.paper10.repository;

import java.sql.ResultSet;

/**
 * @author dafuchen
 * 2019-05-04
 */
public interface QueryOrder {
    ResultSet findOrder(Long id, String category) throws Exception;
}
