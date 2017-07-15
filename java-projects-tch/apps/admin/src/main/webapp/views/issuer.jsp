<%@include file="include/common.jsp"%>
<ti:header bodyId="issuer" title="Issuer Maintenance" cssFiles="themes/redmond/jquery-ui,ui.jqgrid" />
<ti:menu activeItem="issuer" />
<!-- Main Content -->
	<div class="TCH_pageHeader">
		<label>Vault Management System</label>
	</div>
	<div id="clearfix"></div>
	<hr class="TCH_ruler" />
	<form id= "TCH_isuConfig" name="issuer" action="${controllerContextPath}/issuer">
		<fieldset>
			<legend>Issuer Configuration</legend>
				<div class="TCH_gridContent">
					<input type="hidden" id="id" name="id"/>
					<input id="TCH_addUser" class="TCH_button" type="submit" value="+ Add Issuer" />
					<div id="clearfix"></div>
					<table id="grid"></table>
					<div id="clearfix"></div>
					<div id="TCH_issuerList"><span id="numIssuers"></span>Issuer Configurations</div>
				</div>	
		</fieldset>
	</form>
	<script>
		var issuerJson=<c:out escapeXml="false" value="${issuers}"/>;
	</script>

<ti:footer jsFiles="grid.locale-en,jquery.jqGrid.min,issuer" docReadyFn="issuerBootstrap"/>