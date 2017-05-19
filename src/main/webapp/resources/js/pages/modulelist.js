$(function() {
	
	$("[data-toggle='tooltip']").tooltip();
	
	$.fn.editable.defaults.mode = 'popup';
	 $('a[data-action="itemName"]').editable({
		 params: function(params) {
			    params.itemName = params.value;
			    return params;
			}
	 });
	 $('a[data-action="businessType"]').editable({
		 params: function(params) {
			    params.businessType = params.value;
			    return params;
			}
	 });

	$('a[data-action="delete"]').click(function() {

		var id = $(this).prop("id");
		var tr = $(this).parent().parent();
		$.ajax({
			url : 'sysconf/delmodule',
			type : 'post',
			data : "id=" + id,
			dataType : 'json',
			success : function(results) {
				$(tr).remove();
			},
			error : function() {
				//alert();
			}
		});
	});

	
	$("#btnsavemodule").click(function() {
		var parameters = $("#formsavemodule").serialize();
		
		$.ajax({
			url : 'sysconf/addmodule',
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