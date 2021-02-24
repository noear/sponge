package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.noear.rock.RockClient;
import org.noear.solon.test.SolonJUnit4ClassRunner;

import java.sql.SQLException;

/**
 * @author noear 2021/2/24 created
 */
@RunWith(SolonJUnit4ClassRunner.class)
public class RockI18nTest {
    @Test
    public void test0() throws SQLException {
        System.out.println(RockClient.getServiceCode("demoapi",200));
    }

    @Test
    public void test2() throws SQLException {
        System.out.println(RockClient.getServiceCodes("demoapi"));
    }
}
