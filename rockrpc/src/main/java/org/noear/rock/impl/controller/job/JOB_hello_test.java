package org.noear.rock.impl.controller.job;

import lombok.extern.slf4j.Slf4j;
import org.noear.solon.cloud.CloudJobHandler;
import org.noear.solon.cloud.annotation.CloudJob;
import org.noear.solon.core.handle.Context;

/**
 * @author noear 2021/5/26 created
 */
@Slf4j
@CloudJob(value = "hello_test", cron7x = "10s")
public class JOB_hello_test implements CloudJobHandler {
    @Override
    public void handle(Context ctx) throws Throwable {
        log.info("exe job: hello_test");
    }
}
