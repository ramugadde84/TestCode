var table = null;
var table2 = null;

/**
 * api to get title of a column
 */
$.fn.dataTable.Api.register('column().title()', function() {
	var colheader = this.header();
	return $(colheader).text().trim();
});

/**
 * api to get a columns name
 */
$.fn.dataTable.Api.register('column().name()', function() {
	var columnSettings = this.settings().init().columns;
	var index = this.index();
	return columnSettings[index].name;
});

$.fn.dataTable.Api.register('cellColumnName()', function($ele) {
	var $td = $($ele).closest('td');
	var cell = this.cell($td);
	var index =  cell.index().column;
	var col = this.column(index);
	var colName = col.name();
	
	return colName;
});

/**
 * api to get a column by name
 * 
 * @param name - the column name
 */
$.fn.dataTable.Api.register('getColumnByName', function(name) {
	var columnLength = this.columns().nodes().length;
	for (var i = 0; i < columnLength; i++) {
		var column = this.column(i);
		if(column.name() === name) {
			return col;
		}
	}

	return null;
});

/**
 * api to search for a row whose cell contains a certain value
 * 
 * @param fieldName - the name of the cell to search
 * @param value - the cell value to search
 */
$.fn.dataTable.Api.register('getRowByValue()', function(fieldName, value) {
	var row = this.row( function ( idx, data, node ) {
		if(typeof fieldName === 'string') {
			return data[fieldName] === value;
		}
		else if($.isArray(fieldName) && $.isArray(value)) {
			var isMatchingIndex = true;
			for(var i=0; i < fieldName.length; i++) {
				if(data[fieldName[i]] !== value[i]) {
					isMatchingIndex = false;
				}
			}
			return isMatchingIndex;
		}
	});
	
	return row;
});

/**
 * api to search for any rows whose cell contains a certain value
 * 
 * @param fieldName - the name of the cell to search
 * @param value - the cell value to search
 */
$.fn.dataTable.Api.register('getRowsByValue()', function(fieldName, value) {
	return this.rows( function ( idx, data, node ) {
		if(typeof fieldName === 'string') {
			return data[fieldName] === value;
		}
		else if($.isArray(fieldName) && $.isArray(value)) {
			var isMatchingIndex = true;
			for(var i=0; i < fieldName.length; i++) {
				if(data[fieldName[i]] !== value[i]) {
					isMatchingIndex = false;
				}
			}
			return isMatchingIndex;
		}
	});
});

/**
 * api to convert a datable columns object to an array
 */
$.fn.dataTable.Api.register('columnsArray()', function() {
	var columnLength = this.columns().nodes().length;
	var arr = [];
	for (var i = 0; i < columnLength; i++) {
		var column = this.column(i);
		arr.push(column);
	}

	return arr;
});


/**
 * 
 * @param elemeID - id of the html element
 * @param noMatchesMsg - override default no matches found message.  Leave blank to use default
 * @param rowSelectionEnabled - Enables row selection.  Set to true or false.
 * @param getAjaxParamsMethod - pass additional params to the ajax call
 * @param responseHandler - method called after data is returned from server
 **/
eis.BootstrapDatatable = function(elementID, noMatchesMsg, rowSelectionEnabled, getAjaxParamsMethod, responseHandler, tableOptions) {
	table2 = this;
	var _GL = {
		selectedRow: null,
		getAjaxParamsMethod: null,
		responseHandler: null,
		plugins: [],
		rows_selected: [],
		primaryKey: '',
		showEntry: true,
		showInfo: true,
		attributes: {
			table: {
				dataClickToSelect: 'data-click-to-select',
				dataSelectItemName: 'data-select-item-name',
				dataFilter: 'data-filter-control',
				dataSelectAll: 'data-select-all',
				primaryKey: 'data-primary-key',
				showEntry: 'data-show-entry',
				showInfo: 'data-show-info',
				dataPageSize: 'data-page-size',
				keys:'data-keys',
				datapageHeight: 'data-page-height'
			},
			th: {
				dataVisible: 'data-visible',
				dataField: 'data-field',
				dataFilterControl: 'data-filter-control',
				dataFilterFormatter: 'data-filter-value-formatter'
			}
		}
	}

	var me = this;

	//datatable object
	this.table = null;
	
	//jquery object pointing to the header row table.  There are two tables one for the header and another for the data
	this.$headerTable = null;
	
	//jquery object pointing to the data table
	this.$bodyTable = null;
	
	//jquery object pointing to the title row on the header table.  The header table will have two rows one for the title and another for the filters
	this.$header = null
	
	//jquery object pointing to the filter row on the header table.
	this.$filter = null

	//initialize table
	this.init = function(setRenderAriaLabelFunc) {
		loadPlugins();
		
		this.setRenderAriaLabelFunc(setRenderAriaLabelFunc);
		
		//set element to be jquery friendly
		elementID = '#' + elementID;
		//$(elementID).show();
		$(elementID).css('display','table');
		//
		var columnSettings = buildColumnSettings();

		//parameters for initalizing a table
		var tableSettings = $.extend({
			autoWidth: true, //auto adjust widths
			//bAutoWidth: false,
			responsive: false, //set table to be responsive.  This feature is broke in ie

			/*
			 * layout of the table.
			 * Each item in <> creates a div with the class = text.
			 * pull-left/right were used to align the entry/info and paging onto one line
			 */
			dom: '<"top">rt<"bottom"<"pull-left" il><"pull-right"p>><"clear">',
			pagingType: "full_numbers", //display next/previous, last/first and numbers in paging
			pageLength: $(elementID).attr(_GL.attributes.table.dataPageSize) ? $(elementID).attr(_GL.attributes.table.dataPageSize) : 5, //default page length
			
			//first array specifies the size and the second array specifies what will display in the length menu dropdown.  -1 = ALL
			lengthMenu: [
				[5, 10, 25, 50, -1],
				[5, 10, 25, 50, "All"]
			], //page length options
			orderCellsTop: true, //puts sorting feature onto top header row.  There are two header rows top = title and bottom=filter.
			scrollX: true, //enable scrollbar if width is too large
			//scrollY: 200, //enables scrollbar if height is too large
			//sScrollY: 200,
			//bScrollCollapse: true,
			//scrollCollapse: false,
			//data:data,
			/* ajax: {
			 	url: "taskOrderSearch.htm",
			 	contentType: "application/json",
			 	type: "POST",
			 	dataSrc: 'result',
			 	data: function (data, settings) {
			 		data.task = 'search';
			 		return data;
			 	}
			 },*/
			//change language of fields.
			language: {
				info: "<span>Showing _START_ to _END_ of _TOTAL_ rows</span>", //information field (display at bottom left)
			paginate: { //pagination
					first: "<span aria-hidden='true'>&lt;&lt;</span><span class='offScreen'>Go to first page </span>",
					last: "<span aria-hidden='true'>&gt;&gt;</span><span class='offScreen'>Go to last page </span>",
					previous: "<span aria-hidden='true'>&lt;</span><span class='offScreen'>Go to previous page </span>",
					next: "<span aria-hidden='true'>&gt;</span><span class='offScreen'>Go to next page </span>"
				},
				lengthMenu:"<span>_MENU_ records shown </span><span class='offScreen'>in</span>",
				emptyTable: noMatchesMsg ? noMatchesMsg : "Please enter search criteria"

			},

			//column ids.
			columns: columnSettings,
			
			fnDrawCallback: fnDrawCallback,

			initComplete: initComplete,
			
			//preDrawCallback: function() { },//debugger; },

			//called after each row has been generated but before it has been rendered
			rowCallback: rowCallback
			
			/*headerCallback: function(thead, data, start, end, display) {},
			drawCallback: function(oSettings) {},*/
		}, tableOptions);

		changeTableHeight(tableSettings);
		callPluginMethod('enable',  []);
		preInit(tableSettings);

		//initialize the bootstrap table
		table = me.table = $(elementID).DataTable(tableSettings);
		
		postInit();
		
		return me;
	}
	
	var changeTableHeight =function(tableSettings) { 
		  var tableHeight = $(elementID).attr(_GL.attributes.table.datapageHeight); 
		  if(tableHeight) { 
		    if(isNaN(parseInt(tableHeight))) { 
		        if(tableHeight.toLowerCase() !== 'noscrollbar') { 
		          tableSettings['scrollY'] = 200; 
		        } 
		    } 
		    else { 
		      tableSettings['scrollY'] = tableHeight; 
		    } 
		  } 
		  else { 
		    tableSettings['scrollY'] = 200; 
		  } 
		}
	
	var loadPlugins = function () {
		_GL.plugins.push({key:"checkbox", plugin:new CheckboxPlugin()});
		_GL.plugins.push({key:"checkbox", plugin:new FilterPlugin()});
	}
	
	var callPluginMethod = function (methodName, args) {
		_.forEach(_GL.plugins, function(plugin) {
			if(plugin.plugin[methodName]) {
				plugin.plugin[methodName].apply(plugin.plugin, args);
			}
		});
	}
	var fnDrawCallback = function(oSettings) {
		//console.log('fnDrawCallback');
		var $scrollBodyThead = $(oSettings.nScrollBody).find('thead');
		var table = $(elementID).DataTable();
		//hide entry, info and pagination when no there is no data to display
		toggleEntry(oSettings);
		toggleInfo(oSettings);
		togglePagination(oSettings);
		
		//508 compliace - remove rol='grid'
		$(elementID).removeAttr("role");
		$(elementID + '_wrapper table').eq(0).removeAttr("role");
		/*
		 * remove invisible filters so that tabbing will jump to the next item without looking like it disappeared.
		 * Two duplicate tables are created one for header and another for the body.
		 * The second table filters are not needed so remove them.
		 */

		var $bodyTable = $(elementID).dataTable();
		//sg328m added to solve headers not readable with screen reader.
		//adds a unique id on the result table header th
		$bodyTable.find(' tr:eq(0)').find('th').each(function() {
		    $(this).removeAttr('role');
		    $(this).attr("id", ($(this).attr('id') + 'Header'));
		});
		//this code sets the header attributes of td with id of the corresponding th
		var $tdIdval = $(elementID + ' thead tr').eq(0).find('th');
		$(elementID + ' tbody tr').each(function() {
		    var index = 0;
		    $(this).find('td').each(function() {
		        $(this).attr("headers", ($tdIdval.eq(index).attr('id')));
		        index = index + 1;
		    });
		});
		var $pagination = $(elementID + '_paginate ul');
		$pagination.find('li').each(function() {
		    $(this).find('a').attr("title", "table navigation page"); // sg328m this code is added to give meaningful information for table navigation number link
	    });		
		callPluginMethod('fnDrawCallback', [$scrollBodyThead]);
	}
	
	var rowCallback = function(row, data, dataIndex) {
		callPluginMethod('rowCallback', [row, data, dataIndex]);
	}
	
	var initComplete = function(settings, json) {
		callPluginMethod('initComplete', [settings, json]);
	}
	
	var preInit = function (tableSettings) {
		callPluginMethod('preInit', [tableSettings]);
		
		_GL.primaryKey = $(elementID).attr(_GL.attributes.table.primaryKey);
		
		var showEntry = $(elementID).attr(_GL.attributes.table.showEntry);
		if(showEntry && showEntry == "false") {
			_GL.showEntry = false;
		}
		
		var showInfo = $(elementID).attr(_GL.attributes.table.showInfo);
		if(showInfo && showInfo == "false") {
			_GL.showInfo = false;
		}
		
		tableSettings.dom = buildDom();
	}
	
	var buildDom = function () {
		//var dom = '<"top">rt<"bottom"<"pull-left" il><"pull-right"p>><"clear">';
		var dom = '<"top">rt<"bottom"{il}<"pull-right"p>><"clear">';
		var il = '<"pull-left" {i}{l}>';
		
		if(!_GL.showInfo && !_GL.showEntry) {
			return il = '';
		} else {
			if(_GL.showInfo) {
				il = il.replace('{i}','i');
			}
			
			if(_GL.showEntry) {
				il = il.replace('{l}','l');
			}
		}
		
		dom = dom.replace('{il}', il);
		
		return dom;
		
	}
	
	var postInit = function () {
		//store a few refrences for easy access
		me.$bodyTable = $(elementID).dataTable();
		me.$headerTable = $(elementID + '_wrapper table').eq(0);
		me.$header = me.$headerTable.find('thead').eq(0);
		me.$filter = me.$headerTable.find('thead').eq(1);

		//hide cols requested to be invisible
		toggleInvisibleColumns();

		
		
		me.table.on('responsive-resize', responsiveResize);
		/*me.table.on('responsive-display', function(e, datatable, row, showHide, update) {});
		me.table.on('responsive-resize', function(e, datatable, columns) {});
		me.table.on('draw.dt', function() {});
		me.table.on('column-sizing.dt', function(e, settings) {});*/

		//set event handlers for row selection
		if (rowSelectionEnabled) {
			me.table.on('click', 'tbody tr', selectRow);
			me.table.on('key', selectRowEnter);
		}

		bindDownloadClick();
		
		callPluginMethod('postInit', []);
	}
	
	var responsiveResize = function(e, datatable, columns) {
		//console.log('responsive-resize');
		/*var count = columns.reduce(function(a, b) {
			return b === false ? a + 1 : a;
		}, 0);*/

		callPluginMethod('responsiveResize', [e, datatable, columns]);

		//console.log(count + ' column(s) are hidden');
	}
	
	

	

	var buildColumnSettings = function() {
		var columnSettings = [];

		var $ths = $(elementID + ' th');

		$ths.each(function(value, index) {
			var fieldName = $(this).attr(_GL.attributes.th.dataField);

			columnSettings.push({
				data: fieldName,
				name: fieldName
			});
		});

		return columnSettings;
	}

	

	//hide columns with attribute data-visible='false'
	var toggleInvisibleColumns = function() {
		$(elementID + ' thead tr').eq(0).find('th').each(function(index) {
			var $th = $(this);
			var columnName = $th.attr(_GL.attributes.th.dataField);
			var column = me.table.column(columnName + ':name');

			var visible = $th.attr(_GL.attributes.th.dataVisible);
			if (visible === "false") {
				column.visible(false);
			}
		});
	}

	
	var toggleEntry = function(oSettings) {
		if(_GL.showEntry) {
			if (oSettings.fnRecordsDisplay() > 0) {
				jQuery(oSettings.nTableWrapper).find('.dataTables_length').show();
			} else {
				jQuery(oSettings.nTableWrapper).find('.dataTables_length').hide();
				//oSettings.oLanguage.sEmptyTable = "No search result found";
			}
		}
		
		/*if(!_GL.showEntry) {
			jQuery(oSettings.nTableWrapper).find('.dataTables_length').hide();
		}*/
	}

	var toggleInfo = function(oSettings) {
		if(_GL.showInfo) {
			if (oSettings.fnRecordsDisplay() > 0) {
				jQuery(oSettings.nTableWrapper).find('.dataTables_info').show();
			} else {
				jQuery(oSettings.nTableWrapper).find('.dataTables_info').hide();
			}
		}
		
		
		
		/*if(!_GL.showInfo) {
			jQuery(oSettings.nTableWrapper).find('.dataTables_info').hide();
		}*/
	}

	var togglePagination = function(oSettings) {
		if (oSettings._iDisplayLength == -1 || oSettings._iDisplayLength > oSettings.fnRecordsDisplay()) {
			jQuery(oSettings.nTableWrapper).find('.dataTables_paginate').hide();
		} else {
			jQuery(oSettings.nTableWrapper).find('.dataTables_paginate').show();
		}
	}
	
	var selectRow = function (e) {
		highliteRow(this);
		_GL.selectedRow = me.table.row(this);
	}
	//on key press event for row selection
	var selectRowEnter = function ( e, datatable, key, cell, originalEvent ) {
	    var $row = table.row(cell.index().row);
		_GL.selectedRow = $row;
	}
		//onclick event for row selection - highlites row
	var highliteRow = function(row) {
		//remove previous row highlight
		$(elementID).find('tr.table-highlight').removeClass('table-highlight');

		//add highlight to selected row
		$(row).addClass('table-highlight');
	}
	
	//get selected row data
	var getSelectedRow = function() {
		//return me.table.rows('.table-highlight');
		return _GL.selectedRow;
	}
	
	this.toMap = function (tableData, columnList) {
		var tableData = me.getData();
		var columns = me.table.columnsArray();
	
		
		return _.map(tableData, function(row) {
			var displayData = {};
			_.forEach(columns, function(column) {
				if (column.visible()) {
					var val = row[column.name()];
					var formattedVal = eis.formatter.hyperlinkFormatter(val);
					if(formattedVal) {
						displayData[column.title()] = formattedVal;
					} else {
						displayData[column.title()] = val;
					}
				}
			});
			return displayData;
		});
	};
	
	var bindDownloadClick = function() {
		//console.log(me.table.column(3).title());
		$(elementID).closest('div.table-container').find('li.download-item').off('click').on('click', function() {
			var $this = $(this);
			eis.download.downloadExcel($this.data('url'), $this.data('task'), me.toMap());
		});
	}

	this.onRowClick = function(func) {
		$(elementID).on('click', 'tbody tr', function(event) {
			func(event, me.table.row(this).data(), $(this));
		});
	}
	
	this.onCellClick = function(func) {
		$(elementID).on('click', 'tbody tr td', function(event) {
			func(event, me.table.cell(this), me.table.row(this), me.table.column(this));
		});
	}
	
	this.onKeyEnter = function(func) {
		$(elementID).on( 'key', function ( e, datatable, key, cell, originalEvent ) {
		    if ( key === 13 ) {
		    	func(e, datatable.row( cell.index().row ).data(), $(this));
		    }
		} );
	}
	
	this.onCheckboxChange = function (fnCheck, fnUncheck) {
		$(elementID).on('check.bs.table', fnCheck)
			.on('uncheck.bs.table', typeof fnUncheck === 'function' ? fnUncheck : fnCheck);	
	}
	
	this.onTableLoad = function(func){
		$(elementID).on('draw.dt', func);
	}
	
	//get selected row data
	this.getSelectedRowData = function() {
		var row = getSelectedRow();
		
		if(row) {
			return row.data();
		}
		
		return null;
		
	}
	
	this.getRowDataByPrimaryKey = function (primaryKey) {
		var row = this.getRowByID(primaryKey);
		
		if(row) {
			return row.data();
		}
	}
	
	

	//clear table
	this.clear = function() {
		me.table.clear().draw();
		callPluginMethod('clear', []);
		_GL.selectedRow = null;
	}

	this.getData = function() {
		//return me.$body._('tr', {"filter":"applied"}).toArray();
		return me.table.rows('tr', {
			"filter": "applied"
		}).data().toArray();
	}

	this.getAllData = function() {
		return me.table.rows().data();
	}

	

	//highlight row. First row starts at index 1.
	this.highlightRow = function(index) {
		if (index) {
			$(elementID + ' tbody tr:nth-child(' + index + ')').addClass('table-highlight');
		}
	}

	

	
	
	//return datatable row object
	this.getRowByID = function (id) {
		return me.table.getRowByValue(_GL.primaryKey, id);
	}
	
	//return datatable row object
	this.getRows = function (fieldName, value) {
		return me.table.getRowByValue(fieldName, id);
	}
	
	//delete selected row
	this.deleteSelectedRow = function() {
		var row = getSelectedRow();
		
		if(row) {
			callPluginMethod('removeFromCheckedList', [row]);
			row.remove().draw();
		}
	}
	
	//delete rows matching a value
	this.deleteRow = function(fieldName, value) {
		var row = me.table.getRowsByValue(fieldName, value);
		
		if(row) {
			row.every(function() {
				callPluginMethod('removeFromCheckedList', [this]);
			});
			row.remove().draw();
		}
	}

	/*
	 * update single field in selected row
	 * 
	 * @fieldID - bootstrp table id of field to update
	 * @value - new value
	 */
	this.updateSelectedRowCell = function(fieldName, value) {
		var row = getSelectedRow();
		
		updateCell(row,fieldName, value);
	}
	
	this.updateCellByPrimaryKey = function(id, fieldName, value) {
		var row = this.getRowByID(id);
		
		updateCell(row, fieldName, value);
	}
	
	/*this.updateCellByValue = function(searchFieldName, searchFieldValue, updateFieldName, updateValue) {
		var rows = me.table.getRowByValue(searchFieldName, searchFieldValue);
		
		updateCells(rows,updateFieldName, updateValue);
	}*/
	
	var updateCell = function (row, fieldName, value) {
		if(row) {
			me.table.cell(row,  fieldName + ':name').data(value).draw();
		}
	}

	this.updateSelectedRow = function(rowData) {
		var row = getSelectedRow();
		
		updateRow(row, rowData);
	}
	
	this.updateRowByPrimaryKey = function(id, rowData) {
		var row = me.table.getRowByValue(_GL.primaryKey, id);
		updateRow(row, rowData);
	}
	
	var updateRow = function (row, rowData) {
		if(row) {
			row.data(rowData).draw();
		}
	}
	
	this.updateRowByColumnName = function(colName, id, rowData) {
		var row = me.table.getRowByValue(colName, id);
		updateRow(row, rowData);
	}

	this.insertRow = function(rowData) {
		me.table.row.add(rowData).draw();
		//_GL.filterPlugin.initSelectFilters();
		callPluginMethod('initSelectFilters', []);
	}
	
	this.append = function(data) {
		var rows = me.table.rows.add(data).draw().nodes();
		//callPluginMethod('initSelectFilters', []);
		
		//var rows = me.table.rows(data);
		callPluginMethod('append', [rows]);
	}

	this.getCheckedRowData = function() {
		return _.map(_GL.rows_selected, function (obj) {
			return me.table.row(obj.$row).data();
		})
	}
	
	this.setRenderAriaLabelFunc = function (func) {
		var aaa = _.find(_GL.plugins,{key:'checkbox'});
		aaa.plugin.setRenderAriaLabelFunc(func);
	}
	
	this.redraw = function () {
		this.table.draw();
	}


	/*//func returns params
	var manualAjaxCall = function(args) {
		var params = _GL.getAjaxParamsMethod(args);
		$.post(args.url, params, args.success, "json")
			.done(args.complete);
	}

	//refresh table - will request data from server
	this.refresh = function() {
		$(elementID).bootstrapTable('refresh');
	}



	this.resetView = function() {
		$(elementID).bootstrapTable('resetView');
	}

	this.getSelections = function() {
		return $(elementID).bootstrapTable('getSelections');
	}

	this.getAllSelections = function() {
		return $(elementID).bootstrapTable('getAllSelections');
	}

	this.getOptions = function() {
		return $(elementID).bootstrapTable('getOptions');
	}*/
	
	/*
	 * Iterates over every cell in table
	 * 
	 * Parameter is a callback function
	 * For each cell, calls callback and passes the current cell, row, and column as parameters
	 */
	this.iterateCells = function(callback) {
		me.table.cells().every(function(rowIndex, columnIndex) {
			callback(this, me.table.row(rowIndex), me.table.column(columnIndex));
		});
	};
	
	/*
	 * Iterates over every row in table
	 * 
	 * Parameter is a callback function
	 * For each row, calls callback and passes the current row as a parameter
	 */
	this.iterateRows = function(callback) {
		me.table.rows().every(function(rowIndex) {
			callback(this);
		});
	};

	
	
	var CheckboxPlugin = function () {
		var enabled = false;
		var rowID = "";
		var renderAriaLabelFunc = function (){};
		var usingSelectAll = true;
		var columnIndex = -1;
			
		this.setRenderAriaLabelFunc = function (func) {
			renderAriaLabelFunc = func;
		}
		
		this.isEnabled = function () {
			return enabled;
		}
		
		this.enable = function () {
			/*
			 * Apply checkbox control if the data-select-item-name is set
			 */
			
			rowID = $(elementID).attr(_GL.attributes.table.dataSelectItemName);
			if ( rowID ) {
				enabled = true;
			}
		}
		
		this.rowCallback = function (row, data, dataIndex) {
			if(this.isEnabled()) {
				var $row = $(row);
				var primaryKey = data[_GL.primaryKey];
				
				// If the row is in the list of selected rows
				var index = _.findIndex(_GL.rows_selected, function(obj) {
					return obj.key === primaryKey;
				});
				
				if (index !== -1) {
					$row.find('input[type="checkbox"]').prop('checked', true);
					$row.addClass('selected');
				}
			}
		}
		
		this.fnDrawCallback = function($scrollBodyThead) {
			var $bodyTable = $(elementID).dataTable();
			var $headerTable = $(elementID + '_wrapper table').eq(0);
			
			var $header = $headerTable.find('thead').eq(0);
			var $th = $header.find('th['+_GL.attributes.th.dataField+'="' +rowID+ '"]').eq(0);
			$header.find(' tr:eq(0)').removeAttr('role');
			$th.attr('aria-label', "Activate to select all");			
			
		}
		
		this.preInit = function(tableSettings) {
			if (this.isEnabled()) {
				if($(elementID).attr(_GL.attributes.table.dataSelectAll) == "false") {
					usingSelectAll = false;
				}
				
				//add select all input box to header				
				
					var $thead = $(elementID + ' thead');
					var $th = $thead.find('th['+_GL.attributes.th.dataField+'="' +rowID+ '"]').eq(0);
					var $checkbox = $('<input name="select_all" value="1" type="checkbox" aria-label="select all"><span> '+$th.text()+' </span>');
					if(usingSelectAll) {					
					//clear the htnml elemnt
					$th.html('');
					$th.append($checkbox);
					$th.attr('aria-label', "Activate to select all");
					
					
				}
					
				//set table settings
				tableSettings.columnDefs = [{
					'targets': $th.index(),
					'searchable': false,
					'orderable': false,
					'width': '1%',
					'className': 'dt-body-center',
					'render': function(data, type, full, meta) {
						if(data =="true"||data === true || full.checked === true) {
							return '<input aria-label="' + renderAriaLabelFunc(full)+ '" type="checkbox" checked>';
						} else {
							return '<input aria-label="' + renderAriaLabelFunc(full)+ '" type="checkbox" >';
						}
						
					}
				}]
			}
		}
		
		this.postInit = function () {
			if(this.isEnabled()) {
				//checkbox onclick event
				$(elementID).find('tbody').on('click', 'input[type="checkbox"]', checkboxOnclick);
				
				/*//this block of code is written for implementing key table and enable check or uncheck check box with space bar
				$(elementID).on('key', function(e, datatable, key, cell, originalEvent) {
					var $focusedCell = $(elementID).find('tbody td.focus input[type="checkbox"]');					
					var $row = $focusedCell.closest('tr');	
						if (key===32) {
							if ($focusedCell.attr('checked')){
								$focusedCell.attr('checked', false);
							}else{
								$focusedCell.attr('checked', true);	
								}
							addRemoveCheckbox($focusedCell);
							e.stopPropagation();
							var rowData = datatable.row($row).data();
							$(elementID).trigger('check.bs.table', [rowData, $row ]);
						}
					});*/
				
				
				$(elementID).find('tbody input[type="checkbox"]').each(function () {
					addRemoveCheckbox($(this));
				})
		
				//if data-click-to-select is set to true then enable row onclick to mark a checkbox
				if(me.$bodyTable.attr(_GL.attributes.table.dataClickToSelect) === 'true') {
					$(elementID).on('click', 'tbody td, thead th:first-child', function(e) {
						$(this).parent().find('input[type="checkbox"]').trigger('click');
					});
				}
		
				// Handle click on "Select all" control
				$('thead input[name="select_all"]').on('click', function(e) {
					if (this.checked) {
						$(elementID).find('tbody input[type="checkbox"]:not(:checked)').trigger('click');
					} else {
						$(elementID).find('tbody input[type="checkbox"]:checked').trigger('click');
					}
		
					// Prevent click event from propagating to parent
					e.stopPropagation();
				});
		
				// Handle table draw event
				me.table.on('draw', function() {
					// Update state of "Select all" control
					updateDataTableSelectAllCtrl(me.table);
				});
			}
		}
		
		this.append = function (rows) {
			if (!this.isEnabled()) {
				return;
			}
			
			_.forEach(rows, function(row) {
				//var $row = me.getRowByID(rowData[_GL.primaryKey])
				var $input = $(row).find('input');
				addRemoveCheckbox($input);
			});
		}
		
		var addRemoveCheckbox = function ($input) {
			var $row = $input.closest('tr');
			var isChecked = $input.is(':checked');
			var columnName = me.table.cellColumnName($input);
			
			// Get row data
			var data = me.table.row($row).data();
			
			data[columnName] = isChecked;
			
			var primaryKey = data[_GL.primaryKey];

			// Determine whether row is in the list of selected row IDs 
			var index = _.findIndex(_GL.rows_selected, function(obj) {
				return obj.key === primaryKey;
			});

			// If checkbox is checked and row is not in list of selected row
			if (isChecked && index === -1) {
				_GL.rows_selected.push({'key':primaryKey, '$row':$row});

			// Otherwise, if checkbox is not checked and row is in list of selected row
			} else if (!isChecked && index !== -1) {
				_GL.rows_selected.splice(index, 1);
			}

			if (isChecked) {
				$row.addClass('selected');
			} else {
				$row.removeClass('selected');
			}

			// Update state of "Select all" control
			updateDataTableSelectAllCtrl(me.table);
		}
		
		var checkboxOnclick = function(e) {
			var $this = $(this);
			var $row = $(this).closest('tr');
			
			addRemoveCheckbox($(this));

			// Prevent click event from propagating to parent
			e.stopPropagation();
			
			var rowData = me.table.row($row).data();
			$(elementID).trigger('check.bs.table', [rowData, $row ]);
		}
		
		var updateDataTableSelectAllCtrl = function(table) {
			if(usingSelectAll) {
				var $tableBody = me.table.table().node();
				var $tableHead = $(elementID + '_wrapper table').eq(0);
				var $chkbox_all = $('tbody input[type="checkbox"]', $tableBody);
				var $chkbox_checked = $('tbody input[type="checkbox"]:checked', $tableBody);
				
				var chkbox_select_all = $('thead input[name="select_all"]').get(0);
	
				// If none of the checkboxes are checked
				if ($chkbox_checked.length === 0) {
					chkbox_select_all.checked = false;
					if ('indeterminate' in chkbox_select_all) {
						chkbox_select_all.indeterminate = false;
					}
	
					// If all of the checkboxes are checked
				} else if ($chkbox_checked.length === $chkbox_all.length) {
					chkbox_select_all.checked = true;
					if ('indeterminate' in chkbox_select_all) {
						chkbox_select_all.indeterminate = false;
					}
	
					// If some of the checkboxes are checked
				} else {
					chkbox_select_all.checked = true;
					if ('indeterminate' in chkbox_select_all) {
						chkbox_select_all.indeterminate = true;
					}
				}
			}
		}
		
		this.removeFromCheckedList = function(row) {
			var keyToRemove = row.data()[_GL.primaryKey]; 
			_.remove(_GL.rows_selected, function(selectedRow) { 
				return selectedRow.key === keyToRemove; 
			});
		}
	}
	
	var FilterPlugin = function () {
		var enabled = false;
		
		this.isEnabled = function () {
			return enabled;
		}
		
		this.enable = function () {
			/*
			 * Apply filters if the data-filter is true
			 */
			if ($(elementID).attr(_GL.attributes.table.dataFilter) === "true") {
				enabled = true;
			}
		}
		this.fnDrawCallback = function($scrollBodyThead) {
			/*
			 * remove invisible filters so that tabbing will jump to the next item without looking like it disappeared.
			 * Two duplicate tables are created one for header and another for the body.
			 * The second table filters are not needed so remove them.
			 */
			$scrollBodyThead.find('.filter input').remove();
			$scrollBodyThead.find('.filter select').remove();
		}
		
		this.initComplete = function(settings, json) {
			//second call to remove invisible filters.
			var $scrollBodyThead = $(settings.nScrollBody).find('thead');
			$scrollBodyThead.find('.filter input').remove();
			$scrollBodyThead.find('.filter select').remove();
		}
		
		/*
		 * Add filter.  If the tables data-filter attribute is true then add filters.  We add a tr to thead.
		 * Then loop through each th item checking its data-filter-control attribute for the type of filter.
		 * data-filter-control can be equal to 'input' or 'select'.
		 */
		this.preInit = function() {
			if (!this.isEnabled()) {
				return;
			}
			var $theader=$(elementID + ' thead').eq(0);
			$theader.find('th').each(function() {				
			var $th = $(this);
				//$($th).attr('scope', 'col');
				$($th).attr('role', 'button');
			});
			var $thead = $(elementID + ' thead');
			var $filter = $('<tr class="filter"></tr>');

			$thead.append($filter);

			$thead.find('th').each(function() {
				var $th = $(this);
				var $filterControl;

				if ($th.attr(_GL.attributes.th.dataFilterControl) === "input") {
					var inputId=$th.attr(_GL.attributes.th.dataField);
					var title = $(this).text();
					$filterControl = $('<td><input class="form-control input-sm col-sm-12" aria-label=" ' + title + '" type="text" placeholder=" ' + title + '" /></td>');

				} else if ($th.attr(_GL.attributes.th.dataFilterControl) === "select") {
					$filterControl = $('<td><select class="form-control input-sm dropdown-scroll col-sm-12" aria-label=" ' + title + '"><option value=""></option></select></td>');
				} else {
					$filterControl = $('<td></td');
				}

				$filter.append($filterControl);
				var attributes = $th.prop("attributes");
				$.each(attributes, function() {
					if(this.name!='scope'&& this.name!='role' && this.name!='id'){
						$filterControl.attr(this.name, this.value);
					}
				});
			});
		}
		
		this.postInit = function () {
			if (!this.isEnabled()) {
				var $tader=$(elementID + ' thead').eq(0);
				$tader.find(' tr:eq(0)').removeAttr('role');
				return;
			}
			var $theader=$(elementID + ' thead').eq(0);
			//$theader.find('th').each(function()
			//Apply filter method to inputs
			this.initInputFilters();

			//apply filter methods to select drop downs and also display unique list of items
			this.initSelectFilters();
		}
		
		this.responsiveResize = function (e, datatable, columns) {
			if (!this.isEnabled()) {
				return;
			}
			
			$.each(columns, function(index, column) {
				var $th = $(".table .filter td").eq(index);
				if (column === true) {
					$th.show();
				} else {
					$th.hide();
				}
			})
		}
		
		//applies filter method to selects.  Also outputs a unique array of items in each dropdown.
		this.initSelectFilters = function() {
			if (!this.isEnabled()) {
				return;
			}

			//var $selects = $(elementID +'_wrapper .filter th');
			var $selects = $(elementID + '_wrapper .filter td');
			$.each($selects, function() {
				var $th = $(this);
				var $select = $(this).find('select');
				var index = $select.parent($select).index();
				var columnName = $th.attr(_GL.attributes.th.dataField);
				var column = me.table.column(columnName + ':name');

				$select.empty();
				$select.addOption('', '');

				//select.appendTo( $(column.footer()).empty() );
				$select.on('change', function() {
					var val = $.fn.dataTable.util.escapeRegex($(this).val());
					column.search(val ? '^' + val + '$' : '', true, false).draw();
				});

				column.data().unique().sort().each(function(d, j) {
					//select.append( '<option value="'+d+'">'+d+'</option>' )
					var formatter = $select.parent().attr(_GL.attributes.th.dataFilterFormatter);
					var val = d;
					if (eis.formatter[formatter]) {
						val = eis.formatter[formatter](val);
					}

					if (val) {
						$select.addOption(val, val);
					}
				});
			});
		}

		//Apply filter method to inputs
		this.initInputFilters = function() {
			if (!this.isEnabled()) {
				return;
			}

			var $inputs = $(elementID + '_wrapper .filter td');
			$.each($inputs, function() {
				var $th = $(this);
				var $input = $(this).find('input');
				var index = $input.parent($input).index();
				var columnName = $th.attr(_GL.attributes.th.dataField);
				var column = me.table.column(columnName + ':name');

				$input.on('keyup change', function() {
					if (column.search() !== this.value) {
						column.search(this.value).draw();
					}
				});
			});
		}
		
		this.clear = function() {
			me.table.search('').columns().search('').draw();
		}
		
		this.append = function (rows) {
			this.initSelectFilters();
		}
	}
	

}

//eis.dataTable.
