package org.noear.sponge.admin.dso.auth;

import org.noear.bcf.BcfClient;
import org.noear.bcf.BcfSessionBase;
import org.noear.solon.Solon;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.core.handle.Context;
import org.noear.solon.extend.auth.AuthProcessor;
import org.noear.solon.extend.auth.annotation.Logical;
import org.noear.sponge.admin.dso.Session;

/**
 * @author noear 2021/5/28 created
 */
public class AuthProcessorImpl implements AuthProcessor {
    private int puid() {
        return Session.current().getPUID();
    }

    @Override
    public boolean verifyIp(String ip) {
        if (Solon.cfg().isWhiteMode() && Solon.cfg().isFilesMode() == false) {
            return CloudClient.list().inListOfClientIp(ip);
        }

        int puid = puid();

        if (puid > 0) {
            Context ctx = Context.current();
            ctx.attrSet("user_puid", "" + puid);
            ctx.attrSet("user_name", BcfSessionBase.global().getUserName());
        }

        return true;
    }

    @Override
    public boolean verifyLogined() {
        return puid() > 0;
    }

    @Override
    public boolean verifyPath(String path, String method) {
        try {
            if (path.contains("@")) {
                return true;
            }

            if (BcfClient.hasUrlpath(path)) {
                return BcfClient.hasUrlpathByUser(puid(), path);
            }else{
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyPermissions(String[] permissions, Logical logical) {
        int puid = puid();

        try {
            if (logical == Logical.AND) {
                boolean isOk = true;

                for (String p : permissions) {
                    isOk = isOk && BcfClient.hasResourceByUser(puid, p);
                }

                return isOk;
            } else {
                for (String p : permissions) {
                    if (BcfClient.hasResourceByUser(puid, p)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyRoles(String[] roles, Logical logical) {
        //
        //bcf 的角色也是资源
        //
        int puid = puid();

        try {
            if (logical == Logical.AND) {
                boolean isOk = true;

                for (String p : roles) {
                    isOk = isOk && BcfClient.hasResourceByUser(puid, p);
                }

                return isOk;
            } else {
                for (String p : roles) {
                    if (BcfClient.hasResourceByUser(puid, p)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
