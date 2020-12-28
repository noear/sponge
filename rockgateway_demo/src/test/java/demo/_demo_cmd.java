package demo;

import org.noear.sponge.rockgateway.RockGateway;
import org.noear.sponge.rockgateway.UapiCode;
import org.noear.sponge.rockgateway.decoder.RockXorDecoder;
import org.noear.sponge.rockgateway.encoder.RockSha1Encoder;
import org.noear.sponge.rockgateway.encoder.RockXorEncoder;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.sponge.rockgateway.interceptor.*;

@Mapping("/cmd/*")
@Controller
class _demo_cmd extends RockGateway {
    @Override
    public int agroup_id() {
        return 5;
    }

    @Override
    protected void register() {

        //
        //::执行前::
        //

        //开始计时
        before(new StartInterceptor());
        //构建参数
        before(new ParamsBuildInterceptor(new RockXorDecoder()));
        //签权
        before(new ParamsAuthInterceptor(new RockSha1Encoder()));

        //
        //::执行后::
        //

        //构建输出
        after(new OutputBuildInterceptor(new RockXorEncoder()));
        //签名
        after(new OutputSignInterceptor(new RockSha1Encoder())); //可选
        //输出
        after(new OutputInterceptor());
        //结束计时
        after(new EndInterceptor("{tag}"));


        add(CMD_0_0_1.class);
    }

    public static class CMD_0_0_1 {
        @Mapping
        public Object call() {
            return new UapiCode(0, "接口不存在");
        }
    }
}
