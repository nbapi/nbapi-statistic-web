<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="navbar" id="header-navbar">

    <div class="container">
        <span id="logo" class="navbar-brand">
       <div class="text-center" style="padding-top: 5px"><c:if test="${currentSysName!=null }">
                           ${currentSysName}
                        </c:if></div>
        <%-- <img src='<c:url value="/resources/image/elong.png"></c:url>' class="normal-logo logo-blue"> --%>
            <%-- <img src='<c:url value="/resources/image/logo.jpg"></c:url>' class="normal-logo logo-white">
            <img src='<c:url value="/resources/image/logo.jpg"></c:url>' class="normal-logo logo-black">
            <img src='<c:url value="/resources/image/logo.jpg"></c:url>' class="small-logo hidden-xs hidden-sm hidden"> --%>
        </span>
        <div class="clearfix">
            <button class="navbar-toggle" data-target=".navbar-ex1-collapse" data-toggle="collapse" type="button">
                <span class="sr-only">
                    Toggle navigation
                </span>
                <span class="fa fa-bars">
                </span>
            </button>
            <div class="nav-no-collapse navbar-left pull-left hidden-sm hidden-xs">
                <ul class="nav navbar-nav pull-left">
                    <li>
                        <a class="btn" id="make-small-nav">
                            <i class="fa fa-bars">
                            </i>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="nav-no-collapse pull-right" id="header-nav">
            
                <ul class="nav navbar-nav pull-right">
                    <!-- <li class="mobile-search">
                        <a class="btn">
                            <i class="fa fa-search">
                            </i>
                        </a>
                        <div class="drowdown-search">
                            <form role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search...">
                                    <i class="fa fa-search nav-search-icon">
                                    </i>
                                </div>
                            </form>
                        </div>
                    </li> -->
                      <%-- <li class="dropdown hidden-xs">
                        <a class="btn dropdown-toggle" data-toggle="dropdown">
                            下游业务系统
                            <i class="fa fa-caret-down">
                            </i>
                        </a>
                        <ul class="dropdown-menu notifications-list" style="min-width: 200px">
                            <c:forEach var="item" items="${businessList}">
                                <li class="item">
                                    <a href="<c:url value="/sysoption?id=${item.id}"></c:url>">
                                        <i class="fa fa-archive">
                                        </i>
                                        ${item.systemName}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </li> --%>
                   
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-cog">&nbsp</i>
                            <span class="hidden-xs">
                                系统配置
                            </span>
                            <b class="caret">
                            </b>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-right notifications-list" style="min-width: 180px">
                            <li class="item">
                                <a href="<c:url value='/sysconf/systemlist'></c:url>">
                                    <i class="fa fa-user">
                                    </i>
                                                                                                                            业务系统
                                </a>
                            </li>
                            <%-- <li class="item">
                                <a href="<c:url value='/module/show'></c:url>">
                                    <i class="fa fa-cog">
                                    </i>
                                    已有统计项
                                </a>
                            </li>
                            <li class="item">
                                <a href="<c:url value='/dimension/show'></c:url>">
                                    <i class="fa fa-envelope-o">
                                    </i>
                                    已有通用维度
                                </a>
                            </li> --%>
                             <li class="item">
                                <a href="<c:url value='/sysconf/metriclist'></c:url>">
                                    <i class="fa fa-reorder">
                                    </i>
                                    度量值
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="hidden-xxs">
                        <a class="btn">
                            <i class="fa fa-power-off">
                            </i>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</header>