package org.noear.rock;

import org.noear.nami.Nami;
import org.noear.rock.protocol.RockRpc;
import org.noear.rock.model.*;
import org.noear.solon.Utils;
import org.noear.water.WW;
import org.noear.water.WaterClient;
import org.noear.wood.cache.EmptyCache;
import org.noear.wood.cache.ICacheServiceEx;
import org.noear.wood.cache.LocalCache;

import java.sql.SQLException;
import java.util.List;

public class RockClient {
    private final static String _lock = "";
    private static ICacheServiceEx cacheLocal = new LocalCache(5);
    private static RockRpc _instance;

    public static void enableCacheLocal(boolean enable) {
        if (enable) {
            if (cacheLocal instanceof LocalCache == false) {
                cacheLocal = new LocalCache(5);
            }
        } else {
            if (cacheLocal instanceof EmptyCache == false) {
                cacheLocal = new EmptyCache();
            }
        }
    }

    public static RockRpc instance() {
        if (_instance == null) {
            synchronized (_lock) {
                if (_instance == null) {
                    _instance = Nami.builder().filterAdd(inv -> {
                        inv.headers.put(WW.http_header_trace, WaterClient.waterTraceId());
                        inv.headers.put(WW.http_header_from, WaterClient.localServiceHost());

                        return inv.invoke();
                    }).create(RockRpc.class);

                    AppModel.rockclient = _instance;
                }
            }
        }

        return _instance;
    }


    /**
     * 添加一个应用模型
     */
    public static AppModel addApp(int agroupID, int ugroupID, String name) throws SQLException {
        return instance().addApp(agroupID, ugroupID, name);
    }

    /**
     * 更新应用的名称
     */
    public static void udpAppName(int appID, String name) throws SQLException {
        instance().udpAppName(appID, name);
    }

    /**
     * 更新应用的审核状态
     */
    public static void udpAppExamine(int appID, int isExamine, int examineVer) throws SQLException {
        instance().udpAppExamine(appID, isExamine, examineVer);
    }

    /**
     * 获取一个应用模型
     */
    public static AppModel getAppByID(int appID) throws SQLException {
        if (appID < 1) {
            throw new RuntimeException("请输入有效的 appID");
        }

        return cacheLocal.getBy("getApp_" + appID, (ru) -> instance().getAppByID(appID));
    }

    //获取一个应用模型
    public static AppModel getAppByKey(String appKey) throws SQLException {
        if (Utils.isEmpty(appKey)) {
            throw new RuntimeException("请输入有效的 appKey");
        }

        return cacheLocal.getBy("getApp_" + appKey, (ru) -> instance().getAppByKey(appKey));
    }

    //可以只输入一个分组
    public static List<AppModel> getAppsByGroup(int agroupID, int ugroupID) throws Exception {
        if (agroupID < 1 && ugroupID < 1) {
            throw new RuntimeException("请输入有效的 agroupID 或 ugroupID");
        }

        return instance().getAppsByGroup(agroupID, ugroupID);
    }

    //获取一个应用组模型
    public static AppGroupModel getAppGroup(int agroupID) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroupID");
        }

        return cacheLocal.getBy("getAppGroup_" + agroupID, (ru) -> instance().getAppGroup(agroupID));
    }

    //获取一个用户组模型
    public static UserGroupModel getUserGroup(int ugroupID) throws Exception {
        if (ugroupID < 1) {
            throw new Exception("请输入有效的 ugroupID");
        }

        return cacheLocal.getBy("getUserGroup_" + ugroupID, (ru) -> instance().getUserGroup(ugroupID));
    }

    //=======================
    //获取一个应用设置集
    public static AppSettingCollection getAppSetting(int appID, int verStart, boolean isClientOnly) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 app_id");
        }

        return cacheLocal.getBy("getAppSetting_" + appID + "_" + verStart + "_" + isClientOnly, (ru) ->
                instance().getAppSetting(appID, verStart, isClientOnly));

    }

    /**
     * 获取一个应用设置项
     */
    public static AppSettingModel getAppSettingItem(int appID, String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        return cacheLocal.getBy("getAppSettingItem_" + appID + "_" + name, (ru) ->
                instance().getAppSettingItem(appID, name));
    }

    public static AppSettingModel getAppSettingItemNoCache(int appID, String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        return instance().getAppSettingItemNoCache(appID, name);
    }

    /**
     * 仅用于管理
     */
    public static boolean delAppSettingItem(int appID, String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        return instance().delAppSettingItem(appID, name);
    }

    /**
     * 获取应用设置项（已包函时间）
     */
    public static AppSettingCollection getAppSettingEx(int appID, int ver, boolean isClientOnly) throws Exception {
        return cacheLocal.getBy("getAppSettingEx_" + appID + "_" + ver + "_" + isClientOnly, (ru) ->
                instance().getAppSettingEx(appID, ver, isClientOnly));
    }


    public static AppSettingCollection getAppSettingEx2(int groupID, int appID, int ver, boolean isClientOnly) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        return cacheLocal.getBy("getAppSettingEx2_" + groupID + "_" + appID + "_" + ver + "_" + isClientOnly, (ru) ->
                instance().getAppSettingEx2(groupID, appID, ver, isClientOnly));
    }

    public static List<AppSettingModel> getAppSettingItemsByName(int agroupID, String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroupID");
        }

        return cacheLocal.getBy("getAppSettingItemsByName_" + agroupID + "_" + name, (ru) ->
                instance().getAppSettingItemsByName(agroupID, name));
    }

    /**
     * 获取一个应用设置项（如果没有去app_group_setting获取）
     */
    public static AppSettingModel getAppSettingItemEx(int appID, String name) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        return cacheLocal.getBy("getAppSettingItemEx_" + appID + "_" + name, (ru) ->
                instance().getAppSettingItemEx(appID, name));
    }


    /**
     * 设置一个应用设置项的值
     */
    public static void setAppSettingItem(int appID, String name, int type, String value, int verStart, boolean isClient) throws Exception {
        if (appID < 1) {
            throw new Exception("请输入有效的 appID");
        }

        instance().setAppSettingItem(appID, name, type, value, verStart, isClient);
    }


    /**
     * 获取一个应用组设置集
     */
    public static AppSettingCollection getAppGroupSetting(int agroupID, int verStart, boolean isClientOnly) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        return cacheLocal.getBy("getAppGroupSetting_" + agroupID + "_" + verStart + "_" + isClientOnly, (ru) ->
                instance().getAppGroupSetting(agroupID, verStart, isClientOnly));
    }

    /**
     * 获取一个应用组的设置项
     */
    public static AppSettingModel getAppGroupSettingItem(int agroupID, String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        return cacheLocal.getBy("getAppGroupSettingItem_" + agroupID + "_" + name, (ru) ->
                instance().getAppGroupSettingItem(agroupID, name));
    }

    public static AppSettingModel getAppGroupSettingItemNoCache(int agroupID, String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        return instance().getAppGroupSettingItemNoCache(agroupID, name);
    }


    /**
     * 仅用于管理
     */
    public static boolean delAppGroupSettingItem(int agroupID, String name) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroupID");
        }

        return instance().delAppGroupSettingItem(agroupID, name);
    }


    /**
     * 设置一个应用组的设置项的值
     */
    public static void setAppGroupSettingItem(int agroupID, String name, int type, String value, int verStart, boolean isClient) throws Exception {
        if (agroupID < 1) {
            throw new Exception("请输入有效的 agroup_id");
        }

        instance().setAppGroupSettingItem(agroupID, name, type, value, verStart, isClient);
    }

    //=======================

    /**
     * 检查应用版本更新
     */
    public static AppUpdateModel chkAppUpdate(int appID, int platform, int verID) throws SQLException {
        return cacheLocal.getBy("chkAppUpdate_" + appID + "_" + platform + "_" + verID, (ru) ->
                instance().chkAppUpdate(appID, platform, verID));
    }

    /**
     * 获取一个应用版本
     */
    public static AppVersionModel getAppVersionEx(int appID, int platform) throws SQLException {
        return cacheLocal.getBy("getAppVersionEx_" + appID + "_" + platform, (ru) ->
                instance().getAppVersionEx(appID, platform));
    }

    public static AppVersionModel getAppGroupVersion(int agroupID, int platform) throws SQLException {
        return cacheLocal.getBy("getAppGroupVersion_" + agroupID + "_" + platform, (ru) ->
                instance().getAppGroupVersion(agroupID, platform));
    }

    public static AppVersionModel getAppVersion(int appID, int platform) throws SQLException {
        return cacheLocal.getBy("getAppVersion_" + appID + "_" + platform, (ru) ->
                instance().getAppVersion(appID, platform));
    }


    //=======================

    /**
     * 获取接口状态码
     */
    @Deprecated
    public static AppCodeCollection getAppCodes(int agroupID) throws SQLException {
        return cacheLocal.getBy("getAppCodes_" + agroupID, (ru) ->
                instance().getAppCodes(agroupID));
    }

    @Deprecated
    public static AppCodeCollection getAppCodes(int agroupID, String lang) throws SQLException {
        return cacheLocal.getBy("getAppCodes_" + agroupID + "_" + lang, (ru) ->
                instance().getAppCodesByLang(agroupID, lang));
    }

    /**
     * 获取一个Api Code 的描述(无异常)
     */
    public static String tryAppCode(int agroupID, int code) {
        try {
            return getAppCode(agroupID, code);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String tryAppCode(int agroupID, int code, String lang) {
        try {
            return getAppCode(agroupID, code, lang);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 获取一个Api Code 的描述
     */
    public static String getAppCode(int agroupID, int code) throws SQLException {
        return cacheLocal.getBy("getAppCode_" + agroupID + "_" + code, (ru) ->
                instance().getAppCode(agroupID, code));
    }

    public static String getAppCode(int agroupID, int code, String lang) throws SQLException {
        return cacheLocal.getBy("getAppCode_" + agroupID + "_" + code + "_" + lang, (ru) ->
                instance().getAppCodeByLang(agroupID, code, lang));
    }

    //=======================

    /**
     * 获取接口状态码
     */
    @Deprecated
    public static AppCodeCollection getServiceCodes(String service) throws SQLException {
        return getServiceCodesByLang(service, "");
    }
    @Deprecated
    public static AppCodeCollection getServiceCodesByLang(String service, String lang) throws SQLException {
        return cacheLocal.getBy("getServiceCodesByLang_" + service + "_" + lang, (ru) ->
                instance().getServiceCodesByLang(service, lang));
    }

    public static AppI18nCollection getServiceCodes2(String service) throws SQLException {
        return getServiceCodesByLang2(service, "");
    }

    public static AppI18nCollection getServiceCodesByLang2(String service, String lang) throws SQLException {
        return cacheLocal.getBy("getServiceCodesByLang_" + service + "_" + lang, (ru) ->
                instance().getServiceCodesByLang2(service, lang));
    }


    /**
     * 获取一个Api Code 的描述
     */
    public static String getServiceCode(String service, Integer code) throws SQLException {
        return getServiceCodeByLang(service, code, "");
    }

    public static String getServiceCodeByLang(String service, Integer code, String lang) throws SQLException {
        return cacheLocal.getBy("getServiceCodeByLang_" + service + "_" + code + "_" + lang, (ru) ->
                instance().getServiceCodeByLang(service, code, lang));
    }

    /**
     * 获取接口状态码
     */
    public static AppI18nCollection getServiceI18ns(String service) throws SQLException {
        return getServiceI18nsByLang(service, "");
    }

    public static AppI18nCollection getServiceI18nsByLang(String service, String lang) throws SQLException {
        return cacheLocal.getBy("getServiceI18nsByLang_" + service + "_" + lang, (ru) ->
                instance().getServiceI18nsByLang(service, lang));
    }

    /**
     * 获取一个Api Code 的描述
     */
    public static String getServiceI18n(String service, String name) throws SQLException {
        return getServiceI18nByLang(service, name, "");
    }

    public static String getServiceI18nByLang(String service, String name, String lang) throws SQLException {
        return cacheLocal.getBy("getServiceI18nByLang_" + service + "_" + name + "_" + lang, (ru) ->
                instance().getServiceI18nByLang(service, name, lang));
    }
}
