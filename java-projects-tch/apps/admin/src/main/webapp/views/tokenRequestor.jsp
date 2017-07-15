<%@include file="include/common.jsp"%>
<ti:header bodyId="tokenRequestor" title="Token Requestor Maintenance"
	cssFiles="themes/redmond/jquery-ui,ui.jqgrid,styles" />
<ti:menu activeItem="tokenRequestor" />
<!-- Main Content -->
<div class="TCH_pageHeader">
	<label>Vault Management System</label>
</div>
<div id="clearfix"></div>
<hr class="TCH_ruler" />
<form id="TCH-tokenRequestorMgmt" name="tokenRequestor">
	<sec:csrfInput />
	<div id="TCH_fieldMessage" style="display: none;"></div>
	<div id="TCH_fieldError" style="display: none;"></div>
	<fieldset>
		<legend>Token Requestor Configuration</legend>
		<div class="TCH_gridContent">
			<input id="TCH_addTokenBtn" class="TCH_button" type="button" value="+ Add Token Requestor" />
			<div id="clearfix"></div>
			<table id="grid"></table>
			<div id="clearfix"></div>
			<div id="TCH_tokenList">
				<span id="numTokenRequestors"></span>Token Requestor Configurations
			</div>
		</div>
	</fieldset>
	<input type="hidden" id="id" name="id"/> 
</form>
<!--Add Token Requestor modal content -->
<div id="TCH_addTokenModal">
	<div class="TCH_pageHeader" id="tokenRequestorModalTitle"></div>
	<div class="savemodalContent">
		<div class="TCH_labelValue divHeight">
			<label for="TCH_tokenName">Name:<span>*</span></label>
			<div class="TCH_fieldValue"><input type="text" id="name" class="TCH_inputs" tabindex="0" size="30"><br /></div>
			<div id="nameErrorMsg" class="fieldErrMsg">
			</div>
		</div>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_tokenID">Token Requestor ID:<span>*</span></label>
			<div class="TCH_fieldValue"><input type="text" id="networkId" class="TCH_inputs" tabindex="0" size="30"><br /></div>
			<div id="networkIdErrorMsg" class="fieldErrMsg">
			</div>
		</div>
	</div>
	<div id="savemodalContentBtn"><input id="TCH_saveChange" class="TCH_button saveChange" type="button" value="Save Changes" /></div>
</div>
<!--Edit Token Requestor modal content -->
<div id="TCH_editTokenModal">
	<div class="TCH_pageHeader" id="tokenRequestorEditModalTitle"></div>
	<div class="savemodalContent">
		<div class="TCH_labelValue">
			<div id="editErrorMsg" class="fieldErrMsg">
			</div>
		</div>
		<div class="TCH_labelValue">
		
		</div>
	</div>
</div>
<!-- Delete Issuer modal content -->
<div id="TCH_delTokenModal">
		<div class="TCH_pageHeader"><h3>Delete Token Requestor</h3></div>
		<div class="deletemodalContent">
			<div class="TCH_labelValue" id="delMsg">
				<label>Are you sure you want to Delete?</label>
			</div>
			<div id="deleteMessage" class="fieldErrMsg"></div>
		</div>
		<div id="delmodalContentBtn">
			<input id="TCH_delUser" class="TCH_button saveChange" type="button" value="Delete" />
			<input id="TCH_cancelDelete" class="TCH_button saveChange modalClose" type="button" value="Cancel" />
		</div>
	</div>

<script>
	var tokenRequestorJson = <c:out escapeXml="false" value="${tokenRequestor}"/>;;
</script>

<ti:footer jsFiles="grid.locale-en,jquery.jqGrid.min,jquery.simplemodal,tokenRequestor" docReadyFn="tokenRequestorBootstrap" />