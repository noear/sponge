package webapp.controller.push;



import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.ModelAndView;
import webapp.controller.BaseController;
import webapp.controller.ViewModel;
import webapp.dso.IDUtil;
import webapp.dso.Session;
import webapp.dso.db.DbDoveApi;
import webapp.dso.db.DbRockApi;
import webapp.models.others.resp.BaseResp;
import webapp.models.rock.AppGroupModel;
import webapp.models.sponge.DoveSmsTemplateModel;

import java.sql.SQLException;
import java.util.List;

/**
 * 2018.07.26
 *
 * @author konar
 */

@Controller
@Mapping("push/template")
public class TemplateController extends BaseController {

    @Mapping("")
    public ModelAndView template() throws SQLException {

        // 有Tag的app_group
        List<AppGroupModel> agroups = DbRockApi.getAppGroupWithTag();
        viewModel.set("agroups", agroups);

        // 默认打开
        if (agroups.size() > 0) {
            viewModel.set("agroup_id", agroups.get(0).agroup_id);
        }

        return view("push/template");
    }

    @Mapping("inner/{agroup_id}")
    public ModelAndView template_inner( int agroup_id) throws SQLException {

        List<DoveSmsTemplateModel> tmps = DbDoveApi.getSmsTemplates(agroup_id);

        viewModel.set("tmps", tmps);

        viewModel.set("agroup_id", agroup_id);

        return view("push/template_inner");
    }

    @Mapping("add/{agroup_id}")
    public ModelAndView template_add( int agroup_id) {

        viewModel.set("agroup_id", agroup_id);

        viewModel.set("is_edit", false);

        return view("push/template_add");
    }

    @Mapping("edit/{template_id}")
    public ModelAndView template_edit( int template_id) throws SQLException {

        DoveSmsTemplateModel tmp = DbDoveApi.getSmsTemplate(template_id);

        viewModel.set("tmp", tmp);

        viewModel.set("agroup_id", tmp.agroup_id);

        viewModel.set("is_edit", true);

        return view("push/template_add");
    }

    @Mapping("ajax/save")
    public BaseResp template_save(DoveSmsTemplateModel tmp) {

        BaseResp resp = new BaseResp();

        resp.code = 1;
        resp.msg = "成功";

        try {

            if (0 == tmp.template_id) {

                tmp.template_id = IDUtil.buildDoveSmsTemplateID();
                tmp.operator = Session.current().getUserName();

                DbDoveApi.addSmsTemplate(tmp.template_id,
                        tmp.agroup_id,
                        tmp.name,
                        tmp.out_id,
                        tmp.content,
                        tmp.operator);

            } else {

                tmp.operator = Session.current().getUserName();

                DbDoveApi.modSmsTemplate(tmp.template_id,
                        tmp.name,
                        tmp.out_id,
                        tmp.content,
                        tmp.operator);

            }

        } catch (Exception e) {

            resp.code = 0;
            resp.msg = e.getLocalizedMessage();

        }

        return resp;

    }

    @Mapping("ajax/del")
    public ViewModel template_del(Integer template_id) throws SQLException{
        boolean result = DbDoveApi.delSmsTemplate(template_id);

        if (result) {
            viewModel.put("code",1);
            viewModel.put("msg","删除成功");
        } else {
            viewModel.put("code",0);
            viewModel.put("msg","删除失败");
        }

        return viewModel;
    }

}
