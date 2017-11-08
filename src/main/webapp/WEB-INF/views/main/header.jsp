<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="navbar" id="header-navbar">

    <div class="container" style=" background-color: rgb(39, 30, 23);">
        <span id="logo" class="navbar-brand" style="text-align:center;padding-top: 16px">分销数据平台</span>
        <div class="clearfix" style=" background-color: rgb(39, 30, 23);">
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
                             <li class="item">
                                <a href="<c:url value='/sysconf/metriclist'></c:url>">
                                    <i class="fa fa-reorder">
                                    </i>
                                    度量值
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- <li class="hidden-xxs">
                        <a class="btn">
                            <i class="fa fa-power-off">
                            </i>
                        </a>
                    </li> -->
                </ul>
            </div>
        </div>
    </div>
</header>