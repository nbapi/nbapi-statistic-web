<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="nav-col">
	<section id="col-left" class="col-left-nano">
		<div id="col-left-inner" class="col-left-nano-content">
			<div id="user-left-box"
				class="clearfix hidden-sm hidden-xs dropdown profile2-dropdown">

				<img alt="" src='<c:url value="/resources/image/logo.gif"></c:url>'>

			</div>
		</div>
		<div class="collapse navbar-collapse navbar-ex1-collapse"
			id="sidebar-nav">
			<ul class="nav nav-pills nav-stacked">

				<li
					<c:if test="${activeItem[0] eq 'index' }"> class ="active" </c:if>><a
					href='<c:url value="/index"></c:url>'> <i
						class="fa fa-dashboard"></i> <span>实时数据监控</span>
				</a></li>
				
				<c:forEach var="item" items="${sysModuleList}">
					<c:set var="preUrl" value="leftnav" />

					<li
						<c:if test="${activeItem[0] eq item.businessType}"> class ="active open" </c:if>><a
						href="" class="dropdown-toggle"> <i class="fa fa-list"></i> <span>${item.itemName}</span>
							<i class="fa fa-angle-right drop-icon"></i>
					</a>
						<ul class="submenu">
							<c:forEach var="dimension" items="${item.oneDimensionList}">
								<li><c:if test='${"noDisplay" != dimension.displayType}'>
										<a
											<c:if test="${activeItem[1] eq item.businessType.concat(dimension.dimensionName)}">class ="active"</c:if>
											href="<c:url value="/${preUrl}/${item.businessType}/${dimension.dimensionName}?sysid=${item.systemId}&dimensionId=${dimension.id}"></c:url>">${dimension.localName}
										</a>
									</c:if></li>
							</c:forEach>
						</ul></li>
				</c:forEach>


				<li><a href="" class="dropdown-toggle"><i
						class="fa fa-dashboard"></i> <span>报表统计</span><i
						class="fa fa-angle-right drop-icon"></i></a>
					<ul class="submenu">
						<li><a
							href="<c:url value="/daily/methodcount"></c:url>">分销商访问次数报表</a></li>
					</ul>
					<ul class="submenu">
						<li><a
							href="<c:url value="/daily/ordercount"></c:url>">分销商订单报表</a></li>
					</ul>
				</li>


			</ul>
		</div>
	</section>
</div>