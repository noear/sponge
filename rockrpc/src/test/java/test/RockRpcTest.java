package test;

import lib.sponge.rock.RockClient;
import lib.sponge.rock.models.AppModel;
import lib.sponge.rock.models.AppUpdateModel;
import lib.sponge.rock.protocol.RockRpc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.noear.snack.ONode;
import org.noear.solon.test.SolonJUnit4ClassRunner;
import org.noear.water.solon_plugin.XWaterUpstream;

@RunWith(SolonJUnit4ClassRunner.class)
public class RockRpcTest {

    @Test
    public void test0() throws Exception {
        RockRpc rpc = XWaterUpstream.xclient(RockRpc.class);
//        RockRpc rpc = Fairy.builder().server("http://12.12.1.1").create(RockRpc.class);

        rpc.getAppsByGroup(1, 1);

        AppModel app = rpc.getAppByID(48);

        assert (app != null && app.app_id > 0);
        System.out.println("RockClient.getApp(48)::成功!!!");


        int cont = XWaterUpstream.get("rockrpc").nodes().size();
        assert cont > 0;

        System.out.println("upstream>>" + cont);
    }

    @Test
    public void test1() throws Exception {
        AppModel app = RockClient.getApp(48);

        assert app != null;
        assert app.app_id == 48;


        System.out.println(ONode.stringify(app));

        Object obj = app.getClientSetting(1);

        assert obj != null;

        System.out.println(ONode.stringify(obj));
    }

    @Test
    public void test2() throws Exception {
        AppUpdateModel obj = RockClient.chkAppUpdate(1, 1, 1);

        System.out.println(obj);
    }

    @Test
    public void test3() throws Exception {
        String obj = RockClient.getAppCode(31, 1, "");

        System.out.println(obj);
    }
}