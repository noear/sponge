package zm.data.dobbin.bull.controller.interceptor;

import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Handler;
import org.noear.sponge.rockgateway.RockParams;
import org.noear.sponge.rockgateway.interceptor.Attrs;
import zm.data.dobbin.bull.controller.UapiBase;
import zm.data.dobbin.bull.dso.Logger;

public class LogInterceptor implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        UapiBase api = UapiBase.current();

        RockParams params = api.params();
        Throwable error = api.error();

        String output = ctx.attr(Attrs.output);

        if (null != output) {
            Logger.logOutput(api, params, output);
        }

        if (null != error) {
            Logger.logError(api, params, error);
        }
    }
}
