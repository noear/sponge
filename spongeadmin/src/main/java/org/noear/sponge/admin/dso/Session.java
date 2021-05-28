package org.noear.sponge.admin.dso;

import org.noear.bcf.BcfClient;
import org.noear.bcf.BcfSessionBase;
import org.noear.bcf.models.BcfUserModel;
import org.noear.solon.Solon;

public final class Session extends BcfSessionBase {
    private static final Session _current = new Session();
    public static Session current(){
        return _current;
    }


    @Override
    public String service() {
        return Solon.cfg().appName();
    }

    //////////////////////////////////
    //当前项目的扩展

    @Override
    public void loadModel(BcfUserModel user) throws Exception {
        setPUID(user.puid);
        setUserId(user.user_id);
        setUserName(user.cn_name);

        boolean is_admin = BcfClient.hasResourceByUser(user.puid, SessionRoles.role_admin);
        boolean is_operator = BcfClient.hasResourceByUser(user.puid, SessionRoles.role_operator);

        if (is_admin) {
            is_operator = true;
        }

        set(SessionRoles.role_admin, is_admin ? 1 : 0);
        set(SessionRoles.role_operator, is_operator ? 1 : 0);

        setIsAdmin(is_admin ? 1 : 0);
        setIsOperator(is_operator ? 1 : 0);
    }

    public final int getIsAdmin() {
        return get("Is_Admin", 0);
    }

    public final void setIsAdmin(int Is_Admin) {
        set("Is_Admin", Is_Admin);
    }

    public final int getIsOperator() {
        return get("Is_Operator", 0);
    }

    public final void setIsOperator(int is_Operator) {
        set("Is_Operator", is_Operator);
    }

    public final String getValidation() {
        return get("Validation_String", null);
    }

    public final void setValidation(String validation) {
        set("Validation_String", validation.toLowerCase());
    }
}
