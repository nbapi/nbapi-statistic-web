<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>系统录入</title>
</head>
<body>
    <div class="modal fade ng-scope" id="addSystem" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">添加下游业务系统</h4>
                </div>
                <div class="modal-body">
                    <form role="form" id="formsavesystem">
                        <div class="form-group">
                            <label for="systemName">下游系统名称</label> <input type="text"
                                class="form-control" name="systemName" id="systemName"
                                placeholder="请输入下游系统名称">
                        </div>
                        <div class="form-group">
                            <label for="businessLine">业务线</label> <input type="text"
                                class="form-control" name="businessLine" id="businessLine"
                                placeholder="请输入业务线名称">
                        </div>
                        <div class="form-group">
                            <label for="contactName">联系人</label><input type="text"
                                class="form-control" name="contactName" id="contactName"
                                placeholder="请输入联系人名称">
                        </div>
                        <div class="form-group">
                            <label for="department">部门</label><input type="text"
                                class="form-control" name="department" id="department"
                                placeholder="请输入联系人所在部门">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="btnsavesystem">保存</button>
                </div>
            </div>
        </div>
    </div>
    <div class="main-box clearfix">
        <div class="main-box-header clearfix">
            <h4>现有业务系统</h4>
        </div>
        <div class="main-box-body clearfix">
            <div class="table-responsive">
                <table class="table table-hover table-condensed">
                    <thead>
                        <tr>
                            <th class="text-center">下游系统</th>
                            <th class="text-center">业务线</th>
                            <th class="text-center">联系人</th>
                            <th class="text-center">部门</th>
                            <th class="text-center">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${systemList}" var="system">
                            <tr>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="systemName"
                                    data-url="sysconf/updatesys?id=${system.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${system.systemName}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="businessLine"
                                    data-url="sysconf/updatesys?id=${system.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${system.businessLine}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="contactName"
                                    data-url="sysconf/updatesys?id=${system.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${system.contactName}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="department"
                                    data-url="sysconf/updatesys?id=${system.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${system.department}</a></td>
                                <td class="text-center"><a id=${system.id } href="javascript:;"
                                    data-action="delete" data-toggle="tooltip" title="确定要删除？"
                                    class="table-link danger"> <span class="fa-stack"> <i
                                            class="fa fa-square fa-stack-2x"></i> <i
                                            class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                    </span>
                                </a><a data-toggle="tooltip" title="查看统计项"
                                    href='<c:url value='/sysconf/modulelist?id=${system.id}'></c:url>'
                                    class="dropdown-toggle"><span class="fa-stack"> <i
                                            class="fa fa-square fa-stack-2x"></i> <i
                                            class="fa fa-search-plus fa-stack-1x fa-inverse"></i>
                                    </span></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="span12 text-center">
                <button class="btn btn-primary" data-toggle="modal" data-target="#addSystem">
                    <i class="fa fa-plus-circle fa-lg"></i> 添加下游系统
                </button>
            </div>
        </div>
    </div>
    <script src="<c:url value="/resources/js/pages/systemlist.js"></c:url>"></script>
</body>
</html>