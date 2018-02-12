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
	<input type="hidden" id="baseUrl" value="${pageContext.request.contextPath}"/>
	<form class="form-horizontal ng-pristine ng-valid">
		<div class="main-box clearfix">

			<div class="main-box-body clearfix">
				<div class="span12">
					<div class="tabs-wrapper profile-tabs">
						<ul class="nav nav-tabs">

							<li id="li-tab-count" class="active">
								<a id="a-home" data-target="#tab-count" data-toggle="tab">分销流量分析</a>
							</li>

						</ul>
						<div class="tab-content">

							<div class="tab-pane fade active in" id="tab-count">
								<div class="row row-input">
									<div class="btn-group">
										日期范围：
										<input id="datetimepickerFrom" type="text" >
										到
										<input id="datetimepickerTo" type="text" >
										<button type="button" id="queryBtn">查询</button>
									</div>
									<div id="summarychart" class="row">
										
									</div>
									<div class="table-responsive">
										<table class="table-striped table-condensed table-bordered table-hover table-responsive">
											<caption>点击日期分析</caption>
											<thead id="theadtitle">
											</thead>
											<tbody id="tbodydata">
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
	<script src="<c:url value="/resources/js/prod/logentity/logSummaryPage.js"></c:url>"></script>
</body>
</html>