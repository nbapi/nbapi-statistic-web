<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title></title>
<link href="<c:url value="/resources/css/dialog/base.css"></c:url>"
	rel="stylesheet" type="text/css">
</head>
<body background="" style="background-color: rgb(11, 255, 183);">
	<input type="hidden" id="baseUrl" value="${pageContext.request.contextPath}"/> 
	<form class="form-horizontal ng-pristine ng-valid">
		<div class="main-box clearfix" style="background-color: rgb(11, 255, 183);"> 

			<div class="main-box-body clearfix">
				<div class="span12">
					<div class="tabs-wrapper profile-tabs">
						<ul class="nav nav-tabs">

							<li id="li-tab-count" class="active">
								<a id="a-home" data-target="#tab-count" data-toggle="tab">去哪可卖产品数报表</a>
							</li>

						</ul>
						<div class="tab-content">

							<div class="tab-pane fade active in" id="tab-count">
								<div class="row">
									<div>
									日期:<input id="datetimepicker" type="text" value="${ds}"/>
									</div>
									<div class="table-responsive">
										<table class="table-striped table-condensed table-bordered table-hover table-responsive">
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
	<script src="<c:url value="/resources/js/report/mainReportPage_q.js"></c:url>"></script>
</body>
</html>