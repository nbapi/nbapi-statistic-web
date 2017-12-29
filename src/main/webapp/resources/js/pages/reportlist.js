$(function() {
	
	$("[data-toggle='tooltip']").tooltip();
	
	$.fn.editable.defaults.mode = 'popup';
	 $('a[data-action="dimensionName"]').editable({
		 params: function(params) {
			    params.dimensionName = params.value;
			    return params;
			}
	 });
	 $('a[data-action="localName"]').editable({
		 params: function(params) {
			    params.localName = params.value;
			    return params;
			}
	 });
	 $('a[data-action="reportSQL"]').editable({
		 params: function(params) {
			    params.reportSQL = params.value;
			    return params;
			}
	 });
	 $('a[data-action="reportCols"]').editable({
		 params: function(params) {
			    params.reportCols = params.value;
			    return params;
			}
	 });
	 $('a[data-action="aliasNames"]').editable({
		 params: function(params) {
			    params.aliasNames = params.value;
			    return params;
			}
	 });

	$('a[data-action="delete"]').click(function() {

		var id = $(this).prop("id");
		var tr = $(this).parent().parent();
		$.ajax({
			url : 'sysconf/deletereport',
			type : 'post',
			data : "id=" + id,
			dataType : 'json',
			success : function(results) {
				$(tr).remove();
			},
			error : function() {
				alert();
			}
		});
	});

	
	$("#btnsavereport").click(function() {
		var parameters = $("#formsavereport").serialize();
		$.ajax({
			url : 'sysconf/addreport0',
			type : 'post',
			async:false,
			data : parameters,
			dataType : 'json',
			success : function(results) {
				window.location.reload();
			},
			error : function(e) {
				alert(e);
			}
		});
	});
	
	
	$('a[data-action="reportLeftNav"]').click(function() {
		$.ajax({
			url : 'sysconf/reportLeftNav',
			async:false,
			dataType : 'json',
			success : function(results) {
				$("#reportLeftNav").empty();
				$.each(results,function(i,result){
					$("#reportLeftNav").append("<li><a href='/report/prodsummary/rptPage?id="+result["id"]+"&localName="+result["localName"]+"&dimensionName="+result["dimensionName"]+"' class ='active'>"+result["dimensionName"]+"</a></li>")
				});
			},
			error : function(e) {
				alert(JSON.stringify(e));
			}
		});
	});
	
	
});