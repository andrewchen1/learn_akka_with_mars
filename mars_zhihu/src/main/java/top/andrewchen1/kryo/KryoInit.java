package top.andrewchen1.kryo;

import com.esotericsoftware.kryo.Kryo;

import java.util.ArrayList;

/**
 * @author dafuchen
 * 2019-03-30
 */
public class KryoInit {

    public void customize(Kryo kryo){
        kryo.register(top.andrewchen1.paper4.dto.CreateSequence.class);
        kryo.register(top.andrewchen1.paper4.dto.DropSequence.class);
        kryo.register(top.andrewchen1.paper4.dto.ListSequences.class);
        kryo.register(top.andrewchen1.paper4.dto.NextValue.class);
        kryo.register(top.andrewchen1.paper4.repository.SequenceRepository.class);
        kryo.register(top.andrewchen1.paper9.order.LimitAsk.class);
        kryo.register(top.andrewchen1.paper9.order.LimitBid.class);
        kryo.register(top.andrewchen1.paper9.order.MarketBid.class);
        kryo.register(top.andrewchen1.paper9.order.MarketAsk.class);
        kryo.register(top.andrewchen1.paper9.order.Cancel.class);
        kryo.register(ArrayList.class);
    }
}
