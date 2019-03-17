package top.andrewchen1.chapter2.db;

import lombok.Data;

/**
 * @author dafuchen
 * 2019-03-17
 */
@Data
public class SetIfNotExists {
    private final String key;
    private final String value;
    public SetIfNotExists(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
