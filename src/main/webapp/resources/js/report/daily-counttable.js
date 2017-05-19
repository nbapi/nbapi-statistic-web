$(function() {
	$("#a-home").click();
	
	$("#seldate").bind("change",function(){
	    var dateval = $(this).val();
	    refreshtable(dateval);
	});
	
	$("#oseldate").bind("change",function(){
	    var dateval = $(this).val();
	    refreshordertable(dateval);
	});

	refreshtable("");
	refreshordertable("");

});

function refreshtable(datesel){
	$.ajax({
		url : 'daily/counttable',
		type : 'get',
		data : "countdate=" + datesel,
		dataType : 'json',
		success : function(results) {
			createtable(results.title, results.data);
		},
		error : function() {
		}
	});
}

function createtable(title,data){
	var theadcompare = "<tr>";
	$.each(title, function(index, value) {
		if (index == 2)
			theadcompare = theadcompare + "<th class='text-center btn-primary'>" + value + "</th>";
		else
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

function refreshordertable(datesel){
	$.ajax({
		url : 'daily/countorder',
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




