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

							<li id="li-tab-count" class="active">
								<a id="a-home" data-target="#tab-count" data-toggle="tab">分销商可定失败报表</a>
							</li>

						</ul>
						<div class="tab-content">

							<div class="tab-pane fade active in" id="tab-count">
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
											<th></th>
											<th class='text-center'>失败原因</th>
											<th class='text-center'>失败次数</th>
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
	<script src="<c:url value="/resources/js/report/checkfailurecount_daily.js"></c:url>"></script>
</body>
</html>