$(function() {
	$("#realtimetable").hide();
	
	// 设置第一个度量值默认选择
	$("div.btn-group label:first-child").click();
	var realTimeMetricChange = true;
	function getCheckedMetric() {
		var metric = "";
		$(".btn-group").find(":radio").each(function()  {
			if($(this).prop("checked")) {
				metric = $(this).prop("id");
			}
		});
		return metric;
	}

	var stompClient = null;
    var reconn;
	var socket;
	var lastConnTime=new Date();
	function connect() {
		disconnect();
		socket = new SockJS(endpoint);

		lastInternal=window.setInterval(function()
		{
			if(new Date().getTime()-lastConnTime.getTime()>60000) {
				console.log("正在进行保底重连或刷新");
				connect();
			}
		}, 60000);
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {

			window.clearInterval(reconn);
			showRealtimeChart();

			navParameters.metric = getCheckedMetric();
			stompClient.send("/app/showdata", {}, JSON.stringify(navParameters));
		});
	}

	function disconnect() {
		if(!isBlank(stompClient)) {
			stompClient.disconnect();
		}
		
		stompClient = null;
		console.log("Disconnected");
	}

	connect();
	socket.onclose=function()
	{
		console.log("连接关闭，定时重连");
		if(reconn!=null) {
			window.clearInterval(reconn);
		}
		var retry=0;
		reconn = window.setInterval(function()
		{
			++retry;
			console.log("retry:"+retry);
			connect();
		}, 5000);

	};

	var dimension = navParameters["dimension"];
	var dimensionVals = eval(dimension.dimensionValuesJson);
	
	var isInit = false;
	
	var userDisplaySeries={};
	function showRealtimeChart() {
		var subscribe = "/realtime/" + navParameters.businessType + "/"
				+ navParameters.dimensionName + "/" + getCheckedMetric();

		var TotalSubScribe= "/realtime/" + navParameters.businessType + "/TOTAL/"
			+ navParameters.dimensionName + "/" + getCheckedMetric();
		stompClient.subscribe(subscribe, function(results) {
			
			lastConnTime=new Date();
			var items = JSON.parse(results.body);
			
			var seriesData = generateSeriesData(items, true);
			
			generateRealTimeTable(items);

			var realchart = jQuery('#realtimechart').highcharts();
			if (isBlank(realchart) || realTimeMetricChange) {
				realTimeMetricChange = false;
				isInit=false;
				initRealtimeChart(dimension.localName, seriesData);
				isInit=true;
				return;
			}
			var currentSeries = realchart.series;
			$.each(currentSeries, function(index0, value0) {

				//联动逻辑
				if(value0.visible)
				{
					userDisplaySeries[value0.name]=true;
				}else
				{
					userDisplaySeries[value0.name]=false;
				}
				for (var i = 0; i < seriesData.length; i++) {
					// 同一个series
					if (value0.name == seriesData[i].name) {

						var data = seriesData[i].data;
						var newTime = data[data.length - 1];

						var points = value0.data;
						var endPoint = points[points.length - 1];
						if(endPoint!=undefined) {
							if (newTime[0] == endPoint.category
								|| newTime[0] == endPoint.name) {
								value0.setData(data, false);
							} else {
								value0.addPoint(newTime, false, true);
							}
						}
						break;
					}
				}

			});
			

		});

		stompClient.subscribe(TotalSubScribe, function(results) {
			var items = JSON.parse(results.body);
			//生成计算的数据
			var seriesData = generateSeriesTotalData(items);
			var realchart = jQuery('#realtimechart').highcharts();
			var currentSeries = realchart.series;
			$.each(currentSeries, function(index0, value0) {

				for (var i = 0; i < seriesData.length; i++) {
					// 同一个series
					if (value0.name==seriesData[i].name && (value0.name =='DOD'||value0.name =='WOW')) {

						var data = seriesData[i].data;
						var newTime = data[data.length - 1];

						var points = value0.data;
						var endPoint = points[points.length - 1];
						if(endPoint!=undefined){
						if (newTime[0] == endPoint.category || newTime[0] == endPoint.name) {
							value0.setData(data, false);
						} else {
							value0.addPoint(newTime, false, true);
						}
						}
						
						break;
					}
				}

			});

			realchart.redraw();

		});
	}


	var singleTotal = [];
	function generateRealTimeTable(items) {
		var tableData = {};

		var heads = $.extend([], dimensionVals);
		heads.push("总计");

		tableData.heads = heads;
		tableData.bodys = generateTableData(items);
		
		
		tableData.tails = singleTotal;
		
		$("#realtimetable").refillTemplate(tableData);
		$("#realtimetable").show();
		$("#realtimetable tbody tr:first-child td").each(function() {
			var lable = "<span class='label label-success'>"+ $(this).text()+ "</span>";
			$(this).html(lable);
		});
		
	}

	function generateTableData(items) {
		for (var j = 0; j <=dimensionVals.length; j++){
			singleTotal[j] = 0;
		}
		
		for(var i=0; i < items.length; i++) {
			var item = items[i];
			
			var dimensionValue = item["dimensionValue"];
			if (isBlank(dimensionValue)) {
				dimensionValue = {};
			} else {
				dimensionValue = JSON.parse(dimensionValue);
			}
			
			var td = [];
		
			for (var j = 0; j < dimensionVals.length; j++) {
				
				var key = dimensionVals[j];
				var value = dimensionValue[key];
				
				if (isBlank(value)) {
					value = '0';
					singleTotal[j]+=0;
				}else{
					singleTotal[j]+=value;
				}
				td.push(value);
			}
			
			if(isBlank(item.total)){
				td.push('0');
				singleTotal[dimensionVals.length]+=0;
			}
		
			td.push(item.total);
			singleTotal[dimensionVals.length]+=item.total;
			item.tdValues= td;
		}

		for(var j=0;j<=dimensionVals.length;j++){
			if(singleTotal[j]==0){
				singleTotal[j]='0';
			}
		}
		var reverseItems = [];
		for (var n = items.length - 1; n >= 0; n--) {
			reverseItems.push(items[n]);
		}
		
		return reverseItems;
	}

	Date.prototype.format=function(fmt) {
		var o = {
			"M+" : this.getMonth()+1, //月份
			"d+" : this.getDate(), //日
			"h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
			"H+" : this.getHours(), //小时
			"m+" : this.getMinutes(), //分
			"s+" : this.getSeconds(), //秒
			"q+" : Math.floor((this.getMonth()+3)/3), //季度
			"S" : this.getMilliseconds() //毫秒
		};
		var week = {
			"0" : "\u65e5",
			"1" : "\u4e00",
			"2" : "\u4e8c",
			"3" : "\u4e09",
			"4" : "\u56db",
			"5" : "\u4e94",
			"6" : "\u516d"
		};
		if(/(y+)/.test(fmt)){
			fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
		}
		if(/(E+)/.test(fmt)){
			fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);
		}
		for(var k in o){
			if(new RegExp("("+ k +")").test(fmt)){
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
			}
		}
		return fmt;
	}

	function initRealtimeChart(localName, series) {
		$('#realtimechart').highcharts({
			chart : {
				borderColor : '#EBBA95',
				borderWidth : 1,
				animation : {
					duration : 1000
				}
			},

			plotOptions: {
				column: {
					stacking: 'normal'
				}
			},
			colors: ['#157117','#3271B7', '#92A8CD', '#A47D7C', '#B5CA92','#4572A7', '#AA4643', '#89A54E', '#80699B', '#3D96AE',
				'#DB843D'],
			tooltip: {
				crosshairs: true,
				shared: true,
					dateTimeLabelFormats: {
					    millisecond:"%H:%M",
					    second:"%H:%M",
					    minute:"%H:%M",
					    hour:"%H:%M",
					    day:"%H:%M"
					},
				formatter: function() {
					var content='<i>'+new Date(this.points[0].x).format("yy-MM-dd HH:mm")+'</i><br/>';
					var total=0;
					for (var i = 0; i < this.points.length; i++) {
						content += '<span style="color: ' + this.points[i].series.color + '">' + this.points[i].series.name + '</span>:<b> ' + this.points[i].y + '</b>';
						if (this.points[i].series.name != 'DOD' && this.points[i].series.name != 'WOW') {
							total += this.points[i].y;
							content += '<span style="color: ' + this.points[i].series.color + '">(<i>' + this.points[i].percentage.toFixed(0) + '%</i>)</span>';
						} else if (this.points[i].y!=undefined&&this.points[i].y!=0) {
							var dis=(total/this.points[i].y)*100;
							content += '<span style="color: ' + this.points[i].series.color + '">(<i>' + dis.toFixed(0) + '%</i>)</span>';

						} else{
							content += '<span style="color: ' + this.points[i].series.color + '">(<i>NA</i>)</span>';
						}
						content+='<br/>';

					}
					return content+'<b>Total:'+total+'</b>';
				},
					valueSuffix : ''
			},

			title : {
				text : localName,
				style: {
	                //color: '#03a9f4',
	                fontWeight: 'bold'
	            }
			},
			xAxis : {
				//categories : cates,
				tickmarkPlacement : 'on',
				labels : {
					rotation : -45
				},
				type: "datetime",
				dateTimeLabelFormats :{
					minute: '%H:%M',
					hour: '%H:%M',
				},
				minRange : 60 * 1000,
				tickInterval : 60 * 1000
			},
			yAxis : {
				allowDecimals : false,
				title : {
					text : ''
				},
				min : 0,
				stackLabels: {
					enabled: true,
					style: {
						fontWeight: 'bold',
						color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
					}
				}
			},

			legend : {
				align : 'center',
				verticalAlign : 'bottom',
				//x:200,
				borderWidth : 0
			},
			credits : {
				enabled : false
			},
			series : series

		});
	}

	// 生成图表数据
	function generateSeriesData(results, isRealTime) {
		var dimensionValsMap = {};
        var firstx=[];
		for (var i = 0; i < results.length; i++) {

			var dimensionValue = results[i]["dimensionValue"];
			if (isBlank(dimensionValue)) {
				dimensionValue = {};
			} else {
				dimensionValue = JSON.parse(dimensionValue);
			}

			for (var j = 0; j < dimensionVals.length; j++) {

				var key = dimensionVals[j];
				var list = dimensionValsMap[key];
				if (isBlank(list)) {
					list = [];
					dimensionValsMap[key] = list;
				}

				var value = dimensionValue[key];
				if (isBlank(value)) {
					value = 0;
				}
				
				var xy = [];
				var x = 0;
				if(isRealTime) {
					x = moment(results[i]["dateTime"],  "YYYY-MM-DD HH:mm").valueOf();
				} else {
					x = moment(results[i]["date"],  "YYYY-MM-DD").hour(results[i]["hourRange"]).minute(0).valueOf();
				}
				if(j==0)
				{
					firstx.push(x);
				}
				xy.push(x);
				xy.push(value);
				list.push(xy);
			}
		}

		var chartSeries = [];
		for (var n = 0; n < dimensionVals.length; n++) {
			var item = {};
			item.type='column';
			item.name = dimensionVals[n];
			item.data = dimensionValsMap[dimensionVals[n]];

			chartSeries.push(item);
		}
		
		if(isRealTime && !isInit) {
			for (var n = 0; n < 2; n++) {
				var item = {};
				item.type = 'spline';
				if (n == 0) {
					item.name = 'DOD';
				} else if (n == 1) {
					item.name = 'WOW';
				}

				var list = [];
				var len=firstx.length;
				
				for(var i=0;i<len;i++) {
					var xy = [];
					xy.push(firstx[i]);
					xy.push(0);
					list.push(xy);
				}
				item.data = list;
				chartSeries.push(item);
			}
		}
		return chartSeries;
	}

	function generateSeriesTotalData(results) {
		var dimensionValsMap = {};
		var chartSeries = [];
		for (var i = 0; i < results.length; i++) {

			var item = {};
			var dimensionValue = results[i];
			if (isBlank(dimensionValue)) {
				dimensionValue = {};
			}
			var name=dimensionValue.name;
			var list = dimensionValsMap[name];
			if (isBlank(list)) {
				list = [];
				dimensionValsMap[name] = list;
			}

			for(var j=0;j<dimensionValue.totalDataEntityList.length;j++) {

				//此处value需要拆解
				var total=0;
				if(name=="DOD"||name=="WOW") {
					var jsonValue = JSON.parse(dimensionValue.totalDataEntityList[j].dimensionValue);
					jQuery.each(userDisplaySeries, function (name, value) {
						if (value && !isBlank(jsonValue) && !isBlank(jsonValue[name])) {
							total += jsonValue[name];
						}
					});
				}else
				{
					total=dimensionValue.totalDataEntityList[j].totalValue;
				}
				var value =total; //dimensionValue.totalDataEntityList[j].totalValue;
				var dateTime=dimensionValue.totalDataEntityList[j].displayTime;
				
				if (isBlank(value)) {
					value = 0;
				}
				var xy = [];
				var x = 0;
				x = moment(dateTime, "YYYY-MM-DD HH:mm").valueOf();
				xy.push(x);
				xy.push(value);
				list.push(xy);
			}
			item.type=dimensionValue.type;
			item.name=dimensionValue.name;
			item.data = dimensionValsMap[name];
			chartSeries.push(item);
		}
		return chartSeries;
	}

	function getTime(date) {
		var time = moment(date);
		time.format("HH:mm");
		time = time.add('minutes', -1);
		var datetime = new Date();
		datetime.setTime(time);
		var hour = datetime.getHours();
		var minute = datetime.getMinutes();
		return hour+":"+minute;
	}
	
	
	$(".btn-group").find(":radio").each(function()  {
		$(this).change(function() {
			realTimeMetricChange = true;
			connect();
			requestComparisonData();
		});
	});
	
	$('a[data-target="#tab-realtime"]').on('shown.bs.tab', function(e) {
		$('#realtimechart').highcharts().reflow();
    });

	$('a[data-target="#tab-comparison"]').on('shown.bs.tab', function(e) {
		$('#comparisonChart').highcharts().reflow();
    });

	 $("#warnningButton").click(function() {
		 $("#warningMsg").hide();
	 });
	
	 
	 //---------------------------------------------------------------------------------------------
	 // 添加比对数据
	 //---------------------------------------------------------------------------------------------
	 $("#comparisonDate").datepicker({
		 format:'yyyy-mm-dd',
		 startView : 'month',
		 multidate: true,
		 multidateSeparator:','
	 });
	 
	 var nowDate = new Date();
	 var yesterday = new Date(moment().add(-1, 'days').format("YYYY-MM-DD"));
	 
	 $('#comparisonDate').datepicker('setDate', [yesterday, nowDate]);
	 $('#comparisonDate').datepicker('update');
	 
	 function getSelectedDimensionValue() {
		 var selected = "";
		 $("#dimensionValuesRadio").find(":radio").each(function() {
			 if($(this).prop("checked")) {
				 selected = $(this).val();
				 return ;
			 }
		 });
		 
		 return selected;
	 }
	 
	 var seriesResults = null;
	 $("#dimensionValuesRadio").find(":radio").change(function() {
		 var seriesOptions = generateSeries(seriesResults);
		 createComparisonChart(seriesOptions);
	 });
	 
	 $("#dataComparison").click(function() {
		 requestComparisonData();
	 });
	 
	 // 默认选中当天，初始化数据
	 requestComparisonData();
	 
	 function requestComparisonData() {
		var newParameters = $.extend(true, {}, navParameters);

		newParameters.date = $("#comparisonDate").val();
		newParameters.metric = getCheckedMetric();
		newParameters = JSON.stringify(newParameters);

		$.ajax({
			url : 'comm/getcomparisondata',
			type : 'post',
			data : "parameter=" + newParameters,
			dataType : 'json',
			success : function(results) {
				seriesResults = results;
				var seriesOptions = generateSeries();
				createComparisonChart(seriesOptions);

				// 生成table
				var e = {};
				// 默认最大的取值范围 与后台的一直
				e.min = moment("2100-01-01 00:00", "YYYY-MM-DD HH:mm");
				e.max = moment("2100-01-01 23:59", "YYYY-MM-DD HH:mm");
				var data = generateSummarizeData(e);
				generateComparisonTable(data, e);

			},
			error : function() {
				//alert();
			}
		});
	}
	 
	 function generateSeries() {
		 var selected = getSelectedDimensionValue();
		 var item = seriesResults[selected];
			
		var	seriesOptions = [];
			$.each(item, function(key, value) {
				var obj = {};
				obj.name = key;
				obj.data = value;
				seriesOptions.push(obj);
			});
		return seriesOptions;
	 }
	 
	function createComparisonChart(seriesOptions) {
			$('#comparisonChart').highcharts('StockChart', {
			    chart: {
			    	type : 'spline',
					borderColor : '#EBBA95',
					borderWidth : 1
			    },
			    legend : {
			    	enabled:true
			    },
			    rangeSelector: {
			    	//inputEnabled: false, 
			    	buttonSpacing: 20,
			    	inputDateFormat: '%H:%M',
			    	inputEditDateFormat: '%H:%M',
			        selected: 4,

			        buttons: [{
				    	type: 'minute',
				    	count: 15,
				    	text: '15分钟'
				    }, {
				    	type: 'minute',
				    	count: 30,
				    	text: '30分钟'
				    }, {
				    	type: 'minute',
				    	count: 45,
				    	text: '45分钟'
				    }, {
				    	type: 'minute',
				    	count: 60,
				    	text: '1小时'
				    }, {
				    	type: 'all',
				    	text: '全天'
				    }],
				    buttonTheme: {
		                width: 50
		            },
			    },
			    xAxis: {
		           // type: 'datetime',
		            dateTimeLabelFormats: {
		                second: '%H:%M:%S',
		                minute: '%H:%M',
		                hour: '%H:%M',
		                day: '%H:%M',
		                week: '%H:%M',
		                month: '%H:%M',
		                year: '%H:%M'
		            },
		            events: {
		            	afterSetExtremes: function(e) {
		            		var data = generateSummarizeData(e);
		            		generateComparisonTable(data, e);
		            	}
		            }
		        },
		        
			    yAxis: {
			    	allowDecimals : false,
					title : {
						text : ''
					},
					min : 0
			    },
			    navigator: {
			    	height:30,
		            handles: {
		                backgroundColor: 'yellow',
		                borderColor: '#03a9f4'
		            },
		            margin:10
			    },
			    tooltip: {
			    	useHTML: true,
			    	formatter: function() {
		                var s = '<b>' + Highcharts.dateFormat("%H:%M", this.x) + '</b>';
		                $.each(this.points, function () {
		                    s += "<br><span style='color:" + this.series.color+"'>" + this.series.name + "</span>: " + "<b>"+ Math.ceil(this.y)+"</b>";
		                });
		                return s;
			    	}
			    },
			    credits:{
			    	enabled : false
			    },
			    series: seriesOptions
			});
		}
	 
	 function generateSummarizeData(e) {
		 var min = e.min;
		 var max = e.max;
		 
		 min = moment(min).seconds(0).milliseconds(0).valueOf();
		 
		 var result = {};
		 $.each(seriesResults, function(key, value) {
			 
			 //var obj = {};
			 $.each(value, function(key1, value1) {
				 var map1 = result[key1];
				 if(isBlank(map1)) {
					 map1 = {};
					 result[key1] = map1;
				 }
				 
				 var total = 0;
				 $.each(value1, function(key2, value2) {
					 if(value2[0]>=min && value2[0] <= max) {
						 total = total + value2[1];
					 }
				 });
				 
				 map1[key] = total;
			 });
		 });
		 
		 return result;
	 }
	 
	 function generateComparisonTable(data, e) {
		 var dimension = navParameters["dimension"];
		 var legends = eval(dimension.dimensionValues);

		 
		 var minHHmm = moment(e.min).format("HH:mm");
		 var maxHHmm = moment(e.max).format("HH:mm");
		 
		 var thead = "<tr><th class='text-center' style='vertical-align:middle'>时间:("+ minHHmm +"-" + maxHHmm + ")</th>";
		 for(var i=0; i<legends.length; i++) {
			 thead = thead + "<th class='text-center'>"+legends[i] +"</th>";
		 }
		 
		 thead = thead + "<th>总计</th></tr>";
		 
		 $("#comparisonThead").html(thead);
		 
		 var tbody = "";
		 $.each(data, function(key0, value0){
			 tbody = tbody + "<tr><td class='text-center'>" +  key0 + "</td>";
			
			 //var total = 0;
			 for(var j=0; j<legends.length; j++) {
				 
				 var val = value0[legends[j]];
				 if(isBlank(val)) {
					 val = 0;
				 }
				 tbody = tbody + "<td class='text-center'>" + val +"</td>";
			 }
			 
			 tbody = tbody + "<td>"+ value0["total"] + "</td></tr>";
		 });
		 
		 $("#comparisonTbody").html(tbody);
	 }
	 
});