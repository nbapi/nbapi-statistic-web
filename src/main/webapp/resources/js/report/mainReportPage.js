$(function() {
	$('#datetimepicker').datepicker({
		autoclose: true,
		format: "yyyymmdd",
	}).on('changeDate', function(){
		refreshPage();
	});
	
	var daysago = new Date(moment().format("YYYY-MM-DD"));
	$('#datetimepicker').datepicker('setDate', daysago);
});
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
	} 

function refreshPage(){
	var ds = $("#datetimepicker").val();
	var id=getQueryString("id");
	$.ajax({
		url : 'report/generalReport/getRptData',
		type : 'get',
		data : "ds=" + ds+"&id="+id,
		dataType : 'json',
		success : function(result) {
			initTable(result.title, result.data);
		},
		error : function() {
		}
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