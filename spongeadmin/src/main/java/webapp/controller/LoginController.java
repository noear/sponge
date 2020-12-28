package webapp.controller;

import org.noear.bcf.BcfClient;
import org.noear.bcf.BcfUtil;
import org.noear.bcf.models.BcfResourceModel;
import org.noear.bcf.models.BcfUserModel;

import org.noear.solon.annotation.Mapping;
import org.noear.solon.annotation.Controller;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.MethodType;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.water.utils.ImageUtils;
import org.noear.water.utils.RandomUtils;
import org.noear.water.utils.TextUtils;
import webapp.dso.Session;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuety on 14-9-10.
 */
 //非单例
@Controller
public class LoginController extends BaseController {

    @Mapping("login") //视图 返回
    public ModelAndView login(Context request) {
        //Config.regWater(request);

        return view("login");
    }

    @Mapping("/")
    public void index() throws Exception {
         redirect("/login");
    }
    //-----------------

    //ajax.path like "{view}/ajax/{cmd}"

    //$共享SESSOIN$::自动跳转
    @Mapping("/login/auto")
    public void login_auto() throws Exception {
        int puid = Session.current().getPUID();
        if (puid > 0) {
            String def_url = BcfClient.getUserFirstResource(puid).uri_path;
            if (TextUtils.isEmpty(def_url) == false) {
                redirect(def_url);
                return;
            }
        }

        redirect("/login");
    }

    @Mapping("/login/ajax/check")  // Map<,> 返回[json]  (ViewModel 是 Map<String,Object> 的子类)
    public ViewModel login_ajax_check(Context request,String userName, String passWord, String validationCode) throws Exception {

        //验证码检查
        if (!validationCode.toLowerCase().equals(Session.current().getValidation())) {
            return viewModel.set("code", 0).set("msg", "提示：验证码错误！");
        }

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
            return viewModel.set("code", 0).set("msg", "提示：请输入账号和密码！");
        }

        BcfUserModel user = BcfClient.login(userName, passWord);

        if (user.puid == 0)
            return viewModel.set("code", 0).set("msg", "提示：账号或密码不对！"); //set 直接返回；有利于设置后直接返回，不用另起一行
        else {
            Session.current().loadModel(user);

            //新方案 //xyj,20181120,(uadmin)
            BcfResourceModel res = BcfClient.getUserFirstResource(user.puid);
            String def_url = null;

            if (TextUtils.isEmpty(res.uri_path)) {
                def_url = "/track/tag/@" + res.cn_name;
            } else {
                def_url = BcfUtil.buildBcfUnipath(res);
            }

            return viewModel.set("code", 1)
                    .set("msg", "ok")
                    .set("url", def_url);

        }
    }

    /*
     * 获取验证码图片
     */
    @Mapping(value = "/login/validation/img", method = MethodType.GET, produces = "image/jpeg")
    public void getValidationImg(Context ctx) throws IOException {
        // 生成验证码存入session
        String code = RandomUtils.code(4);
        Session.current().setValidation(code);

        // 获取图片
        BufferedImage bufferedImage = ImageUtils.getValidationImage(code);

        //XContext resp = Session.getContext();
        // 禁止图像缓存
        ctx.headerSet("Pragma", "no-cache");
        ctx.headerSet("Cache-Control", "no-cache");
        ctx.headerSet("Expires", "0");

        //resp.contentType("image/jpeg");

        // 图像输出
        ImageIO.setUseCache(false);
        ImageIO.write(bufferedImage, "jpeg", ctx.outputStream());
    }

    @Mapping("/user/modifymm")
    public ModelAndView modifyPassword(){
        return view("passwordModify");
    }

    //确认修改密码
    @Mapping("/user/confirmModify")
    public Map<String,String> confirmModify(String newPass, String oldPass) throws SQLException{
        HashMap<String, String> result = new HashMap<>();
        int success = BcfClient.setUserPassword(Session.current().getUserId() + "", oldPass, newPass);
        //0:出错；1：旧密码不对；2：修改成功
        if(0 == success){
            result.put("code","0");
            result.put("msg","出错了");
        }
        if(1 == success){
            result.put("code","0");
            result.put("msg","旧密码不对");
        }
        if(2 == success){
            result.put("code","1");
            result.put("msg","修改成功");
        }
        return result;
    }
}
