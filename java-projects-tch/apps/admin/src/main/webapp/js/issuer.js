var issuerLength = issuerJson.length;

function setupGrid() {
	var $grid = $("#grid"), colModel;
	$("#grid")
			.jqGrid(
					{
						datatype : 'jsonstring',
						datastr : issuerJson,
						width : 970,
						height : 100,
						emptyrecords : "No issuers found",
						jsonReader : {
							repeatitems : false,
							root : function(obj) {
								return obj;
							}
						},
						colNames : [ '', 'Issuer Name', 'IISN',
								'Supported Token Requestors', 'id' ],
						colModel : [ {
							name : 'act',
							index : 'act',
							width : 19,
							sortable : false
						}, {
							name : 'name',
							index : 'name',
							width : 100,
							editable : true
						}, {
							name : 'iisn',
							index : 'iisn',
							width : 70,
							editable : true
						}, {
							name : 'tokenRequestor',
							index : 'tokenRequestor',
							width : 600,
							editable : true
						}, {
							name : 'id',
							index : 'id',
							key : true,
							hidden : true,
							width : 10,
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
								a1 = "<input type='button' class='editbtn' id='editbtn_"
										+ cl + "'/>";
								jQuery("#grid").jqGrid('setRowData', cl, {
									act : a1
								});
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
	// show sort icons of all sortable columns
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

function setupHandlers() {
	$('#numIssuers').html(issuerLength);
	$('.editbtn').click(function() {
		editIssuer($(this).attr('id').split('_')[1]);
	});
};

function issuerBootstrap() {
	setupGrid();
};

function editIssuer(id) {
	$('#id').val(id);
	document.issuer.submit();
};