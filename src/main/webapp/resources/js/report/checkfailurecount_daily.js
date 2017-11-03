$(function() {
	$("#a-home").click();
	
	$("#seldate").bind("change",function(){
	    var dateval = $(this).val();
	    refreshtable(dateval);
	});

	refreshtable("");

});

function refreshtable(datesel){
	$.ajax({
		url : '../daily/checkfailurecountdata',
		type : 'get',
		data : "countdate=" + datesel,
		dataType : 'json',
		success : function(results) {
			createtable(results.data);
		},
		error : function() {
		}
	});
}

function createtable(data){

	var tbodycompare = "";
	$.each(data, function(key, lines) {
		tbodycompare = tbodycompare + "<tr>";
		tbodycompare = tbodycompare + "<td class='text-center' rowspan='" + lines.length +"'>" + key + "</td>";
		$.each(lines, function(index, line) {
			if (index != 0) tbodycompare = tbodycompare + "<tr>";
			tbodycompare = tbodycompare + "<td>" + line[0] + "</td>";
			tbodycompare = tbodycompare + "<td class='text-center'>" + line[1] + "</td>";
			tbodycompare = tbodycompare + "</tr>";
		});
	});
	
	$("#tbodydata").html(tbodycompare);
}





