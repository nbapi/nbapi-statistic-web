$(function() {
	$("#queryBtn").click(function() {
		var sql = $("#sql").val();
		refreshPage(sql);
	});
});

function refreshPage(sql){
	$("#queryBtn").text("执行中...");
	$("#queryBtn").attr("disabled", true);
	$.ajax({
		url : 'ia/exesql',
		type : 'get',
		data : "sql=" + sql,
		dataType : 'json',
		success : function(result) {
			if (result.error != null){
				alert(result.error);
			}else{
				initTable(result.title, result.data);
			}
			$("#queryBtn").text("查询");
			$("#queryBtn").attr("disabled", false);
		},
		error : function(xmlr, textStatus, errorThrown) {
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