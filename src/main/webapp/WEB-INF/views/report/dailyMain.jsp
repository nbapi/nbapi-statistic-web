<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title></title>
<link href="<c:url value="/resources/css/dialog/base.css"></c:url>"
	rel="stylesheet" type="text/css">
</head>
<body background="">

	<form class="form-horizontal ng-pristine ng-valid">
		<div class="main-box clearfix">

			<div class="main-box-body clearfix">
				<div class="span12">
					<div class="tabs-wrapper profile-tabs">
						<ul class="nav nav-tabs">

							<li id="li-tab-count"
								<c:if test="${selectTab=='li-tab-count'}">class="active"</c:if>>
								<a id="a-home" data-target="#tab-count" data-toggle="tab">分销商接口调用量</a>
							</li>
							<li id="li-tab-order"
								<c:if test="${selectTab=='li-tab-order'}">class="active"</c:if>>
								<a data-target="#tab-order" data-toggle="tab">分销商订单</a>
							</li>

						</ul>
						<div class="tab-content">
							<%-- 分销商接口调用量分析 --%>
							<div
								<c:if test="${selectTab=='li-tab-count'}">class="tab-pane fade active in"</c:if>
								<c:if test="${selectTab!='li-tab-count'}">class="tab-pane fade"</c:if>
								id="tab-count">

								<div class="row">
									<div class="btn-group">
										<select id="seldate" name="选择日期">
                                        	<c:forEach items="${selDate}" var="cc">
                                            	<option value="${cc}">${cc}</option>
                                            </c:forEach>
                                        </select>
									</div>
									
									<div class="table-responsive">
										<table class="table-striped table-condensed table-bordered table-hover">
											<thead id="theadtitle">
											</thead>
											<tbody id="tbodydata">
											</tbody>
										</table>
									</div>
								</div>
							</div>

							<%-- 当天小时数据 --%>
							<div
								<c:if test="${selectTab=='li-tab-order'}">class="tab-pane fade active in"</c:if>
								<c:if test="${selectTab!='li-tab-order'}">class="tab-pane fade"</c:if>
								id="tab-order">
								
								<div class="row">
									<div class="btn-group">
										<select id="oseldate" name="选择日期">
                                        	<c:forEach items="${oselDate}" var="oc">
                                            	<option value="${oc}">${oc}</option>
                                            </c:forEach>
                                        </select>
									</div>
									
									<div class="table-responsive">
										<table class="table-striped table-condensed table-bordered table-hover">
											<thead><th></th>
											<th class='text-center' colspan="3">可定性检查</th>
											<th class='text-center' colspan="3">可定性检查通过率</th>
											<th class='text-center btn-primary' colspan="3">成单请求</th>
											<th class='text-center' colspan="3">成单请求通过率</th>
											<th class='text-center' colspan="3">预付</th>
											<th class='text-center' colspan="3">现付</th>
											</thead>
											<thead id="otheadtitle">
											
											</thead>
											<tbody id="otbodydata">
											</tbody>
										</table>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>

		</div>
	</form>
	<script src="<c:url value="/resources/js/report/daily-counttable.js"></c:url>"></script>
</body>
</html>