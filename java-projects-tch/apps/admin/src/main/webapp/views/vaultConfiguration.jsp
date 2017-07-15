<%@include file="include/common.jsp"%>
<ti:header bodyId="vault" title="Vault Master Configuration" cssFiles="themes/redmond/jquery-ui,ui.jqgrid" />
<ti:menu activeItem="vault" />
<!-- Main Content -->
	<div class="TCH_pageHeader">
		<label>vault Maintenance</label>
	</div>
	<div id="clearfix"></div>
	<hr class="TCH_ruler" />
	
	<div id="TCH-vaultMgmt">
		<form name= "vaultConfiguration" method="post" action="${controllerContextPath}/vault/configure/saveVaultMasterConfiguration">
			<sec:csrfInput />
			<fieldset>
				<legend>Vault Master Configuration</legend>
					<div class="TCH_labelValue">
						<label for="TCH_switchEndPoint">Switch Endpoint:<span>*</span></label>
						<div class="TCH_fieldValue">
							<input name="switchEndpointUrl" id="switchEndpointUrl" class="TCH_inputs" value ="${vaultConfiguration.switchEndpointUrl}" />
						</div>
					</div>
					<div class="TCH_labelValue">
						<label for="TCH_logLevel">Log Level:<span>*</span></label>
						<div class="TCH_fieldValue">
							<select class="TCH_inputs"name="baseLogLevel" id="baseLogLevel">
		    					<c:forEach items="${loggingLevels}" var="logLevel">
		        					<option value="${logLevel}" ${logLevel == vaultConfiguration.baseLogLevel ? 'selected="selected"' : ''} >${logLevel}</option>
		    					</c:forEach>
							</select>
					</div>
					</div>
					<div class="TCH_labelValue">
						<label for="TCH_batchFile">Batch File Processing Root Directory:<span>*</span></label>
						<div class="TCH_fieldValue">
							<input name="batchFileRootDirectory" id="batchFileRootDirectory" class="TCH_inputs" value ="${vaultConfiguration.batchFileRootDirectory}" />
						</div>
					</div>
					<div class="TCH_labelValue">
						<label for="TCH_cloudSwitchID">Cloud Switch ID:<span>*</span></label>
						<div class="TCH_fieldValue">
							<input name="cloudSwitchId" id="cloudSwitchId" class="TCH_inputs" value ="${vaultConfiguration.cloudSwitchId}" />
						</div>
					</div>
					<div class="TCH_labelValue">
						<label for="TCH_vaultSwitchID">Vault Switch ID:<span>*</span></label>
						<div class="TCH_fieldValue">
							<input name="vaultSwitchId" id="vaultSwitchId" class="TCH_inputs" value ="${vaultConfiguration.vaultSwitchId}" />
						</div>
					</div>
					<input id="TCH_updateConfig" class="TCH_button" type="button" value="Update Config" onclick = "vaultConfigValidation()" />
			</fieldset>
		</form>
	</div>
	
<ti:footer jsFiles="grid.locale-en,jquery.jqGrid.min,vaultConfiguration" docReadyFn="issuerBootstrap"/>