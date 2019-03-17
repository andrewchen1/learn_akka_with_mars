package top.andrewchen1.chapter2.db;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dafuchen
 * 2019-03-17
 */
@Data
public class GetRequest implements Serializable {
    private static final long serialVersionUID = -2735666317780667925L;

    private final String key;

    public GetRequest(String key) {
        this.key = key;
    }
}
