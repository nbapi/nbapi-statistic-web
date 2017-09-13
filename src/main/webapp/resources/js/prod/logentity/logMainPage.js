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
	$.ajax({
		url : 'prod/logentity/getMainData',
		type : 'get',
		data : "ds=" + ds,
		dataType : 'json',
		success : function(result) {
			var chartSeries = generateSeriesData(result.chart);
			initDayChart(chartSeries);
			initTable(ds, result.title, result.data);
		},
		error : function() {
		}
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

function initTable(ds, title, data){
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
			else if (index == 1) tbodycompare = tbodycompare + "<td class='text-center'><a href='" + base + "/prod/logentity/logDetailPage?ds=" + ds + "&username=" + username + "'>" + value + "</a></td>";
			else tbodycompare = tbodycompare + "<td class='text-center'>" + value + "</td>";
		});
		tbodycompare = tbodycompare + "</tr>";
	});
	
	$("#tbodydata").html(tbodycompare);
}