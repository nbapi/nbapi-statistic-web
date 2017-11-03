$(function() {
	$('#datetimepicker').datepicker({
		autoclose: true,
		format: "yyyymmdd",
	}).on('changeDate', function(){
		refreshPage();
	});
	
	var ds = $("#datetimepicker").val();
	var year = ds.substring(0,4);
	var month = ds.substring(4,6);
	var day = ds.substring(6,8); 
	var datesource = new Date(parseInt(year),parseInt(month) - 1,parseInt(day));
	$('#datetimepicker').datepicker('setDate', datesource);
});

function refreshPage(){
	var ds = $("#datetimepicker").val();
	var year = ds.substring(0,4);
	var month = ds.substring(4,6);
	var day = ds.substring(6,8); 
	var datesource = new Date(parseInt(year),parseInt(month) - 1,parseInt(day));

	var username = $("#username").val();
	$.ajax({
		url : 'prod/valiorder/getDetailData',
		type : 'get',
		data : "ds=" + ds + "&username=" + username,
		dataType : 'json',
		success : function(result) {
			var chartSeries = generateSeriesData(result.chart);
			initDetailChart(chartSeries, datesource.getTime());
			initTable(result.chart);
		},
		error : function() {
		}
	});
}

function generateSeriesData(results) {
	var series = [];
	$.each(results, function(index, line){
		var item = {};
		item.name = line[0];
		item.y = line[1];
		series.push(item);
	});
	return series;
}

function initDetailChart(series, t) {
	$('#detailchart').highcharts({
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
			text : '每日错误占比',
			style: {
                fontWeight: 'bold'
            }
		},
		series : [{'name': '每日错误占比', 'data' : series}]
	});
}

function initTable(data){
	var theadcompare = "<tr><th class='text-center'>错误</th><th class='text-center'>次数</th></tr>";
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