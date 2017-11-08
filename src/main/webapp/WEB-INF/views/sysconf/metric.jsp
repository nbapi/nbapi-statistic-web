<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body style="background-color: rgb(11, 255, 183);">
    <div class="modal fade ng-scope" id="addMetricModal" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog" style="background-color: rgb(11, 255, 183);">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">添加度量值</h4>
                </div>
                <div class="modal-body">
                    <form role="form" id="formsavemetric">
                        <div class="form-group">
                            <label for="exampleInputEmail1">名称</label> <input type="text"
                                class="form-control" name="name" id="exampleInputEmail1"
                                placeholder="请输入名称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">显示名称</label> <input type="text"
                                class="form-control" name="displayName" id="exampleInputPassword1"
                                placeholder="请输入显示名称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">显示顺序</label><input type="text"
                                class="form-control" name="order" id="exampleTextarea1"
                                placeholder="请输入显示顺序,顺序不可重复">
                        </div>
                        <div class="form-group">
                            <label for="exampleTextarea">计算表达式</label>
                            <textarea class="form-control" id="exampleTextarea" name="formula"
                                rows="3"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="btnsavmetric">保存</button>
                </div>
            </div>
        </div>
    </div>
    <div class="main-box clearfix" style="background-color: rgb(11, 255, 183);">
        <header class="main-box-header clearfix">
            <h2>度量值</h2>
        </header>
        <div class="main-box-body clearfix">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr role="row">
                            <th class="text-center">名称</th>
                            <th class="text-center">显示名称</th>
                             <th class="text-center">显示顺序</th>
                            <th class="text-center">计算表达式</th>
                            <th class="text-center">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${metricList}">
                            <tr>
                                <td class="text-center">${item.name}</td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="displayname"
                                    data-url="sysconf/updatemetric?id=${item.id}" data-type="text"
                                    data-pk="1" data-title="请输入显示名称" class="editable editable-click">${item.displayName}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="order"
                                    data-url="sysconf/updatemetric?id=${item.id}" data-type="text"
                                    data-pk="1" data-title="请输入显示顺序,顺序不能重复" class="editable editable-click">${item.order}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-url="sysconf/updatemetric?id=${item.id}"
                                    data-action="formula" data-type="text" data-pk="${item.id}"
                                    data-title="请输入计算表达式" class="editable editable-click">${item.formula}</a></td>
                                <td class="text-center"><a id=${item.id } href="javascript:;"
                                    data-toggle="tooltip" title="确定要删除？" class="table-link danger">
                                        <span class="fa-stack"> <i
                                            class="fa fa-square fa-stack-2x"></i> <i
                                            class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                    </span>
                                </a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="span12 text-center">
                <button class="btn btn-primary" data-toggle="modal" data-target="#addMetricModal">
                    <i class="fa fa-plus-circle fa-lg"></i> 添加度量值
                </button>
            </div>
        </div>
    </div>
    <script src="<c:url value="/resources/js/pages/metric.js"></c:url>"></script>
    
</body>
</html>