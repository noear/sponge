package org.noear.rock.impl;

import org.noear.mlog.utils.Tags;
import org.noear.solon.Solon;
import org.noear.solon.cloud.CloudLogger;
import org.noear.solon.core.handle.Context;

public class App {
    public static void main(String[] args) {


        Solon.start(App.class, args, app -> {
            app.enableSafeStop(app.cfg().isFilesMode() == false);
        });

        CloudLogger log = CloudLogger.get("sponge_log_rock");

        Solon.global().onError((ex) -> {
            Context ctx = Context.current();

            if (ctx != null) {
                log.error(Tags.tag0(ctx.path()), "{}\n\n{}", ctx.paramMap().toString(), ex);
            } else {
                log.error(Tags.tag0("global"), ex);
            }
        });
    }
}
