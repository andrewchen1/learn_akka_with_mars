package top.andrewchen1.paper10.model.dto;

import lombok.Data;
import top.andrewchen1.paper10.model.QueryOrderEventTypeEnum;
import top.andrewchen1.paper9.order.OrderTypeEnum;

/**
 * @author dafuchen
 * 2019-05-04
 */
@Data
public class PeekOrderDTO {
    private QueryOrderEventTypeEnum queryOrderEventType;

    private Long id;

    private OrderTypeEnum orderType;
}
