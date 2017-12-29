<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表系统录入</title>
</head>
<body>
 <div class="modal fade ng-scope" id="addReport" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">添加维度</h4>
                </div>
                <div class="modal-body">
                    <form role="form" id="formsavereport">
                        <div class="form-group">
                            <label for="dimensionName">维度中文名称</label> <input type="text"
                                class="form-control" name="dimensionName" id="dimensionName"
                                placeholder="请输入维度中文名称">
                        </div>
                        <div class="form-group">
                            <label for="localName">维度名称</label> <input type="text"
                                class="form-control" name="localName" id="localName"
                                placeholder="请输入维度名称">
                        </div>
                        <div class="form-group">
                            <label for="reportSQL">关联SQL语句</label><input type="text"
                                class="form-control" name="reportSQL" id="reportSQL"
                                placeholder="关联SQL语句">
                        </div>
                        <div class="form-group">
                            <label for="aliasNames">别名</label><input type="text"
                                class="form-control" name="aliasNames" id="aliasNames"
                                placeholder="请输入联系人所在部门">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="btnsavereport">保存</button>
                </div>
            </div>
        </div>
    </div>
    <div class="main-box clearfix">
        <div class="main-box-header clearfix">
            <h4>报表业务系统</h4>
        </div>
        <div class="main-box-body clearfix">
            <div class="table-responsive">
                <table class="table table-hover table-condensed">
                    <thead>
                        <tr>
                            <th class="text-center">维度中文名称</th>
                            <th class="text-center">维度名称</th>
                            <th class="text-center">关联SQL语句</th>
                            <th class="text-center">关联字段名</th>
                            <th class="text-center">关联字段别名</th>
                            <th class="text-center">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${reportList}" var="report">
                            <tr>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="dimensionName"
                                    data-url="sysconf/updatereport?id=${report.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${report.dimensionName}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="localName"
                                    data-url="sysconf/updatereport?id=${report.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${report.localName}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="reportSQL"
                                    data-url="sysconf/updatereport?id=${report.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${report.reportSQL}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="reportCols"
                                    data-url="sysconf/updatereport?id=${report.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${report.reportCols}</a></td>
                                 <td class="text-center"><a href="javascript:;"
                                    data-action="aliasNames"
                                    data-url="sysconf/updatereport?id=${report.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${report.aliasNames}</a></td>
                                <td class="text-center"><a id=${report.id } href="javascript:;"
                                    data-action="delete" data-toggle="tooltip" title="确定要删除？"
                                    class="table-link danger"> <span class="fa-stack"> <i
                                            class="fa fa-square fa-stack-2x"></i> <i
                                            class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                    </span>
                                </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="span12 text-center">
                <button class="btn btn-primary" data-toggle="modal" data-target="#addReport">
                    <i class="fa fa-plus-circle fa-lg"></i> 添加下游系统
                </button>
            </div>
        </div>
    </div>
    <script src="<c:url value="/resources/js/pages/reportlist.js"></c:url>"></script>
</body>
</html>