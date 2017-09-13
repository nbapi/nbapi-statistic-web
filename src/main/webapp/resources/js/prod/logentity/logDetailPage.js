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
		url : 'prod/logentity/getDetailData',
		type : 'get',
		data : "ds=" + ds + "&username=" + username,
		dataType : 'json',
		success : function(result) {
			var chartSeries = generateSeriesData(result.chart);
			initDetailChart(chartSeries, datesource.getTime());
			initTable(result.title, result.data);
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
			list.push(value1);
		});
		serie.data = list;
		series.push(serie);
	});
	return series;
}

function initDetailChart(series, t) {
	$('#detailchart').highcharts({
		chart : {
			type : 'spline'
		},
        plotOptions: {
        	spline: {
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 2
                    }
                },
                marker: {
                    enabled: false
                },
                pointInterval: 60000, //one minute
                pointStart: t
            }
        },
		title : {
			text : '分销商每日明细',
			style: {
                fontWeight: 'bold'
            }
		},
		xAxis : {
			type: 'datetime',
			labels: {
				overflow: 'justify'
		    }
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

function initTable(title, data){
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