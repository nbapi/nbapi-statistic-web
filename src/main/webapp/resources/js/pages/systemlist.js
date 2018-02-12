$(function() {
	
	$("[data-toggle='tooltip']").tooltip();
	
	$.fn.editable.defaults.mode = 'popup';
	 $('a[data-action="systemName"]').editable({
		 params: function(params) {
			    params.systemName = params.value;
			    return params;
			}
	 });
	 $('a[data-action="businessLine"]').editable({
		 params: function(params) {
			    params.businessLine = params.value;
			    return params;
			}
	 });
	 $('a[data-action="contactName"]').editable({
		 params: function(params) {
			    params.contactName = params.value;
			    return params;
			}
	 });
	 $('a[data-action="department"]').editable({
		 params: function(params) {
			    params.department = params.value;
			    return params;
			}
	 });

	$('a[data-action="delete"]').click(function() {

		var id = $(this).prop("id");
		var tr = $(this).parent().parent();
		$.ajax({
			url : 'sysconf/delsystem',
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

	
	$("#btnsavesystem").click(function() {
		var parameters = $("#formsavesystem").serialize();
		
		$.ajax({
			url : 'sysconf/addsystem',
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