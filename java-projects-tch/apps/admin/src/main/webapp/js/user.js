var $grid, colModel;
var currUserCount = userConfigurationJson.length;

function userBootstrap() {
	setNumElements();
	initializeGrid();
	setupSearchHandlers();
};

function setNumElements() {
	$('#numUsers').html(currUserCount);
};

function initializeGrid() {
	$grid = $("#grid"), colModel;
	$("#grid")
			.jqGrid(
					{
						data : userConfigurationJson,
						datatype : 'local',
						width : 970,
						height : 100,
						emptyrecords : "Nothing to display",
						colNames : [ '', 'User ID', 'Roles', 'Issuer', 'IISN',
								'Id' ],
						colModel : [ {
							name : 'act',
							index : 'act',
							width : 50,
							sortable : false
						}, {
							name : 'userId',
							index : 'userId',
							width : 90,
							editable : true
						}, {
							name : 'userRoles',
							index : 'userRoles',
							width : 250,
							editable : true
						}, {
							name : 'issuerName',
							index : 'issuerName',
							width : 350,
							editable : true
						}, {
							name : 'iisn',
							index : 'iisn',
							hidden : true
						}, {
							name : 'id',
							index : 'id',
							key : true,
							hidden : true
						} ],
						onSortCol : function(index, idxcol, sortorder) {
							if (this.p.lastsort >= 0
									&& this.p.lastsort !== idxcol
									&& this.p.colModel[this.p.lastsort].sortable !== false) {
								// show the icons of last sorted
								// columnnot(checkbox)
								$(this.grid.headers[this.p.lastsort].el).find(
										">div.ui-jqgrid-sortable>span.s-ico")
										.show();
							}
						},
						gridComplete : function() {
							var ids = jQuery("#grid").jqGrid('getDataIDs');
							for ( var i = 0; i < ids.length; i++) {
								setupRow(ids[i]);
							}
							setupHandlers();
							if ($("#searchUserId").val().length > 0) {
								populateSearchContent();
							}
						},
						pager : '#pager',
						pgbuttons : true,
						altRows : true,
						altclass : 'myAltRowClass',
						viewrecords : true,
						sortorder : "asc",
						rowNum:10000000
					});
	displaySortIcons();
};

/*
 * Displaying rows in the grid based on values populated in the search User ID
 * textbox
 */
function setupSearchHandlers() {
	$("#searchBtn").click(function() {
		populateSearchContent();
	});
};

function populateSearchContent() {
	var rowData = $('#grid').jqGrid('getRowData');
	var rowDataLength = rowData.length;
	for ( var i = 0; i < rowData.length; ++i) {
		if (rowData[i].userId.toLowerCase().indexOf(
				$('#searchUserId').val().toLowerCase()) == -1) {
			$('#' + rowData[i].id).hide();
			rowDataLength--;
		} else {
			$('#' + rowData[i].id).show();
		}
	}
	$('#numUsers').html(rowDataLength);
};

function setupRow(cl) {
	a1 = "<input type='button' class='editbtn' id='editBtn_" + cl + "'/>";
	b1 = "<input type='button' class='delbtn' id='delBtn_" + cl + "'/>";
	$("#grid").jqGrid('setRowData', cl, {
		act : a1 + b1
	});
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

function addUser(title) {
	$('#isEdit').val('N');
	clearData();
	displayModal(title);
};

function handleEdit() {
	var myId = $(this).attr('id');
	$('#id').val(myId.split('_')[1]);
	$('#isEdit').val('Y');
	$.get(controllerContextPath + '/user/usermgmt', {
		'id' : $('#id').val()
	}, function(data) {
		if ($('#currentUserId').val() != data.userId) {
			// Add to Model. Call Save Changes.
			fillEditData(data);
			if (data.iisn != null && data.iisn != '') {
				displayModal('Edit Issuer User');
			} else {
				displayModal('Edit TCH User');
			}
		}
	});
};

function handleDelete() {
	var myId = $(this).attr('id');
	var idToUse = myId.split('_')[1];
	$('#id').val(idToUse);
	var rowData = $('#grid').jqGrid('getRowData', idToUse);
	if ($('#currentUserId').val() != rowData.userId) {
		var title = 'Delete TCH User'
		if (rowData.iisn != null && rowData.iisn != '') {
			title = 'Delete Issuer User';
		}
		displayDeleteModal(title);
	}
}

function setupHandlers() {
	$('#TCH_addTCHUser').off('click');
	$('#TCH_addTCHUser').click(function(e) {
		e.preventDefault();
		$('.issuerUser').hide();
		$('.tchUser').show();
		$('#isIssuerUser').val('N');
		addUser($(this).val());
	});
	$('#TCH_addIssuerUser').off('click');
	$('#TCH_addIssuerUser').click(function(e) {
		e.preventDefault();
		$('.tchUser').hide();
		$('.issuerUser').show();
		$('#isIssuerUser').val('Y');
		addUser($(this).val());
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
	$("input").off('keypress');
	$("input").keypress(function(event) {
		if (event.which == 13) {
			event.preventDefault();
			populateSearchContent();
		}
	});
};

function clearData() {
	$('#userId').val('');
	$('#id').val('');
	$('#userId').attr('disabled', false);
	$('#firstName').val('');
	$('#lastName').val('');
	$(':checked').each(function() {
		if ($(this).attr('name') == 'authorizedRoles') {
			$(this).attr('checked', false);
		}
	});
};

function fillEditData(data) {
	if (data.iisn != null && data.iisn != '') {
		$('.issuerUser').show();
		$('.tchUser').hide();
	} else {
		$('.issuerUser').hide();
		$('.tchUser').show();
	}
	$(':checked').each(function() {
		if ($(this).attr('name') == 'authorizedRoles') {
			$(this).attr('checked', false);
		}
	});
	$('#iisn').val(data.iisn);
	$('#userId').val(data.userId);
	$('#userId').attr('disabled', true);
	$('#id').val(data.id);
	$('#firstName').val(data.firstName);
	$('#lastName').val(data.lastName);
	for ( var i = 0; i < data.authorizedRoles.length; ++i) {
		$('#authorizedRoles_' + data.authorizedRoles[i]).attr('checked', true);
	}
	$('#tchUserRolesDiv').html($('#tchUserRolesDiv').html());
};

function saveChanges() {
	var myGrid = $("#grid");
	var isEdit = $('#isEdit').val();
	$(':disabled[type="text"]').attr('disabled', false);
	var iisn = $('#iisn').val();
	if (iisn != null && iisn != '') {
		$('#authorizedRoles_3').attr('checked', true);
	}
	var params = $('input,select').serialize();
	if (isEdit != 'N') {
		$('#userId').attr('disabled', true);
	}
	$
			.post(
					controllerContextPath + '/user/usermgmt',
					params,
					function(data) {
						$('#userIdErrorMsg').hide();
						$('#firstNameErrorMsg').hide();
						$('#lastNameErrorMsg').hide();
						$('#rolesErrorMsg').hide();
						$('#iisnErrorMsg').hide();
						if (data.success) {
							if (isEdit == 'N') {
								currUserCount++;
								setNumElements();
								myGrid.jqGrid('addRowData',
										data.userConfigurationProperties.id,
										data.userConfigurationProperties);
								setupRow(data.userConfigurationProperties.id);
								$('.editbtn').off('click').click(handleEdit);
								$('.delbtn').off('click').click(handleDelete);
							} else {
								myGrid
										.jqGrid(
												'setCell',
												data.userConfigurationProperties.id,
												'userRoles',
												data.userConfigurationProperties.userRoles);
								myGrid.jqGrid('getLocalRow',
										data.userConfigurationProperties.id).userRoles = data.userConfigurationProperties.userRoles;
								if (iisn != null && iisn != '') {
									var issuerName = $('#iisn :selected')
											.text();
									myGrid
											.jqGrid(
													'setCell',
													data.userConfigurationProperties.id,
													'issuerName', issuerName);
									myGrid
											.jqGrid(
													'getLocalRow',
													data.userConfigurationProperties.id).issuerName = issuerName;
								}
							}
							if (data.saveSuccessMsg != null && data.saveSuccessMsg != '') {
								$('#TCH-issuerMgmt #TCH_fieldMessage').html(
										data.saveSuccessMsg).show();
							}
							$.modal.close();
						} else {
							if (data.userIdErrorMsg != null
									&& data.userIdErrorMsg != '') {
								$('#userIdErrorMsg').html(data.userIdErrorMsg)
										.show();
							}
							if (data.firstNameErrorMsg != null
									&& data.firstNameErrorMsg != '') {
								$('#firstNameErrorMsg').html(
										data.firstNameErrorMsg).show();
							}
							if (data.lastNameErrorMsg != null
									&& data.lastNameErrorMsg != '') {
								$('#lastNameErrorMsg').html(
										data.lastNameErrorMsg).show();
							}
							if (data.rolesErrorMsg != null
									&& data.rolesErrorMsg != '') {
								$('#rolesErrorMsg').html(data.rolesErrorMsg)
										.show();
							}
							if (data.iisnErrorMsg != null
									&& data.iisnErrorMsg != '') {
								$('#iisnErrorMsg').html(data.iisnErrorMsg)
										.show();
							}
						}
					});
};

function deleteChanges() {
	$.post(controllerContextPath + '/user/usermgmt/delete', {
		'id' : $('#id').val()
	}, function(data) {
		if (data.success) {
			currUserCount--;
			setNumElements();
			$.modal.close();
			// /$('.TCH_labelValue').hide();
			if (data.success) {
				$('#grid').jqGrid('delRowData', $('#id').val());
			}
			if (data.deleteMessage != null && data.deleteMessage != '') {
				$('#TCH-issuerMgmt #TCH_fieldMessage').html(
						data.deleteMessage).show();
			}
			$('.editbtn').off('click').click(handleEdit);
			$('.delbtn').off('click').click(handleDelete);
		}
	});
};

function displayModal(title) {
	$('#userModalTitle').html('<h3>' + title + '</h3>');
	$(this).closest('tr').addClass('ui-state-highlight');
	$('#TCH_addUserModal').modal({
		dataCss : {
			height : "500px",
			width : "500px"
		}
	});
};

function displayDeleteModal(title) {
	$(this).closest('tr').addClass('ui-state-highlight');
	$('#delModalHeader').html('<h3>' + title + '</h3>');
	$('#TCH_delIssuerModal').modal({
		dataCss : {
			height : "210px",
			width : "500px"
		}
	});
};
