var gridData = null;
var iisn = $('#iisn').val();
function customerManagementBootstrap() {
	customerManagementGrid();
	$('#lockUnlockConfirm').click(lockUnlockUser);
	$('#TCH_saveChange').click(function() {
		$.modal.close();
	});
	$('#lockUnlockCancel').click(function() {
		$.modal.close();
	});
	$("#srcissn").click(function() {
		$("#TCH_userid").val("");
		iisn = $("#iisn option:selected").val();
		$("#grid").jqGrid('GridUnload');
		$('#numCustomers').html(0);
		customerManagementGrid();
	});
	$("input").keypress(function(event) {
	    if (event.which == 13) {
	        event.preventDefault();
	        populateSearchContent();
	    }
	});
};

function customerManagementGrid() {
	$.get(controllerContextPath + '/user/customer', {
		'iisn' : iisn
	}, function(data) {
		gridData = data;
		initializeGrid();
		setupResetHandlers();
		setupLockHandlers();
		setupSearchHandlers();
		displaySortIcons();
		$('#numCustomers').html(gridData.length);
	});

};

function setupLockHandlers() {
	$('.lockUnlockBtn').off('click');
	$('.lockUnlockBtn').each(function() {
		var myId = $(this).attr('id').split('_')[1];
		var localRow = $("#grid").jqGrid('getLocalRow', myId);

		if (localRow.locked) {
			$(this).click(function() {
				$('#id').val(myId);
				unlock();
			});
		} else {
			$(this).click(function() {
				$('#id').val(myId);
				lock();
			});
		}

	});
};

function lock() {
	$('#url').val('lock');
	$('#lockUnlockMsg').html('lock');
	displayConfirmationModal();
};

function unlock() {
	$('#url').val('unlock');
	$('#lockUnlockMsg').html('unlock');
	displayConfirmationModal();
};

function setupResetHandlers() {
	$('.unlockresetbtn').click(function() {
		var id = $(this).attr('id').split('_')[1];
		$.post(controllerContextPath + '/user/customer/resetPassword', {
			'iisn' : $('#iisn').val(),
			'id' : id
		}, function(data) {
			if (data.success) {
				displayModal(data);
			} else {
				// TODO failure logic
				console.log(data);
			}
		});

	});
};

function setupRepaintHandlers() {
	$('.unlockresetbtn').off('click');
	$('.lockresetbtn').off('click');
	setupResetHandlers();
	setupLockHandlers();
};

function lockUnlockUser() {
	var myId = $('#id').val();
	var url = $('#url').val();

	$.post(controllerContextPath + '/user/customer/' + url, {
		'id' : myId,
		'iisn' : $('#iisn').val(),
		'userId' : $('#userId').val()
	}, function(data) {
		if (data.success) {
			if (url == 'lock') {
				$('#lockbtn_' + myId).removeClass('unlockbtn').addClass(
						'lockbtn');
				$('#resetbtn_' + myId).attr('disabled', true);
				$('#grid').jqGrid('getLocalRow', myId).locked = true;
			} else {
				$('#lockbtn_' + myId).removeClass('lockbtn').addClass(
						'unlockbtn');
				$('#grid').jqGrid('getLocalRow', myId).locked = false;
				$('#resetbtn_' + myId).attr('disabled', false).addClass(
						'unlockresetbtn');
			}
			setupRepaintHandlers();
		}
		$.modal.close();
	});
};

function initializeLockButtons(cl) {
	var c1, d1;
	var locked = $("#grid").jqGrid('getLocalRow', cl).locked;
	if (locked) {
		c1 = "<input type='button' class='lockbtn lockUnlockBtn' id='lockbtn_"
				+ cl + "'/>";
		d1 = "<input type='button' class='lockresetbtn' id='resetbtn_" + cl
				+ "' value='Reset' disabled='disabled' />";
	} else {
		c1 = "<input type='button' class='unlockbtn lockUnlockBtn' id='lockbtn_"
				+ cl + "'/>";
		d1 = "<input type='button' class='unlockresetbtn unlock_btn' id='resetbtn_"
				+ cl + "' value='Reset' />";
	}
	jQuery("#grid").jqGrid('setRowData', cl, {
		act : c1 + d1
	});
};

function initializeGrid() {
	var $grid = $("#grid"), colModel;
	var resetAltRows = function() {
		$(this).children("tbody:first").children('tr.jqgrow').removeClass(
				'myAltRowClass');
		$(this).children("tbody:first").children('tr.jqgrow:visible:odd')
				.addClass('myAltRowClass');
	};
	$("#grid")
			.jqGrid(
					{
						data : gridData,
						datatype : 'local',
						width : 970,
						height : 100,
						emptyrecords : "Nothing to display",
						colNames : [ '', 'User ID', 'Issuer', 'Authenticated',
								'ID', 'locked', 'iisn' ],
						colModel : [ {
							name : 'act',
							index : 'act',
							width : 55,
							sortable : false
						}, {
							name : 'userId',
							index : 'userId',
							width : 120,
							editable : true
						}, {
							name : 'issuerName',
							index : 'issuerName',
							width : 120,
							editable : true
						}, {
							name : 'authenticated',
							index : 'authenticated',
							width : 350,
							editable : true
						}, {
							name : 'id',
							index : 'id',
							hidden : true,
							key : true,
							editable : true
						}, {
							name : 'locked',
							index : 'locked',
							hidden : true,
							editable : true
						}, {
							name : 'iisn',
							index : 'iisn',
							hidden : true,
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
								initializeLockButtons(cl);

							}
							if($("#TCH_userid").val().length > 0){
								populateSearchContent();
							}
							setupRepaintHandlers();
							
						},
						pager : '#pager',
						pgbuttons : true,
						altRows : true,
						altclass : 'myAltRowClass',
						scroll:1,
						loadOnce:true,
						viewrecords : true,
						sortorder : "asc",
						rowNum:100
					});

};

/*
 * Displaying rows in the grid based on values populated in the search User ID
 * textbox
 */
function setupSearchHandlers() {
	$("#srcicn").click(function() {
		populateSearchContent();
	});
}

function populateSearchContent() {
	var rowData = $('#grid').jqGrid('getRowData');
	var dataLength = rowData.length;
	for ( var i = 0; i < rowData.length; ++i) {
		if (rowData[i].userId.toLowerCase().indexOf(
				$('#TCH_userid').val().toLowerCase()) == -1) {
			$('#' + rowData[i].id).hide();
			dataLength--;
		} else {
			$('#' + rowData[i].id).show();
		}
	}
	$('#numCustomers').html(dataLength);
}

function displayModal(data) {
	$(this).closest('tr').addClass('ui-state-highlight');
	$('#userId').val(data.userPassword.userId);
	$('#password').val(data.userPassword.password);
	$('#TCH_pwdDetailsModal').modal({
		dataCss : {
			height : "250px",
			width : "500px"
		}
	});
	return false;

};

function displayConfirmationModal() {
	$(this).closest('tr').addClass('ui-state-highlight');
	$('#TCH_confirmationModal').modal({
		dataCss : {
			height : "200px",
			width : "500px"
		}
	});
};

function displaySortIcons() {
	colModel = $('#grid').jqGrid('getGridParam', 'colModel');
	$(
			'#gbox_' + $.jgrid.jqID($('#grid')[0].id)
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
