$(function() {
	
	$.fn.editable.defaults.mode = 'popup';
	 $('a[data-action="displayname"]').editable({
		 //url:"metric/updatedisname",
		 params: function(params) {
			    params.displayName = params.value;
			    return params;
			}
	 });
	 $('a[data-action="order"]').editable({
		 //url:"metric/updatedisname",
		 params: function(params) {
			    params.order = params.value;
			    return params;
			}
	 });
	 $('a[data-action="formula"]').editable({
		 //url:"metric/updatedisname",
		 params: function(params) {
			    params.formula = params.value;
			    return params;
			}
	 });

	$('a[class="table-link danger"]').tooltip();
	
	$('a[class="table-link danger"]').click(function() {

		var id = $(this).prop("id");
		var tr = $(this).parent().parent();
		$.ajax({
			url : 'sysconf/delmetric',
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

	$("#btnsavmetric").click(function() {
		var parameter = $("#formsavemetric").serialize();
		$.ajax({
			url : 'sysconf/addmetric',
			type : 'post',
			data : parameter,
			dataType : 'json',
			success : function(results) {
				window.location.reload();
			},
			error : function() {

			}
		});
	});
});