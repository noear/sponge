<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 应用设置</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script>
        function addAgroup() {
            location.href="/rock/apsets/add?app_id=${app_id}";
        }

        function imp(file) {
            if(confirm("确定要导入吗？") == false){
                return;
            }

            var fromData = new FormData();
            fromData.append("file", file);
            fromData.append("app_id","${app_id}");

            $.ajax({
                type:"POST",
                url:"./ajax/import",
                data:fromData,
                processData: false,
                contentType: false,
                success:function (data) {
                    if(data.code==1) {
                        top.layer.msg('操作成功');
                        setTimeout(function(){
                            location.reload();
                        },800);
                    }else{
                        top.layer.msg(data.msg);
                    }
                }
            });
        }

        function exp() {
            var vm = formToMap(".sel_from");
            if(!vm.sel_id){
                alert("请选择..");
                return;
            }

            window.open("./ajax/export?app_id=${app_id}&ids=" + vm.sel_id, "_blank");
        }

        $(function(){
            $('#sel_all').change(function(){
                var ckd= $(this).prop('checked');
                $('[name=sel_id]').prop('checked',ckd);
            });

            $("#imp_file").change(function () {
                imp(this.files[0]);
            })
        });
    </script>
    <style>
    </style>
</head>
<body>
<toolbar>
    <form>
        <input type="text" class="w350" value="${name}" name="name" id="name" value="${name}" placeholder="设置项名称"/>
        <input type="hidden"  name="app_id"  value="${app_id}"/>
        <button type="submit">查询</button>&nbsp;&nbsp;
        <c:if test="${app_id>0&&isOperator==1}">
            <button type="button" onclick="addAgroup()" class="edit">新增</button>
        </c:if>
        <c:if test="${agroup_id>0&&isOperator==1}">
            <div><file>
                <label><input id="imp_file" type="file" accept=".jsond"/><a class="btn minor w80">导入</a></label>
            </file>
                <button type='button' class="minor w80 mar10-l" onclick="exp('${app_id}')" >导出</button>
            </div>
        </c:if>
    </form>
</toolbar>

<datagrid>
    <table>
        <thead>
        <tr>
            <td width="20px"><checkbox><label><input type="checkbox" id="sel_all" /><a></a></label></checkbox></td>
            <td width="120px">设置项名称</td>
            <td width="60px">设置项<br/>值类型</td>
            <td>设置项值</td>
            <td width="70px">是否输出<br/>到客户端</td>
            <td width="70px">开始支持<br/>的版本</td>
            <c:if test="${isOperator==1}">
                <td width="50px">操作</td>
            </c:if>
        </tr>
        </thead>
        <tbody class="sel_from">
        <c:forEach var="apsets" items="${apsetsList}">
            <tr>
                <td><checkbox><label><input type="checkbox" name="sel_id" value="${apsets.row_id}" /><a></a></label></checkbox></td>
                <td style="word-break: break-all;word-wrap: break-word;text-align: left">${apsets.name}</td>
                <td>
                    <c:if test="${apsets.type==0}">文本</c:if>
                    <c:if test="${apsets.type==1}">数字</c:if>
                    <c:if test="${apsets.type==9}">JSON</c:if>
                </td>
                <td style="word-break: break-all;word-wrap: break-word;text-align: left">${apsets.value}
                    <c:if test="${apsets.note != null && apsets.note.length() > 0}">
                        <div>
                            <note>::${apsets.note}</note>
                        </div>
                    </c:if>
                </td>
                <td>${apsets.is_client}</td>
                <td>${apsets.ver_start}</td>
                <c:if test="${isOperator==1}">
                    <td><a href="/rock/apsets/edit?row_id=${apsets.row_id}&app_id=${apsets.app_id}" style="color: blue;">编辑</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</datagrid>
</body>
</html>