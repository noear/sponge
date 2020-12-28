package webapp.models.rock;

import lombok.Getter;
import org.noear.weed.*;

/// <summary>
/// 生成:2018/01/25 05:48:45
/// 
/// </summary>
@Getter
public class AppExCodeModel implements IBinder
{
    public int row_id;
    public int agroup_id;
    public int code;
    public String lang;
    public String note;

    public long counts;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
        row_id = s.get("row_id").value(0);
        agroup_id = s.get("agroup_id").value(0);
        code = s.get("code").value(0);
        lang = s.get("lang").value("");
        note = s.get("note").value(null);
        counts = s.get("counts").value(0L);
	}
	
	public IBinder clone()
	{
		return new AppExCodeModel();
	}
}