<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
    <div class="main-box clearfix" style="min-height: 800px">
        <div class="main-box-body clearfix">
            <br>
            <div class="span12">
                <c:forEach var="item" items="${businessList}">
                    <div class="col-lg-3 col-sm-6 col-xs-12 ">
                        <a class="text-center"
                            href="<c:url value="/statistic/sysoption?id=${item.id}" ></c:url>">
                            <div class="main-box small-graph-box green-bg green-bg-hover">
                                <span class="text-center value">${item.systemName}</span>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>