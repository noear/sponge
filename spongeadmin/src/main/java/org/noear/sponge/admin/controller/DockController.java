package org.noear.sponge.admin.controller;

import org.noear.bcf.BcfClient;
import org.noear.bcf.models.BcfResourceModel;

import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.water.utils.TextUtils;


import java.net.URLDecoder;

 //非单例
@Controller
public class DockController extends BaseController {
    //支持外部url
    @Mapping("/**/$*") //视图 返回
    public ModelAndView dock1(Context request) {
        String uri = request.url().toLowerCase();
        String query = request.uri().getQuery();

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

    //此处改过，xyj，201811(uadmin) //增加内部url支持
    @Mapping("/**/@*") //视图 返回
    public ModelAndView dock2(Context request) {
        String uri = request.path().toLowerCase();
        String query = request.uri().getQuery();

        String fun_name = uri.split("/@")[1];
        String fun_url = uri.split("/@")[0];

        String newUrl = fun_url;

        //传递参数
        if(TextUtils.isEmpty(query) == false) {
            if (newUrl.indexOf("?") > 0) {
                newUrl = newUrl + "&" + query;
            } else {
                newUrl = newUrl + "?" + query;
            }
        }

        try {
            viewModel.set("fun_name", URLDecoder.decode(fun_name, "utf-8"));
            viewModel.set("fun_url", newUrl);

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
