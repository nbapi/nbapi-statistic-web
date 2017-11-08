<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>系统录入</title>
</head>
<body style="background-color: rgb(39, 30, 23);">
    <div class="modal fade ng-scope" id="addModule" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">添加统计项</h4>
                </div>
                <div class="modal-body">
                    <form role="form" id="formsavemodule">
                        <input type="hidden" name="systemId" value="${system.id}">
                        <div class="form-group">
                            <label for="itemName">统计项</label> <input type="text"
                                class="form-control" name="itemName" id="itemName"
                                placeholder="请输入统计项">
                        </div>
                        <div class="form-group">
                            <label for="businessType">业务类型</label> <input type="text"
                                class="form-control" name="businessType" id="businessType"
                                placeholder="请输入业务类型">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="btnsavemodule">保存</button>
                </div>
            </div>
        </div>
    </div>
    <div class="main-box clearfix">
        <div class="main-box-header clearfix">
            <h4><a href='<c:url value="/sysconf/systemlist"></c:url>'>现有业务系统</a> > ${system.systemName}</h4>
        </div>
        <div class="main-box-body clearfix">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th class="text-center">统计项</th>
                            <th class="text-center">业务类型</th>
                            <th class="text-center">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${moduleList}" var="module">
                            <tr>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="itemName"
                                    data-url="sysconf/updatemodule?id=${module.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${module.itemName}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="businessType"
                                    data-url="sysconf/updatemodule?id=${module.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${module.businessType}</a></td>
                                <td class="text-center"><a id=${module.id } href="javascript:;"
                                    data-toggle="tooltip" data-action="delete" title="确定要删除？"
                                    class="table-link danger"> <span class="fa-stack"> <i
                                            class="fa fa-square fa-stack-2x"></i> <i
                                            class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                    </span>
                                </a> <a data-toggle="tooltip" title="查看维度"
                                    href='<c:url value='/sysconf/dimensionlist?id=${module.id}'></c:url>'
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
                <button class="btn btn-primary" data-toggle="modal" data-target="#addModule">
                    <i class="fa fa-plus-circle fa-lg"></i> 添加统计项
                </button>
            </div>
        </div>
    </div>
    <script src="<c:url value="/resources/js/pages/modulelist.js"></c:url>"></script>
</body>
</html>