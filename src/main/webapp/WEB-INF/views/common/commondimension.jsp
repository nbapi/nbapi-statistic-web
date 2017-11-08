
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<body background="">
<form class="form-horizontal ng-pristine ng-valid">
    <div class="main-box clearfix">
        <header class="main-box-header clearfix">
            <div class="btn-group" data-toggle="buttons">
                <c:forEach var="metric" items="${dimension.metricList}">
                    <label class="btn btn-primary"> <input type="radio" name="options"
                        id="${metric.name}"> ${metric.displayName}
                    </label>
                </c:forEach>
            </div>
        </header>
        <div class="main-box-body clearfix">
            <div class="span12">
                <div class="tabs-wrapper profile-tabs">
                    <ul class="nav nav-tabs">
                        <li class="active"><a showtab="" data-target="#tab-realtime"
                            data-toggle="tab" aria-expanded="true">30分钟实时数据</a></li>
                       
                        <li class=""><a showtab="" data-target="#tab-comparison" data-toggle="tab"
                            aria-expanded="false"id ="tabComparison">全天比对数据</a></li>
                      
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane fade active in" id="tab-realtime">
                            <div id="realtimechart" class="row"></div>
                            <br>
                            <div class="row">
                            <div class="table-responsive">
                                <table id="realtimetable" class="table  table-hover table-condensed">
                                    <thead>
                                        <tr>
                                            <th class="text-center">时间</th>
                                            <th class="text-center" data-jst-select="heads"
                                                data-jst-content="$this"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr data-jst-select="bodys">
                                            <td  class="text-center" data-jst-content="$this.time"></td>
                                            <td class="text-center" data-jst-select="$this.tdValues"
                                                data-jst-content="$this"></td>
                                        </tr>
                                    </tbody>
                                    <tfoot>
                                    	 <tr>
                                            <td class="text-center">总计</td>
                                            <td class="text-center"  data-jst-select="tails"
                                                data-jst-content="$this"></td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            </div>
                        </div>

                    <div class="tab-pane fade" id="tab-comparison">
                        <div class="row  pull-right">
                            <div class="form-group">
                                <label class="control-label col-md-1">比对日期</label>
                                <div class="col-md-9">
                                    <div class='input-group '>
                                        <input type='text' class="form-control"
                                            id='comparisonDate' /> <span
                                            class="input-group-addon"><span
                                            class="glyphicon glyphicon-calendar"></span> </span>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-success btn-sm"
                                    id="dataComparison">比较</button>
                            </div>
                        </div>          
                        <div class="row" id="dimensionValuesRadio">
                            <div class="col-md-2">
                                <div class="radio">
                                    <input type="radio" name="dimensionComp" checked="checked"
                                        id="totalComp" value="total"> <label for="totalComp">总计</label>
                                </div>
                            </div>
                            <c:forEach var="item" items="${dimensionValues}">
                                <div class="col-md-2">
                                    <div class="radio">
                                        <input type="radio" name="dimensionComp" id="${item}Comp"
                                            value="${item}"> <label for="${item}Comp">
                                            ${item}</label>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <br>
                        <div class="row" id="comparisonChart" style="min-width: 500px"></div>
                        <br>
                        <div class="row">
                            <div class="table-responsive">
                                <table id="comparisonTable"
                                    class="table  table-hover table-condensed">
                                    <thead id="comparisonThead">
                                    </thead>
                                    <tbody id="comparisonTbody">
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


    <script src="<c:url value="/resources/js/pages/commondimension.js"></c:url>"></script>
    
    <script src="<c:url value="/resources/js/sockjs-1.0.3.min.js"></c:url>"></script>
    <script src="<c:url value="/resources/js/stomp.min.js"></c:url>"></script>
    
    <script type="text/javascript">
        var endpoint = '<c:url value="/ws"></c:url>';
        var navParameters = ${navParameters};
        
	</script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/dialog/base.css"></c:url>"/>
	<script type="text/javascript" src="<c:url value="/resources/js/dialog/common.js"></c:url>"></script>
</body>
</html>