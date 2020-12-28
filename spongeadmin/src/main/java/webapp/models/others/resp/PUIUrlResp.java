package webapp.models.others.resp;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;
import webapp.models.sponge_track.ShortRedirectLog30dModel;

/**
 * @Author:Fei.chu
 * @Description:
 */
public class PUIUrlResp implements IBinder {

    public long url_id;
    public String url_name;
    public String url_partner_key;
    public int pv;
    public int uv;
    public int ip;

    public void bind(GetHandlerEx s)
    {
        //1.source:数据源
        //
        url_id = s.get("url_id").value(0L);
        url_name = s.get("url_name").value(null);
        url_partner_key = s.get("url_partner_key").value(null);
        pv = s.get("pv").value(0);
        uv = s.get("uv").value(0);
        ip = s.get("ip").value(0);
    }

    public IBinder clone()
    {
        return new PUIUrlResp();
    }
}
