package lib.sponge.rockgateway.interceptor;

import lib.sponge.rockgateway.RockParams;
import lib.sponge.rockgateway.encoder.RockDefEncoder;
import lib.sponge.rockgateway.encoder.RockEncoder;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;

/** 输出签名拦截器（用于输出内容的签名） */
public class OutputSignInterceptor implements Handler {
    private RockEncoder _encoder;

    public OutputSignInterceptor(){
        _encoder = new RockDefEncoder();
    }

    public OutputSignInterceptor(RockEncoder encoder) {
        if (encoder == null) {
            _encoder = new RockDefEncoder();
        } else {
            _encoder = encoder;
        }
    }

    @Override
    public void handle(Context ctx) throws Throwable {
        /** 获取参数 */
        RockParams ctp = ctx.attr(Attrs.params);

        if(ctp == null){
            return;
        }

        String output = ctx.attr(Attrs.output, null);

        if (output != null) {
            String out_sign = _encoder.tryEncode(ctx, ctp.getApp(), output);
            ctx.headerSet(Attrs.sign, out_sign);
        }
    }
}
