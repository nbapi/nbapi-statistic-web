
$(function() {
	
	$("[data-toggle='tooltip']").tooltip();
	
	$.fn.editable.defaults.mode = 'popup';
	 $('a[data-action="localName"]').editable({
		 params: function(params) {
			    params.localName = params.value;
			    return params;
			}
	 });
	 $('a[data-action="dimensionName"]').editable({
		 params: function(params) {
			    params.dimensionName = params.value;
			    return params;
			}
	 });
	 
	 $('a[data-action="displayType"]').editable({
		 params: function(params) {
			    params.displayType = params.value;
			    return params;
			}
	 });
	 $('a[data-action="dimensionCount"]').editable({
		 params: function(params) {
			    params.dimensionCount = params.value;
			    return params;
			}
	 });
	 
	 $('a[data-action="dimensionValues"]').editable({
		 params: function(params) {
			    params.dimensionValues = params.value;
			    return params;
			}
	 });
	 
	 $('a[data-action="displayOrder"]').editable({
		 params: function(params) {
			    params.displayOrder = params.value;
			    return params;
			}
	 });
	
	$('a[data-action="delete"]').click(function() {

		var id = $(this).prop("id");
		var tr = $(this).parent().parent();
		$.ajax({
			url : 'sysconf/deldimension',
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
	
	// 取得所有的度量值
	$.ajax({
		url : 'sysconf/getAllMetrics',
		type : 'post',
		data : "",
		dataType : 'json',
		success : function(results) {
			
			var content = "";
			
			for(var i=0; i<results.length; i++) {
				var value = results[i];
				
				if(i % 4 == 0) {
					content = content + "<div class='row'>";
				}
				
				var input = "<input type='checkbox' id='"+ value.id +"'> <label for='" +value.id +"'>" + value.displayName + "</label>";
				input ="<div class='col-md-3'><div class='checkbox-nice checkbox-inline'>" + input + "</div></div>";
				
				content = content + input ;
				
				if(((i+1) % 4 == 0) || ((i == results.length-1) && ((i+1) % 4 == 0))) {
					content = content + "</div>";
				}
			}
			
			$("#modalbody").html(content);
		},
		error : function() {
			alert("");
		}
	});

	
	$("#btnsavedimension").click(function() {
		var parameters = $("#formsavedimension").serialize();
		
		$.ajax({
			url : 'sysconf/adddimension',
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
	
	var currentDimensionId = "";
	$('a[data-action="showrelation"]').click(function() {

		var id = $(this).prop("id");
		currentDimensionId = id;
		$.ajax({
			url : 'sysconf/getdimenmetricrelation',
			type : 'post',
			data : "dimensionId=" + id,
			dataType : 'json',
			success : function(results) {
				$("#modalbody").find(":checkbox").each(function() {
					
					var id = $(this).prop("id");
					var used = false;
					
					$.each(results, function(index, value) {
						if(id == value.metricId) {
							used = true;
						}
					});
					
					if(used) {
						$(this).prop("checked", "checked");
					} else {
						$(this).removeAttr("checked");
					}
				});
			},
			error : function() {
				alert("");
			}
		});
		
		$("#dimenMetricRelationModal").modal('show');
	});

	$("#btnsaverelation").click(function() {
		var checkedItems = [];
		$("#modalbody").find(":checkbox").each(function() {
			
			if($(this).prop("checked")) {
				checkedItems.push($(this).prop("id"));
			}
			
		});
		
		var parameter = "dimensionId=" + currentDimensionId + "&metricIds=" + checkedItems;
		
		$.ajax({
			url : 'sysconf/updaterelation',
			type : 'post',
			data : parameter,
			dataType : 'json',
			success : function(results) {
				$("#dimenMetricRelationModal").modal('hide');
				showMessage("<div class='ns-box-inner'><span class='fa fa-bullhorn fa-2x pull-left'></span><p>关联关系更新成功!</p></div>");
			},
			error : function() {
				alert("");
			}
		});
	});
	
});