package top.andrewchen1.paper9.order;

import lombok.Data;

/**
 * @author dafuchen
 * 2019-03-30
 */
@Data
public class Order {
    /**
     * id编号
     */
    private Long id;
    /**
     * 账户编号
     */
    private Long accountId;
    /**
     * 标记
     */
    private String symbol;
    /**
     * 项目
     */
    private String category;
}
