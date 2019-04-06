package top.andrewchen1.chapter4.fsm;

/**
 * @author dafuchen
 * 2019-03-23
 */
public enum Status {
    /**
     * 中断连接
     */
    DISCONNECTED,
    /**
     * 连接
     */
    CONNECTED,
    /**
     * 连接等待
     */
    CONNECTED_PENDING
}
