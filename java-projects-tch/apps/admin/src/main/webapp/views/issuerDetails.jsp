
<%@include file="include/common.jsp"%>
<ti:header bodyId="issuer" title="Issuer Maintenance"
	cssFiles="themes/redmond/jquery-ui,ui.jqgrid,custom" />
<ti:menu activeItem="issuer" />
<div id="clearfix"></div>
<!-- Main Content -->
<div class="TCH_pageHeader">
	<label>Vault Management System</label>
</div>
<div id="clearfix"></div>
<hr class="TCH_ruler" />
<form name="issuerForm" method="POST"
	action="${controllerContextPath}/issuer?${_csrf.parameterName}=${_csrf.token}"
	enctype="multipart/form-data">
	<sec:csrfInput />
	<div id="section_wrapper">
		<c:if test="${saveMessage!=null}">
			<div id="TCH_fieldMessage">
					<spring:message code="${saveMessage}" arguments="Issuer"></spring:message>
			</div>
		</c:if>
		<fieldset>
			<legend>
				<c:choose>
					<c:when test="${issuer.id!=null}">Edit Issuer</c:when>
					<c:otherwise>Add Issuer</c:otherwise>
				</c:choose>
			</legend>
			<div class="TCH_Left_section_Wrap">
				<%@include file="issuerInfo.jsp"%>
				<%@include file="authenticationOptions.jsp"%>
			</div>
			<div class="TCH_Right_section_Wrap">
				<%@include file="supportedTokenRequestorInfo.jsp"%>
				<div id="TCH_webTknsModal">
					<%@include file="tokenRules.jsp"%>
				</div>
				<%@include file="binMappings.jsp"%>
			</div>
		</fieldset>
	</div>
	<div id="TCH_saveIssuer">
		<input id="saveIssuer" class="TCH_button" type="button"
			value="Save Issuer" />
	</div>
</form>

<script>
	var issuerBinMappingJson = <c:out escapeXml="false" value="${issuer.tokenBinMappings}"/>;
		
</script>

<ti:footer
	jsFiles="grid.locale-en,jquery.jqGrid.min,issuerDetails,tokenrules,jquery.simplemodal,binMapping,common"
	docReadyFn="issuerDetailBootstrap" />