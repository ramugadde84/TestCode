var $grid, colModel;
var currTokenRequestorLength = tokenRequestorJson.length;

function tokenRequestorBootstrap() {
	setNumElements();
	initializeGrid();
};

function setNumElements() {
	$('#numTokenRequestors').html(currTokenRequestorLength);
};

function setupTokenRequestorRow(cl) {
	a1 = "<input type='button' class='editbtn' id='editBtn_" + cl + "'/>";
	b1 = "<input type='button' class='delbtn' id='delBtn_" + cl + "'/>";
	$("#grid").jqGrid('setRowData', cl, {
		act : a1 + b1
	});
};

function initializeGrid() {
	$grid = $("#grid"), colModel;
	$("#grid")
			.jqGrid(
					{
						data : tokenRequestorJson,
						datatype : 'local',
						width : 970,
						height : 120,
						emptyrecords : "Nothing to display",
						colNames : [ '', 'Token Requestor Name',
								'Token Requestor ID', 'ID' ],
						colModel : [ {
							name : 'act',
							index : 'act',
							width : 39,
							sortable : false
						}, {
							name : 'name',
							index : 'name',
							width : 140,
							editable : true
						}, {
							name : 'networkId',
							index : 'networkId',
							width : 560,
							editable : true
						}, {
							name : 'id',
							index : 'id',
							key : true,
							hidden : true,
							width : 140,
							editable : true
						} ],
						onSortCol : function(index, idxcol, sortorder) {
							if (this.p.lastsort >= 0
									&& this.p.lastsort !== idxcol
									&& this.p.colModel[this.p.lastsort].sortable !== false) {
								// show the icons of last sorted column
								$(this.grid.headers[this.p.lastsort].el).find(
										">div.ui-jqgrid-sortable>span.s-ico")
										.show();
							}
						},
						gridComplete : function() {
							var ids = jQuery("#grid").jqGrid('getDataIDs');
							for ( var i = 0; i < ids.length; i++) {
								var cl = ids[i];
								setupTokenRequestorRow(cl);
							}
							setupHandlers();
						},
						pager : '#pager',
						pgbuttons : true,
						altRows : true,
						altclass : 'myAltRowClass',
						viewrecords : true,
						sortorder : "asc",
						rowNum:10000000
					});
	//for sort symbols to do sorting. 
	displaySortIcons();
};

function displaySortIcons() {
	colModel = $grid.jqGrid('getGridParam', 'colModel');
	$(
			'#gbox_' + $.jgrid.jqID($grid[0].id)
					+ ' tr.ui-jqgrid-labels th.ui-th-column').each(
			function(i) {
				var cmi = colModel[i], colName = cmi.name;

				if (cmi.sortable !== false) {
					// show the sorting icons
					$(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
				} else if (!cmi.sortable && colName !== 'rn'
						&& colName !== 'cb' && colName !== 'subgrid') {
					// change the mouse cursor on the columns which are
					// non-sortable
					$(this).find('>div.ui-jqgrid-sortable').css({
						cursor : 'default'
					});
				}
			});
};

function handleEdit() {
	var myId = $(this).attr('id');
	$('#id').val(myId.split('_')[1]);
	$
			.get(
					controllerContextPath + '/tokenRequestor',
					{
						'id' : $('#id').val()
					},
					function(data) {
						if (data != null && data != '') {
							fillEditData(data);
							displayModal('Edit Token Requestor');
						} else {
							displayEditErrorModal('Edit Token Requestor');
							$('#editErrorMsg')
									.html(
											'An error occured while attempting to edit token requestor');
						}
					});
};

function handleDelete() {
	var myId = $(this).attr('id');
	$('#id').val(myId.split('_')[1]);
	displayDeleteModal();
};

function clearData() {
	$('#id').val('');
	$('#name').val('');
	$('#networkId').val('');
};

function setupHandlers() {
	$('#TCH_addTokenBtn').off('click');
	$('#TCH_addTokenBtn').click(function() {
		clearData();
		displayModal('Add Token Requestor');
	});
	$('#TCH_saveChange').off('click');
	$('#TCH_saveChange').click(function() {
		saveChanges();
	});

	$('.editbtn').off('click');
	$('.delbtn').off('click');
	$('.editbtn').click(handleEdit);
	$('.delbtn').click(handleDelete);

	$('#TCH_delUser').off('click');
	$('#TCH_delUser').click(function() {
		deleteChanges();
	});
	$('#TCH_cancelDelete').off('click');
	$('#TCH_cancelDelete').click(function() {
		$.modal.close();
	});
};

function fillEditData(data) {
	$('#name').val(data.name);
	$('#networkId').val(data.networkId);
};

function saveChanges() {
	$('#TCH-tokenRequestorMgmt #TCH_fieldMessage').hide();
	$('#TCH-tokenRequestorMgmt #TCH_fieldError').hide();
	var myGrid = $("#grid");
	var params = getAllFormElementsById();
	var id = $('#id').val();
	$("#nameErrorMsg").html('');
	$("#networkIdErrorMsg").html('');
	var isEdit = (id != '');
	$
			.post(
					controllerContextPath + '/tokenRequestor',
					params,
					function(data) {
						if (data.success) {
							if (!isEdit) {
								currTokenRequestorLength++;
								setNumElements();
								myGrid.jqGrid('addRowData',
										data.tokenRequestor.id,
										data.tokenRequestor);
								setupTokenRequestorRow(data.tokenRequestor.id);

							} else {
								myGrid.jqGrid('setCell', id, 'name',
										data.tokenRequestor.name);
								myGrid.jqGrid('setCell', id, 'networkId',
										data.tokenRequestor.networkId);
								myGrid.jqGrid('getLocalRow', id).name = data.tokenRequestor.name;
								myGrid.jqGrid('getLocalRow', id).networkId = data.tokenRequestor.networkId;
							}
							if (data.saveSuccessMsg != null && data.saveSuccessMsg != '') {
								$('#TCH-tokenRequestorMgmt #TCH_fieldMessage').html(
										data.saveSuccessMsg).show();
							}
							$('.editbtn').off('click');
							$('.delbtn').off('click');
							$('.editbtn').click(handleEdit);
							$('.delbtn').click(handleDelete);
							$.modal.close();
						} else {
							if (data.nameErrorMsg != null
									&& data.nameErrorMsg != '') {
								$('#nameErrorMsg').html(data.nameErrorMsg)
										.show();
							}
							if (data.networkIdErrorMsg != null
									&& data.networkIdErrorMsg != '') {
								$('#networkIdErrorMsg').html(
										data.networkIdErrorMsg).show();
							}
							if (data.errorMessage != null
									&& data.errorMessage != '') {
								$('#TCH-tokenRequestorMgmt #TCH_fieldError')
										.html(data.errorMessage).show();
								$('.editbtn').off('click');
								$('.delbtn').off('click');
								$('.editbtn').click(handleEdit);
								$('.delbtn').click(handleDelete);
								$.modal.close();
							}
						}
					});
};

function deleteChanges() {
	$('#TCH-tokenRequestorMgmt #TCH_fieldMessage').hide();
	$('#TCH-tokenRequestorMgmt #TCH_fieldError').hide();
	var id = $('#id').val();
	var networkId = $('#grid').jqGrid('getCell', id, 2);
	$.post(controllerContextPath + '/tokenRequestor/delete', {
		'id' : id,
		'networkId' : networkId
	}, function(data) {
		if (data.success) {
			currTokenRequestorLength--;
			setNumElements();
			$('#grid').jqGrid('delRowData', id);
			$('#TCH_delUser').hide();
			$('#TCH_cancelDelete').hide();
			$('#delMsg').hide();
			if (data.deleteMessage != null && data.deleteMessage != '') {
				$('#TCH-tokenRequestorMgmt #TCH_fieldMessage').html(
						data.deleteMessage).show();
			}
			$('.editbtn').off('click');
			$('.delbtn').off('click');
			$('.editbtn').click(handleEdit);
			$('.delbtn').click(handleDelete);
			$.modal.close();
		} else {
			if (data.errorMessage != null && data.errorMessage != '') {
				$('#TCH-tokenRequestorMgmt #TCH_fieldError').html(
						data.errorMessage).show();
				$('.editbtn').off('click');
				$('.delbtn').off('click');
				$('.editbtn').click(handleEdit);
				$('.delbtn').click(handleDelete);
				$.modal.close();
			}
		}
	});
}

function displayModal(title) {
	$('#tokenRequestorModalTitle').html('<h3>' + title + '</h3>');
	$.get(controllerContextPath + '/tokenRequestor', {
		'id' : $('#id').val()
	}, function(data) {
		$(this).closest('tr').addClass('ui-state-highlight');
		$('#TCH_addTokenModal').modal({
			dataCss : {
				height : "300px",
				width : "500px"
			}
		});
	});
};

function displayDeleteModal() {
	$('#TCH_delUser').show();
	$('#TCH_cancelDelete').show();
	$('#delMsg').show();
	$('#deleteMessage').hide();
	$.get(controllerContextPath + '/tokenRequestor', {
		'id' : $('#id').val()
	}, function(data) {
		$(this).closest('tr').addClass('ui-state-highlight');
		$('#TCH_delTokenModal').modal({
			dataCss : {
				height : "210px",
				width : "500px"
			}
		});
	});
};
function displayEditErrorModal(title) {
	$('#tokenRequestorEditModalTitle').html('<h3>' + title + '</h3>');
	$(this).closest('tr').addClass('ui-state-highlight');
	$('#TCH_editTokenModal').modal({
		dataCss : {
			height : "250px",
			width : "500px"
		}
	});
};
