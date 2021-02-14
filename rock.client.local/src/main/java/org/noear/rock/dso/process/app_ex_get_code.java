package org.noear.rock.dso.process;

import org.noear.weed.DbContext;
import org.noear.weed.DbQueryProcedure;

/**
 * Created by xq on 2017/7/3.
 */
public class app_ex_get_code extends DbQueryProcedure {
    public app_ex_get_code(DbContext db) {
        super(db);

        lazyload(()->{
            //set("{colname}", ()->{popname});
            //
            set("@agroup_id", agroup_id);
            set("@code", code);

            sql("SELECT * from appx_ex_code WHERE agroup_id=@agroup_id AND code=@code");
        });
    }

    public int agroup_id;
    public int code;
}