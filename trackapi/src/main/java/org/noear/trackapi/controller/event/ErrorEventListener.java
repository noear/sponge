package org.noear.trackapi.controller.event;

import org.noear.snack.ONode;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.event.EventListener;
import org.noear.solon.core.handle.Context;
import org.noear.solon.logging.utils.TagsMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ErrorEventListener implements EventListener<Throwable> {
    static Logger log = LoggerFactory.getLogger(ErrorEventListener.class);

    @Override
    public void onEvent(Throwable err) {
        Context ctx = Context.current();

        if (ctx != null) {
            TagsMDC.tag0(ctx.path());
            log.error("> Body: {}\r\n{}", ONode.stringify(ctx.paramMap()), err);
        } else {
            TagsMDC.tag0("global");
            log.error("{}", err);
        }
    }
}
