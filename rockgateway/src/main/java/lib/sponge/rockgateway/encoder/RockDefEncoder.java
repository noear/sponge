package lib.sponge.rockgateway.encoder;

import lib.sponge.rock.models.AppModel;
import org.noear.solon.core.handle.Context;

public class RockDefEncoder implements  RockEncoder {
    @Override
    public String tryEncode(Context context, AppModel app, String text) throws Exception {
        return text;
    }
}
