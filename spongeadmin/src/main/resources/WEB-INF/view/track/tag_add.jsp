<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 跟踪管理-新建标签</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            $("input[name='date']").val(getQueryString("date") ? getQueryString("date") : "<% out.print(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); %>");
            $("input[name='key']").val(getQueryString("key"));
        });

        function save() {
            var tagName = $("#tagName").val();
            var tagHost = $("#tagHost").val();
            var agroup_id = ${agroup_id};
            var note = $("#note").val();

            if(agroup_id=='0'){
                top.layer.msg("请选择应用组");
                return;
            }

            var t_user_field = $("#t_user_field").val();
            var t_track_params = $("#t_track_params").val();
            var t_trans_params = $("#t_trans_params").val();
            var t_build_link = $("#t_build_link").val();
            if(tagName==null||tagName==undefined||tagName=="") {
                top.layer.msg("标签名不能为空");
            } else {
                $.ajax({
                    type:"POST",
                    url:"add/ajax/save",
                    data:{
                        "tagName":tagName,
                        "tagHost":tagHost,
                        "agroup_id":agroup_id,
                        "note":note,
                        "t_user_field":t_user_field,
                        "t_track_params":t_track_params,
                        "t_trans_params":t_trans_params,
                        "t_build_link":t_build_link
                    },
                    success: function(data){
                        if(data.code == 1){
                            top.layer.msg(data.msg);
                            location.href = "/track/tag/inner?agroup_id="+agroup_id;
                        } else {
                            top.layer.msg(data.msg)
                        }
                    }
                });
            }

        }
    </script>
</head>
<body>

<main>
        <detail><form>
            <h2>新建标签（<a class="t2" onclick="history.back(-1)">返回</a>）</h2>
            <hr/>
            <table>

                <tr>
                    <td>标签名称</td>
                    <td><input type="text" placeholder="请输入标签名称" id="tagName"/> *必填</td>
                </tr>
                <tr>
                    <td>标签主域</td>
                    <td><input type="text" placeholder="请输入标签主域" id="tagHost"/> 例：https://x.x.x</td>
                </tr>
                <tr>
                    <td>用户标识</td>
                    <td><input type="text" placeholder="请输入用户标识" id="t_user_field"/></td>
                </tr>
                <tr>
                    <td>跟踪参数</td>
                    <td><input type="text" placeholder="请输入跟踪参数" id="t_track_params"/> 例(c,f,ap,v)用逗号隔开；禁放无限值的参数</td>
                </tr>
                <tr>
                    <td>透传参数</td>
                    <td><input type="text" placeholder="请输入透传参数" id="t_trans_params"/> 例(c=c,ukey=uk)用逗号隔开</td>
                </tr>
                <tr>
                    <td>构建链接<br/></td>
                    <td><textarea  id="t_build_link" style="height: 80px;"></textarea> 例(网站::f=web;卡片::f=app)</td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td><input type="text" id="note" class="longtxt" /></td>
                </tr>
                <tr>
                    <td></td>
                    <td><button type="button" onclick="save()">保存</button></td>
                </tr>
            </table>
        </form></detail>
</main>

</body>
</html>