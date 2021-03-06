package org.noear.sponge.admin.controller;

import org.noear.bcf.BcfClient;
import org.noear.bcf.models.BcfResourceModel;

import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.water.utils.TextUtils;


import java.net.URLDecoder;

@Controller
public class DockController extends BaseController {
    //支持外部url
    @Mapping("/**/$*") //视图 返回
    public ModelAndView dock1(Context ctx) {
        String uri = ctx.path().toLowerCase();
        String query = ctx.queryString();

        try {
            BcfResourceModel res = BcfClient.getResourceByPath(uri);
            viewModel.set("fun_name", res.cn_name);
            viewModel.set("fun_url", res.note);

            if (query != null && query.indexOf("@=") >= 0) {
                viewModel.set("fun_type", 1);
            } else {
                viewModel.set("fun_type", 0);
            }
        } catch (Exception ex) {

        }

        return view("dock");
    }

    //此处改过，noear，201811(uadmin) //增加内部url支持
    @Mapping("/**/@*") //视图 返回
    public ModelAndView dock2(Context ctx) {
        String uri = ctx.path();
        String query = ctx.queryString();

        String fun_name = uri.split("/@")[1];
        String fun_url = uri.split("/@")[0].toLowerCase();

        if(TextUtils.isEmpty(query)==false) {
            fun_url = fun_url + "?" + query;
        }

        try {
            viewModel.set("fun_name", URLDecoder.decode(fun_name, "utf-8"));
            viewModel.set("fun_url", fun_url);

            if (query != null && query.indexOf("@=") >= 0) {
                viewModel.set("fun_type", 1);
            } else {
                viewModel.set("fun_type", 0);
            }
        } catch (Exception ex) {

        }

        return view("dock");
    }
}
