package top.andrewchen1.chapter2.db;

import lombok.Data;

/**
 * @author dafuchen
 * 2019-03-17
 */
@Data
public class Delete {
    private final String key;

    public Delete(String key) {
        this.key = key;
    }
}
