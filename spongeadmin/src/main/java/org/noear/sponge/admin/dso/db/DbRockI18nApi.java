package org.noear.sponge.admin.dso.db;

import org.noear.rock.RockUtil;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.model.TagCountsModel;
import org.noear.sponge.admin.model.rock.AppExCodeModel;
import org.noear.sponge.admin.model.rock.AppExI18nModel;
import org.noear.water.utils.TextUtils;
import org.noear.weed.DbContext;
import org.noear.weed.DbTableQuery;

import javax.rmi.CORBA.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author noear 2021/2/24 created
 */
public class DbRockI18nApi {
    private static DbContext db() {
        return Config.sponge_rock;
    }

    //对api_code 根据agroup_id分组
    public static List<TagCountsModel> getApCodeCounts(int agroup_id) throws SQLException {
        return db().table("appx_ex_code")
                .whereEq("agroup_id", agroup_id)
                .groupBy("service")
                .selectList("service tag,count(*) counts", TagCountsModel.class);
    }

    //根据agroup_id获取列表。
    public static List<AppExCodeModel> getApcodeByService(String service, Integer code, String lang) throws SQLException {
        if (lang == null) {
            lang = "";
        }

        if (TextUtils.isEmpty(service)) {
            return new ArrayList<>();
        }

        return db().table("appx_ex_code")
                .whereEq("service", service)
                .andEq("lang", lang)
                .build((tb) -> {
                    if (code != null) {
                        tb.andEq("code", code);
                    }
                })
                .orderBy("code ASC")
                .select("*")
                .getList(new AppExCodeModel());
    }

    //根据agroup_id获取列表。
    public static List<TagCountsModel> getApcodeLangsByService(String service) throws SQLException {
        if (TextUtils.isEmpty(service)) {
            return new ArrayList<>();
        }

        return db().table("appx_ex_code")
                .whereEq("service", service)
                .groupBy("lang")
                .orderBy("lang ASC")
                .select("lang tag,count(*) counts")
                .getList(TagCountsModel.class);
    }

    //根据id获取对应状态码信息
    public static AppExCodeModel getApCodeById(Integer row_id) throws SQLException {
        return db().table("appx_ex_code")
                .where("row_id = ?", row_id)
                .select("*")
                .getItem(new AppExCodeModel());
    }

    public static boolean editApcode(Integer row_id, Integer agroup_id, String service, Integer code, String lang, String note) throws SQLException {
        DbTableQuery tb = db().table("appx_ex_code")
                .set("agroup_id", agroup_id)
                .set("code", code)
                .set("service", service)
                .set("lang", lang)
                .set("note", note);

        boolean isOk = true;
        if (row_id > 0) {
            isOk = tb.where("row_id = ?", row_id).update() > 0;
        } else {
            isOk = tb.insert() > 0;
        }

        if (isOk) {
            RockUtil.delCacheForCodes(agroup_id);
        }

        return isOk;
    }


    ///////////
    // i18n
    //////////


    //对api_code 根据agroup_id分组
    public static List<TagCountsModel> getApi18nCounts(int agroup_id) throws SQLException {
        return db().table("appx_ex_i18n")
                .whereEq("agroup_id", agroup_id)
                .groupBy("service")
                .selectList("service tag,count(*) counts", TagCountsModel.class);
    }

    //根据agroup_id获取列表。
    public static List<AppExI18nModel> getApi18nByService(String service, String name, String lang) throws SQLException {
        if (lang == null) {
            lang = "";
        }

        if (TextUtils.isEmpty(service)) {
            return new ArrayList<>();
        }

        return db().table("appx_ex_i18n")
                .whereEq("service", service)
                .andEq("lang", lang)
                .build((tb) -> {
                    if (name != null) {
                        tb.andEq("name", name);
                    }
                })
                .orderBy("name ASC")
                .select("*")
                .getList(new AppExI18nModel());
    }

    //根据agroup_id获取列表。
    public static List<TagCountsModel> getApi18nLangsByService(String service) throws SQLException {
        if (TextUtils.isEmpty(service)) {
            return new ArrayList<>();
        }

        return db().table("appx_ex_i18n")
                .whereEq("service", service)
                .groupBy("lang")
                .orderBy("lang ASC")
                .select("lang tag,count(*) counts")
                .getList(TagCountsModel.class);
    }

    //根据id获取对应状态码信息
    public static AppExI18nModel getApi18nById(Integer row_id) throws SQLException {
        return db().table("appx_ex_i18n")
                .where("row_id = ?", row_id)
                .select("*")
                .getItem(new AppExI18nModel());
    }

    public static boolean editApi18n(Integer row_id, Integer agroup_id, String service, String name, String lang, String note) throws SQLException {
        DbTableQuery tb = db().table("appx_ex_i18n")
                .set("agroup_id", agroup_id)
                .set("name", name)
                .set("service", service)
                .set("lang", lang)
                .set("note", note);

        boolean isOk = true;
        if (row_id > 0) {
            isOk = tb.where("row_id = ?", row_id).update() > 0;
        } else {
            isOk = tb.insert() > 0;
        }

        if (isOk) {
            RockUtil.delCacheForCodes(agroup_id);
        }

        return isOk;
    }

    public static void impApi18n(int agroup_id, String service, String name, String lang, String note) throws SQLException {
        db().table("appx_ex_i18n")
                .set("agroup_id", agroup_id)
                .set("name", name)
                .set("service", service)
                .set("lang", lang)
                .set("note", note)
                .upsertBy("agroup_id,service,name,lang");
    }
}