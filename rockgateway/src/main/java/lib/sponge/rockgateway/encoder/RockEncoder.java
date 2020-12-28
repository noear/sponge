package lib.sponge.rockgateway.encoder;

import lib.sponge.rock.models.AppModel;
import org.noear.solon.core.handle.Context;

public interface RockEncoder {
    String tryEncode(Context context, AppModel app, String text) throws Exception;
}
