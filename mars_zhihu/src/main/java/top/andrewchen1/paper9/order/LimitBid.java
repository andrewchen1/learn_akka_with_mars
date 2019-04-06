package top.andrewchen1.paper9.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dafuchen
 * 2019-03-30
 */
public class LimitBid implements Limit{
    private long id;
    private BigDecimal price;
    private long quantity;
    private long completed;
    private long accountId;
    private String symbol;

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public BigDecimal getAmount() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public Long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public Long getCompleted() {
        return completed;
    }

    @Override
    public void setCompleted(Long completed) {
        this.completed = completed;
    }

    @Override
    public Long getSurplus() {
        return quantity - completed;
    }

    @Override
    public Long knockdown(Long volume) {
        assert(getSurplus() >= volume);
        this.completed += volume;
        return getSurplus();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public Map<String, Object> getContent() {
        var content = new HashMap<String, Object>();
        content.put("quantity", quantity);
        content.put("completed", completed);
        content.put("accountId", accountId);
        content.put("symbol", symbol);
        return content;
    }
}
