    <%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>系统录入</title>
</head>
<body style="background-color: rgb(39, 30, 23);">
    <div class="modal fade ng-scope" id="addDimension" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">添加维度</h4>
                </div>
                <div class="modal-body">
                    <form role="form" id="formsavedimension">
                        <input type="hidden" name="moduleId" value="${module.id}">
                        <div class="form-group">
                            <label for="localName">维度中文名称 </label> <input type="text"
                                class="form-control" name="localName" id="localName"
                                placeholder="请输入维度中文名称 ">
                        </div>
                        <div class="form-group">
                            <label >维度维数</label>
                            <div class="radio">
                                <input type="radio" name="dimensionCount" id="oneDimension"
                                    value="one" checked="checked"> <label for="oneDimension">
                                                                                                          一维</label>
                                <input type="radio" name="dimensionCount" id="twoDimension"
                                    value="two"> <label for="twoDimension">
                                                                                                                        二维</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dimensionName">维度名称</label> <input type="text"
                                class="form-control" name="dimensionName" id="dimensionName"
                                placeholder="请输入维度名称">
                        </div>
                        <div class="form-group">
                            <label for="displayType">数据展示方式</label><input type="text"
                                class="form-control" name="displayType" id="displayType"
                                placeholder="请输入数据展示方式">
                        </div>
                        <div class="form-group">
                            <label for="displayOrder">显示顺序</label><input type="text"
                                class="form-control" name="displayOrder" id="displayOrder"
                                placeholder="请输入显示顺序">
                        </div>
                        
                        <div class="form-group">
                            <label for="dimensionValues">维度属性值</label><textarea rows="3"
                                class="form-control" name="dimensionValues" id="dimensionValues"
                                placeholder="请输入维度属性值" ></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="btnsavedimension">保存</button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="modal fade ng-scope" id="dimenMetricRelationModal" tabindex="-1" role="dialog"
        aria-labelledby="dimenMetricRelationModal" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">维度和度量值的绑定关系</h4>
                </div>
                <div class="modal-body" id="modalbody">
                    
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="btnsaverelation">保存</button>
                </div>
            </div>
        </div>
    </div>
    
    <div class="main-box clearfix">
        <div class="main-box-header clearfix">
            <h4><a href='<c:url value="/sysconf/systemlist"></c:url>'>现有业务系统</a> > <a href='<c:url value="/sysconf/modulelist?id=${system.id}"></c:url>'>${system.systemName}</a> > ${module.itemName}</h4>
        </div>
        <div class="main-box-body clearfix">
            <div class="table-responsive">
                <table class="table ">
                    <thead>
                        <tr>
                            <th class="text-center">维度中文名称 </th>
                            <th class="text-center">维度名称</th>
                            <th class="text-center">数据展示方式</th>
                            <th class="text-center">维度维数</th>
                            <th class="text-center">显示顺序</th>
                            <th class="text-center">属性值</th>
                            <th class="text-center">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${dimensionList}" var="dimension">
                            <tr>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="localName"
                                    data-url="sysconf/updatedimension?id=${dimension.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${dimension.localName}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="dimensionName"
                                    data-url="sysconf/updatedimension?id=${dimension.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${dimension.dimensionName}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="displayType"
                                    data-url="sysconf/updatedimension?id=${dimension.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${dimension.displayType}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="dimensionCount"
                                    data-url="sysconf/updatedimension?id=${dimension.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${dimension.dimensionCount}</a></td>
                                <td class="text-center"><a href="javascript:;"
                                    data-action="displayOrder"
                                    data-url="sysconf/updatedimension?id=${dimension.id}" data-type="text"
                                    data-pk="1" data-title="请输入显示顺序" class="editable editable-click">${dimension.displayOrder}</a></td>
                            
                                <td class="text-center"><a href="javascript:;"
                                    data-action="dimensionValues"
                                    data-url="sysconf/updatedimension?id=${dimension.id}" data-type="text"
                                    data-pk="1" data-title="请输入系统名称" class="editable editable-click">${dimension.dimensionValues}</a></td>
                                  <td class="text-center" style="width: 80px"><a id=${dimension.id } href="javascript:;"
                                    data-toggle="tooltip" data-action="delete"  title="确定要删除？" class="table-link danger">
                                        <span class="fa-stack"> <i
                                            class="fa fa-square fa-stack-2x"></i> <i
                                            class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                    </span>
                                </a><a href="javascript:;" data-toggle="tooltip" title="查看度量值"
                                        id="${dimension.id}" data-action="showrelation"
                                        class="dropdown-toggle">
                                        <span class="fa-stack"> <i
                                            class="fa fa-square fa-stack-2x"></i> <i
                                            class="fa fa-search-plus fa-stack-1x fa-inverse"></i>
                                    </span>
                                        </a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="span12 text-center">
                <button class="btn btn-primary" data-toggle="modal" data-target="#addDimension">
                    <i class="fa fa-plus-circle fa-lg"></i> 添加维度
                </button>
            </div>
        </div>
    </div>
    <script src="<c:url value="/resources/js/pages/dimensionlist.js"></c:url>"></script>
</body>
</html>