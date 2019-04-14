package top.andrewchen1.paper4;

import org.junit.Assert;
import org.junit.Test;
import top.andrewchen1.paper4.repository.SequenceRepository;

/**
 * @author dafuchen
 * 2019-03-26
 */
public class SequenceRepositoryTest {
    @Test
    public void createSequenceTest() throws Exception {
        Boolean result = SequenceRepository.createSequence("order_id");
    }

    @Test
    public void listSequenceTest() throws Exception {
        Assert.assertEquals("sequences", SequenceRepository.listSequence().get(0));
    }

    @Test
    public void nextValue()throws Exception {
        Assert.assertEquals(Long.valueOf(1), SequenceRepository.getNextValue("order_id"));
    }

    @Test
    public void dropSequence() throws Exception {
        SequenceRepository.dropSequence("orderid");
    }
}
