$(function() {
	$('#datetimepicker').datepicker({
		autoclose: true,
		format: "yyyymmdd",
	}).on('changeDate', function(){
		refreshPage();
	});

	//默认日期选中
	var daysago = new Date(moment().add(-1, 'days').format("YYYY-MM-DD"));
	$('#datetimepicker').datepicker('setDate', daysago);
});


function refreshPage(){
	var ds = $("#datetimepicker").val();
	$.ajax({
		url : 'prod/valiorder/getSummaryData',
		type : 'get',
		data : "ds=" + ds,
		dataType : 'json',
		success : function(result) {
			var chartSeries = generateSeriesData(result.chart);
			initSummaryChart(chartSeries);
			initTable(result.title, result.data, ds);
		},
		error : function(e, textStatus, errorThrown) {
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
	$('#mainchart').highcharts({
		chart : {
			type : 'pie'
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
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.percentage:.1f}%</b><br/>'
        },
		title : {
			text : '每日成功失败占比',
			style: {
                fontWeight: 'bold'
            }
		},
		series : [{'name': '成功失败占比', 'data' : series}]
	});
}

function initTable(title, data, ds){
	var base = $('#baseUrl').val();
	var theadcompare = "<tr>";
	$.each(title, function(index, value) {
		theadcompare = theadcompare + "<th class='text-center'>" + value + "</th>";
	});
	theadcompare = theadcompare + "</tr>";
	$("#theadtitle").html(theadcompare);
	
	var tbodycompare = "";
	$.each(data, function(index, line) {
		var username;
		tbodycompare = tbodycompare + "<tr>";
		$.each(line, function(index, value) {
			if (index == 0) username = value;
			else if (index == 8) tbodycompare = tbodycompare + "<td class='text-center'><a href='" + base + "/prod/valiorder/valiOrderDetailPage?ds=" + ds + "&username=" + username + "'>" + value + "</a></td>";
			else tbodycompare = tbodycompare + "<td class='text-center'>" + value + "</td>";
		});
		tbodycompare = tbodycompare + "</tr>";
	});
	
	$("#tbodydata").html(tbodycompare);
}