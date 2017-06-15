$(function() {
	$("#a-home").click();
	
	$("#oseldate").bind("change",function(){
	    var dateval = $(this).val();
	    refreshordertable(dateval);
	});

	refreshordertable("");

});

function refreshordertable(datesel){
	$.ajax({
		url : 'daily/ordercountdata',
		type : 'get',
		data : "countdate=" + datesel,
		dataType : 'json',
		success : function(results) {
			createordertable(results.title, results.data);
		},
		error : function() {
		}
	});
}

function createordertable(title,data){
	var theadcompare = "<tr>";
	$.each(title, function(index, value) {
		theadcompare = theadcompare + "<th class='text-center'>" + value + "</th>";
	});
	theadcompare = theadcompare + "</tr>";
	$("#otheadtitle").html(theadcompare);
	
	var tbodycompare = "";
	$.each(data, function(index, line) {
		tbodycompare = tbodycompare + "<tr>";
		$.each(line, function(index, value) {
			tbodycompare = tbodycompare + "<td class='text-center'>" + value + "</td>";
		});
		tbodycompare = tbodycompare + "</tr>";
	});
	
	$("#otbodydata").html(tbodycompare);
}




