package top.andrewchen1.chapter2.db;

import java.io.Serializable;

/**
 * @author dafuchen
 * 2019-03-17
 */
public class KeyNotFoundException extends Exception implements Serializable {
    public final String key;

    public KeyNotFoundException(String key) {
        this.key = key;
    }
}
