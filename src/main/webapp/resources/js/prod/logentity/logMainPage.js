$(function() {
	$('#datetimepicker').datepicker({
		autoclose: true,
		format: "yyyymmdd",
	});
	$('#datetimepicker').datepicker('update');
	
	refreshPage();
});

function initDayChart(series) {
	$('#mainchart').highcharts({
		chart : {
			type : 'column'
		},
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                }
            },
            column: {
				stacking: 'normal'
			}
        },
        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b><br/>'
        },
		title : {
			text : '分销商接口请求量',
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
		series : series

	});
}

function generateSeriesData(results) {
	var series = [];
	$.each(results, function(key0, value0){
		var serie = {};
		serie.name = key0;
		var list = [];
		
		$.each(value0, function(key1, value1){
			var item = {};
			item.name = key1;
			item.y = value1;
			list.push(item);
		});

		serie.data = list;
		series.push(serie);
	});
	return series;
}

function initTable(title, data){
	var base = $('#baseUrl').val();
	var theadcompare = "<tr>";
	$.each(title, function(index, value) {
		theadcompare = theadcompare + "<th class='text-center'>" + value + "</th>";
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

function refreshPage(){
	var ds = $("#datetimepicker").val();
	$.ajax({
		url : '/prod/logentity/getMainData',
		type : 'get',
		data : "ds=" + ds,
		dataType : 'json',
		success : function(result) {
			var chartSeries = generateSeriesData(result.chart);
			initDayChart(chartSeries);
			initTable(result.title, result.data);
		},
		error : function() {
		}
	});
}