package org.noear.sponge.rockuapi.encoder;

import org.noear.sponge.rock.models.AppModel;
import org.noear.solon.core.handle.Context;
import org.noear.water.utils.EncryptUtils;

public class Md5Encoder implements Encoder {
    @Override
    public String tryEncode(Context context, AppModel app, String text) throws Exception {
        return EncryptUtils.md5(text);
    }
}