var numBinMappings= (issuerBinMappingJson.length == null) ? 0 :issuerBinMappingJson.length;
$('#numBinMappings').html(numBinMappings);

function removeValueFromClass(clazz) {
	$(clazz ).find('input').each(function() {
		if($(this).is(':radio')) {
			$(this).val('0');
		} else {
			$(this).val('');
		}
	});
	$(clazz ).find('textArea').each(function() {
		$(this).html('');
		$(this).parent().html($(this).parent().html());
	});
};

function showHideAuthenticationOptions(authValue,fromChange) {
	switch (authValue) {
	case '1':
	case '2':		
		$('.authOption1').show();
		$('.authOption5').hide();
		$('.authOption3').hide();
		
		break;
	case '3':
	case '4':
		$('.authOption1').hide();
		$('.authOption5').hide();
		$('.authOption3').show();
		break;
	case '5':
		$('.authOption3').hide();
		$('.authOption1').hide();
		$('.authOption5').show();
		break;
	}
};

function handleAuthenticationChange() {
	$('#authMechanism').change(function() {
	    var authValue = $(this).val();
	    showHideAuthenticationOptions(authValue,true);
	});
};


function initializeGrid() {
	var $grid = $("#grid"), colModel;
	
	$("#grid").jqGrid({ 
		data: issuerBinMappingJson, 
		datatype: 'local',
		width: 403,
        height: 100, 
		emptyrecords: "Nothing to display",
		colNames:['', 'Real BIN', 'Token BIN','id','iisn'], 
		colModel:[
		          {name:'act', index:'act', width:30, sortable: false},
		          {name:'realBin',index:'realBin', key: true,width:120, sortable:true},
		          {name:'tokenBin', index:'tokenBin', width:350, editable:true},
		          {name:'id',index:'id', key: true,hidden:true},
		          {name:'iisn',index:'iisn', hidden:true}
		          ], 
		          onSortCol: function (index, idxcol, sortorder) {
		        	  if (this.p.lastsort >= 1 && this.p.lastsort !== idxcol
		        			  && this.p.colModel[this.p.lastsort].sortable !== false) {
		        		  // show the icons of last sorted column
		        		  $(this.grid.headers[this.p.lastsort].el)
		        		  .find(">div.ui-jqgrid-sortable>span.s-ico").show();
		        	  }
		          },
		          gridComplete: function(){
		        	  var ids = jQuery("#grid").jqGrid('getDataIDs');
		        	  for(var i=0;i < ids.length;i++){
		        		  var cl = ids[i];
		        		  a1 = "<input type='button' class='editbtn' id='editBtn_"+cl+"' />"; 
		        		  jQuery("#grid").jqGrid('setRowData',ids[i],{act:a1});
		        	  }
		        	  editHandlers();
		          },
		          pager: '#pager', 
		          pgbuttons: true,
		          altRows: true,
		          altclass:'myAltRowClass',
		          viewrecords: true, 
		          sortorder: "asc", 
	});
	//	show sort icons of all sortable columns
	colModel = $grid.jqGrid('getGridParam', 'colModel');
	$('#gbox_' + $.jgrid.jqID($grid[0].id) +
	' tr.ui-jqgrid-labels th.ui-th-column').each(function (i) {
		var cmi = colModel[i], colName = cmi.name;

		if (cmi.sortable !== false) {
			// show the sorting icons
			$(this).find('>div.ui-jqgrid-sortable>span.s-ico').show();
		} else if (!cmi.sortable && colName !== 'rn' && colName !== 'cb' && colName !== 'subgrid') {
			// change the mouse cursor on the columns which are non-sortable
			$(this).find('>div.ui-jqgrid-sortable').css({cursor: 'default'});
		}
	});
};

function issuerDetailBootstrap() {
	showHideAuthenticationOptions($('#authMechanism').val(),false);
	handleAuthenticationChange();
	$('#saveIssuer').click(function() {
		setAuthenticationOptions($('#authMechanism').val());
		document.issuerForm.submit();
	});
	/* Close the modal on click of cancel button */
	$('.modalClose').click(function(e) {
		e.preventDefault();
		$.modal.close();
		$(this).closest('tr').removeClass('ui-state-highlight');
	});

	initializeGrid();
	binMappingBootstrap();
	setupTokenRules();
};

function notSavedModal() {
	$('#TCH_IssuerNotSavedModal').modal({ dataCss: { height: "250px", width: "500px", display: "block" }});
}

function setAuthenticationOptions(authValue) {
	switch (authValue) {
	case '1':
	case '2':
		removeValueFromClass($('.authOption5'));
		removeValueFromClass($('.authOption3'));
		break;
	case '3':
	case '4':
		removeValueFromClass($('.authOption1'));
		removeValueFromClass($('.authOption5').not('.authOption3'));
		break;
	case '5':
		removeValueFromClass($('.authOption1'));
		removeValueFromClass($('.authOption3').not('.authOption5'));
		break;
	}
};