function binMappingBootstrap() {
	var $grid, colModel;
	setupHandlers();
};

function editHandlers(){
	$(".editbtn").click(function (){
		editData($(this).attr('id').split('_')[1]);
	});
}

function setupHandlers() {
	$('#binMapping').click(function() {
		$('#realBin').val("");
		$('#tokenBin').val("");
		$('#binMappingId').val("");
		displayModal('Add Mapping');
	});
	
}

function displayModal(title) {
	$('#binMappingModalTitle').html('<h3>' + title + '</h3>');

	$(this).closest('tr').addClass('ui-state-highlight');
	$('#TCH_saveIssuerModal').modal({
		dataCss : {
			height : "300px",
			width : "500px"
		}
	});
};

function setNumElements() {
	$('#numBinMappings').html(numBinMappings);
};

function saveBinMappingDetails() {
	clearAllErrMsg();
	var myGrid = $("#grid");
	var params = getAllFormElementsById();
	params['id']=params['binMappingId'];
	var id = $('#binMappingId').val();
	var isEdit = (id != '');
	$.post(
					controllerContextPath + '/issuer/mapping',
					params,
					function(data) {
						if (data.success) {
							if (!isEdit) {
								numBinMappings++;
								setNumElements();
								try {
									myGrid
											.jqGrid('addRowData',
													data.binMapping.id,
													data.binMapping);
								} catch (obj) {
									// Pass
								}
								var ids = jQuery("#grid").jqGrid('getDataIDs');
								for ( var i = 0; i < ids.length; i++) {
									var cl = ids[i];
									a11 = "<input type='button' class='editbtn' id='editBtn_"
											+ cl
											+ "' onclick='editData("
											+ cl
											+ ")' />";
									jQuery("#grid").jqGrid('setRowData',
											ids[i], {
												act : a11
											});
								}
								$("#TCH_edit_tckns").html("<a href='#'>Edit Token Requestor/Token Rules</a>");
							} else {
								try {
									myGrid.jqGrid('setCell', id, 'realBin',
											data.binMapping.realBin);
									myGrid.jqGrid('getLocalRow', id).realBin = data.binMapping.realBin;
								} catch (obj) {
									// Pass
								}
								try {
									myGrid.jqGrid('setCell', id, 'tokenBin',
											data.binMapping.tokenBin);
									myGrid.jqGrid('getLocalRow', id).tokenBin = data.binMapping.tokenBin;
								} catch (obj) {
									// Pass
								}

							}
							$.modal.close();
						} else {
							if (data.realBinErrMsg != null
									&& data.realBinErrMsg != '') {
								$('#realBinErrMsg').html(data.realBinErrMsg)
										.show();
							}
							if (data.tokenBinErrMsg != null
									&& data.tokenBinErrMsg != '') {
								$('#tokenBinErrMsg').html(data.tokenBinErrMsg)
										.show();
							}
						}
					});
};

function clearAllErrMsg() {
	$('#realBinErrMsg').html(" ");
	$('#tokenBinErrMsg').html(" ");
}

/**
 * this will take primary key and it will send to controller.
 */
function editData(id) {
	$('#binMappingId').val(id);
	$.get(controllerContextPath + '/issuer/mapping', {
		'id' : $('#binMappingId').val(),'iisn':$('#iisn').val()
	}, function(data) {
		populateEditData(data);
		displayModal('Edit Mapping');
	});
}

function populateEditData(data) {
	$('#realBin').val(data.realBin);
	$('#tokenBin').val(data.tokenBin);
};