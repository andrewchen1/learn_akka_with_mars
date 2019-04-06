package top.andrewchen1.paper9.order;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dafuchen
 * 2019-03-30
 */
public class TradeOrder extends Order {
    /**
     * 总数量
     */
    @Getter
    @Setter
    private Long quantity;
    /**
     * 完成数量
     */
    @Setter
    @Getter
    private Long completed;

    public Long getSurplus() {
        return quantity - completed;
    }

    /**
     * 拍版成交
     */
    public Long knockdown(Long volume) {
        assert (getSurplus() >= volume);
        this.completed += volume;
        return getSurplus();
    }
}
