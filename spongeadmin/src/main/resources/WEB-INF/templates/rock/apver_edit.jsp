<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 版本发布编辑页</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="${js}/layer.js"></script>
    <script>
        $(function () {
            document.getElementById('type').value="${apver.type}";
            document.getElementById('platform').value="${apver.platform}";
        });
        var row_id = ${apver.row_id};
        var agroup_id = ${agroup_id};
        function saveEdit() {
            var app_id = $('#app_id').val();
            var ver = $('#ver').val();
            var content = $('#content').val();
            var type = $('#type').val();
            var platform = $('#platform').val();
            var url = $('#url').val();
            var is_disabled = $('#is_disabled').prop("checked") ? 1 : 0;

            if (!ver || ver == null) {
                top.layer.msg("版本号不能为空！");
                return;
            }

            if (row_id == null) {
                row_id = 0
            }

            var reg = "^[0-9]*$";
            var re = new RegExp(reg);
            if (re.test(app_id) && re.test(ver)) {
            } else {
                top.layer.msg('应用ID和版本号为纯数字！');
                return;
            }

            $.ajax({
                type: "POST",
                url: "/rock/apver/edit/ajax/save",
                data: {
                    "app_id": app_id,
                    "row_id": row_id,
                    "ver": ver,
                    "content": content, "type": type,
                    "alert_ver": $('#alert_ver').val(),
                    "force_ver": $('#force_ver').val(),
                    "platform": platform,
                    "url": url,
                    "is_disabled": is_disabled,
                    "agroup_id": agroup_id
                },
                success: function (data) {
                    if (data.code == 1) {
                        top.layer.msg(data.msg);
                        setTimeout(function () {
                            parent.location.href = "/rock/apver?agroup_id=" + agroup_id + "&_state=" + is_disabled;
                        }, 1000);
                    } else {
                        top.layer.msg(data.msg);
                    }
                }
            });
        }
    </script>
    <style>
    </style>
</head>
<body>
<toolbar class="blockquote">
    <left class="ln30">
        <h2><a onclick="history.back(-1)" href="#" class="noline">应用版本发布</a></h2> /  编辑
    </left>
</toolbar>

<detail>
    <form>
        <table>
            <tr>
                <th>应用ID</th>
                <td><input type="text" id="app_id" value="${apver.app_id}" placeholder="请输入应用ID"></td>
            </tr>
            <tr>
                <th>版本号</th>
                <td><input type="text" id="ver" value="${apver.ver}" placeholder="请输入版本号"></td>
            </tr>
            <tr>
                <th>更新内容</th>
                <td><textarea id="content">${apver.content}</textarea></td>
            </tr>
            <tr>
                <th>更新方式</th>
                <td>
                    <select id="type">
                        <option value="0">普通更新</option>
                        <option value="1">强制更新</option>
                    </select>（第三优先）
                </td>
            </tr>
            <tr>
                <th>定制更新</th>
                <td>
                    <input type="text" id="alert_ver" value="${apver.alert_ver}" class="txt"  placeholder="输入版本号"> 版本以下提示更新（第二优先；0表示忽略）
                    <br/>
                    <input style="margin-top: -1px;" value="${apver.force_ver}" type="text" id="force_ver" class="txt"  placeholder="输入版本号"> 版本以下强制更新（第一优先；0表示忽略）
                </td>
            </tr>
            <tr>
                <th>选择平台</th>
                <td>
                    <select id="platform">
                        <option value="1">IOS</option>
                        <option value="2">Android</option>
                        <option value="3">Web</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>更新地址</th>
                <td><input type="text" id="url" value="${apver.url}" class="longtxt" placeholder="请输入更新地址"></td>
            </tr>
            <tr>
                <th></th>
                <td>
                    <checkbox>
                        <label><input id="is_disabled" value="1" type="checkbox" ${apver.is_disabled == 1?"checked":""}><a>是否禁用</a></label>
                    </checkbox>
                </td>
            </tr>
            <tr>
                <th></th>
                <td><button type="button" onclick="saveEdit()">保存</button></td>
            </tr>
        </table>
    </form>
</detail>

</body>
</html>