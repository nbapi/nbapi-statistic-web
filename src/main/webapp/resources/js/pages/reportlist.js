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
			url : 'sysconf/addreport',
			type : 'post',
			data : parameters,
			dataType : 'json',
			success : function(results) {
				window.location.reload();
			},
			error : function() {
			}
		});
	});
	
});