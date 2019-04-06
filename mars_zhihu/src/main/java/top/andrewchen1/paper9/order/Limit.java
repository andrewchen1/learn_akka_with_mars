package top.andrewchen1.paper9.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author dafuchen
 * 2019-03-30
 */
public class Limit extends TradeOrder {
    /**
     * 单价
     */
    @Getter
    @Setter
    private BigDecimal price;

    public BigDecimal getAmount() {
        return price.multiply(BigDecimal.valueOf(getQuantity()));
    }
}
