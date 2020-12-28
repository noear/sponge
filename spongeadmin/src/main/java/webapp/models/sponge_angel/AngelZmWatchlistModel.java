package webapp.models.sponge_angel;

import lombok.Getter;
import org.noear.weed.*;
import java.util.*;

/// <summary>
/// 生成:2018/03/29 03:44:11
/// 
/// </summary>
@Getter
public class AngelZmWatchlistModel implements IBinder
{
    public long open_id;
    public int is_matched;
    public String details;
    public String biz_no;
    public Date create_fulltime;
    public int create_date;
    public Date update_fulltime;
    public int update_date;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        open_id = s.get("open_id").value(0L);
        is_matched = s.get("is_matched").value(0);
        details = s.get("details").value(null);
        biz_no = s.get("biz_no").value(null);
        create_fulltime = s.get("create_fulltime").value(null);
        create_date = s.get("create_date").value(0);
        update_fulltime = s.get("update_fulltime").value(null);
        update_date = s.get("update_date").value(0);
	}
	
	public IBinder clone()
	{
		return new AngelZmWatchlistModel();
	}
}