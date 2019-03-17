package top.andrewchen1.chapter2.db;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dafuchen
 * 2019-03-17
 */
@Data
public class SetRequest implements Serializable {

    private static final long serialVersionUID = 4807403519828200373L;

    private final String key;
    private final Object value;

    public SetRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
