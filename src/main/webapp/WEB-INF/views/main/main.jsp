<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 为了确保适当的绘制和触屏缩放，需要在 <head> 之中添加 viewport 元数据标签 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>日志统计分析</title>
<link href="<c:url value="/resources/css/bootstrap.min.css"></c:url>" rel="stylesheet"
    type="text/css">
<link href="<c:url value="/resources/css/font-awesome/css/font-awesome.min.css"></c:url>"
    rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/nanoscroller.css"></c:url>" rel="stylesheet"
    type="text/css">
<link href="<c:url value="/resources/css/themestyles.css"></c:url>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/bootstrap-datetimepicker.css"></c:url>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/bootstrap-datepicker3.min.css"></c:url>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/daterangepicker-bs3.css"></c:url>" rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/bootstrap-editable.css"></c:url>" rel="stylesheet"
    type="text/css">
<link href="<c:url value="/resources/css/notification/normalize.css"></c:url>" rel="stylesheet"
    type="text/css">
<link href="<c:url value="/resources/css/notification/ns-default.css"></c:url>" rel="stylesheet"
    type="text/css">
<link href="<c:url value="/resources/css/notification/ns-style-attached.css"></c:url>" rel="stylesheet"
    type="text/css">
<link href="<c:url value="/resources/css/notification/ns-style-theme.css"></c:url>" rel="stylesheet"
    type="text/css">  

<!-- Placed at the end of the document so the pages load faster -->
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js"></c:url>"></script>
<script src="<c:url value="/resources/js/moment.min.js"></c:url>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"></c:url>"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.min.js"></c:url>"></script>
<script src="<c:url value="/resources/js/bootstrap-datetimepicker.js"></c:url>"></script>
<script src="<c:url value="/resources/js/daterangepicker.js"></c:url>"></script>
<script src="<c:url value="/resources/js/jquery.nanoscroller.min.js"></c:url>"></script>
<script src="<c:url value="/resources/js/bootstrap-editable.min.js"></c:url>"></script>

<script src="<c:url value="/resources/js/highstock.js"></c:url>"></script>

<script src="<c:url value="/resources/js/js-template.js"></c:url>"></script>
<script src="<c:url value="/resources/js/navigation.js"></c:url>"></script>
<script src="<c:url value="/resources/js/hotelcommon.js"></c:url>"></script>
<script src="<c:url value="/resources/js/notification/modernizr.custom.js"></c:url>"></script>
<script src="<c:url value="/resources/js/notification/classie.js"></c:url>"></script>
<script src="<c:url value="/resources/js/notification/notificationFx.js"></c:url>"></script>
<script src="<c:url value="/resources/js/jquery.twbsPagination.min.js"></c:url>"></script>
<script src="<c:url value="/resources/js/theme.js"></c:url>"></script>
<script type="text/javascript">
    var contextPath = "<c:url value="/"></c:url>";
    $.ajaxPrefilter(function(options,originalOptions, jqXHR) {
    	options.url = contextPath + options.url;
	});
    
    function showMessage(message) {
        var notification = new NotificationFx(
                {
                    message : message,
                    layout : 'attached',
                    effect : 'bouncyflip',
                    wrapper : document.body,
                    ttl: 2000,
                    type : 'success', // notice, warning or error
                    onClose : function() {
                        $("#popup")[0].disabled = false;
                    }
                });

        //d show the notification
        notification.show();
    }
    
    Highcharts.setOptions({

        global : {
            useUTC : false
        },
        lang: {
        	rangeSelectorZoom: '缩略',
        	rangeSelectorFrom: '从',
        	rangeSelectorTo:'到'
        }

    });
</script>
</head>
<body class="theme-whbl fixed-header">
    <div id="theme-wrapper">
        <%@ include file="header.jsp"%>
        <div id="page-wrapper" class="container">
            <div class="row">
                <%@ include file="left.jsp"%>
                <div id="content-wrapper">
                    <div class="row" style="opacity: 1;">
                        <div class="col-lg-12">
                            <div class="slide-main-container">
                                <div class="slide-main-animation ng-scope">
                                    <sitemesh:write property='body' />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
