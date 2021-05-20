package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.noear.rock.RockClient;
import org.noear.solon.test.SolonJUnit4ClassRunner;

/**
 * @author noear 2021/5/20 created
 */
@RunWith(SolonJUnit4ClassRunner.class)
public class ApiTest {
    @Test
    public void test() throws Exception{
        assert  RockClient.getAppByID(4).app_id == 4;
        assert  RockClient.getAppGroup(4).agroup_id == 4;
        assert  RockClient.getAppSettingItemEx(4,"_img_pre_path").value != null;
        assert  RockClient.getAppSettingItem(4,"_img_pre_path").value == null;
        assert  RockClient.getAppGroupSettingItem(4,"_img_pre_path").value != null;

        assert  RockClient.getAppByKey("73f0759694a9441980562788a9e4256b").app_id == 4;
        assert  RockClient.getAppByKey("73f0759694a9441980562788a9e4256b").getSetting("_img_pre_path").value != null;
    }
}
