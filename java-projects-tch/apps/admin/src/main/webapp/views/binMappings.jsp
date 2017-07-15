<%@include file="include/common.jsp"%>
<div class="art_right">
	<div class="sec_header">BIN Mapping</div>
	<input type="hidden" id="id" name="id"/> 
	<div class="sec_right">
		<div id="TCH_binMapping"class="TCH_gridContent">
			<c:if test="${issuer.id!=null}">
				<input id="binMapping" class="TCH_button" type="button" onclick = "" value="+ Add Mapping" />
			</c:if>
			<table id="grid"></table>
			<div id="TCH_binList"><span id="numBinMappings"></span>Real BIN</div>
		</div>
	</div>
</div>
	<div id="TCH_edit_tckns">
		<c:if test="${issuer.id!=null && issuer.tokenBinMappings!='[]'}">
			<a href="#">Edit Token Requestor/Token Rules</a>
		</c:if>
	</div>

<!--Save Issuer modal content -->
<div id="TCH_saveIssuerModal">
	<div class="TCH_pageHeader" id="binMappingModalTitle"></div>
	<input type="hidden" id="binMappingId" name="binMappingId"/> 
	<div class="savemodalContent">
		<div class="TCH_labelValue divHeight">
			<label for="TCH_realBin">Real BIN:<span>*</span></label>
			<div class="TCH_fieldValue"><input type="text" id="realBin" class="TCH_inputs" tabindex="0" size="30" ><br /></div>
			<div id="realBinErrMsg" class="fieldErrMsg" ></div>
		</div>
		<div class="TCH_labelValue divHeight1">
			<label for="TCH_tokenBin">Token BIN:<span>*</span></label>
			<div class="TCH_fieldValue"><input type="text" id="tokenBin" class="TCH_inputs" tabindex="0" size="30"><br /></div>
			<div id="tokenBinErrMsg" class="fieldErrMsg" ></div>
		</div>
	</div>
	<div id="savemodalContentBtn"><input id="TCH_saveChange" class="TCH_button saveChange" type="button" value="Save Changes" onclick="saveBinMappingDetails()"/></div>
</div>
	