$(function() {
	$('#datetimepickerFrom').datepicker({
		autoclose: true,
		format: "yyyymmdd",
	});
	$('#datetimepickerTo').datepicker({
		autoclose: true,
		format: "yyyymmdd",
	});
	
	//默认日期选中
	var nowDate = new Date();
	var daysago = new Date(moment().add(-7, 'days').format("YYYY-MM-DD"));
	$('#datetimepickerFrom').datepicker('setDate', daysago);
	$('#datetimepickerFrom').datepicker('update');
	$('#datetimepickerTo').datepicker('setDate', nowDate);
	$('#datetimepickerTo').datepicker('update');
	
	$("#queryBtn").click(function() {
		var startDs = $("#datetimepickerFrom").val();
		var endDs = $("#datetimepickerTo").val();
		refreshPage(startDs,endDs);
	});
	$("#queryBtn").click();
});


function refreshPage(startDs,endDs){
	$.ajax({
		url : 'prod/logentity/getSummaryData',
		type : 'get',
		data : "startDs=" + startDs + "&endDs=" + endDs,
		dataType : 'json',
		success : function(result) {
			var chartSeries = generateSeriesData(result.summary);
			initSummaryChart(chartSeries);
			initTable(result.title, result.data);
		},
		error : function(e, textStatus, errorThrown) {
			alert('日期范围不能大于15天');
		}
	});
}

function generateSeriesData(results) {
	var chartSeries = [];
	for (var n = 0; n < results.length; n=n+2) {
		var item = {};
		item.name = results[n];
		item.y = results[n+1];
		chartSeries.push(item);
	}
	return chartSeries;
}

function initSummaryChart(series) {
	$('#summarychart').highcharts({
		chart : {
			type : 'column'
		},
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                }
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b><br/>'
        },
		title : {
			text : '每日请求量汇总',
			style: {
                fontWeight: 'bold'
            }
		},
		xAxis : {
			type: 'category'
		},
		yAxis : {
            title: {
                text: '(次)'
            }
		},
		legend : {
			align : 'center',
			verticalAlign : 'bottom',
			borderWidth : 0
		},
		credits : {
			enabled : false
		},
		series : [{'name': '请求量', 'data' : series}]

	});
}

function initTable(title, data){
	var base = $('#baseUrl').val();
	var theadcompare = "<tr>";
	$.each(title, function(index, value) {
		theadcompare = theadcompare + "<th class='text-center'><a href='" + base + "/prod/logentity/logMainPage?ds=" + value + "'>" + value + "</a></th>";
	});
	theadcompare = theadcompare + "</tr>";
	$("#theadtitle").html(theadcompare);
	
	var tbodycompare = "";
	$.each(data, function(index, line) {
		tbodycompare = tbodycompare + "<tr>";
		$.each(line, function(index, value) {
			tbodycompare = tbodycompare + "<td class='text-center'>" + value + "</td>";
		});
		tbodycompare = tbodycompare + "</tr>";
	});
	
	$("#tbodydata").html(tbodycompare);
}