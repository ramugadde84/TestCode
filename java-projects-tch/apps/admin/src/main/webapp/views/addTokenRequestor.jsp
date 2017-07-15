<%@include file="include/common.jsp"%>
<ti:header bodyId="issuer" title="Issuer Maintenance" cssFiles="themes/redmond/jquery-ui,ui.jqgrid" />
<ti:menu activeItem="issuer" />
<!-- Main Content -->
	<div class="TCH_pageHeader">
			<label>Vault Management System</label>
		</div>
		<div id="clearfix"></div>
		<hr class="TCH_ruler" />
		<form id="add_user">
			<sec:csrfInput />
			<fieldset>
				<legend>Add New Issuer</legend>
				<div class="TCH_section_Wrap">
					<!--for left article -->
					<div class="art_left">
						<div class="sec_header">Issuer Information</div>
						<div class="sec_left">	
							<div class="TCH_labelValue">
								<label for="TCH_issuerName">Name:<span>*</span></label>
								<div class="TCH_fieldValue"><input type="text" id="TCH_issuerName" class="TCH_inputs" tabindex="0" size="30"><br /></div>
							</div>
							<div class="TCH_labelValue">
								<label for="TCH_issuerID">IID:<span>*</span></label>
								<div class="TCH_fieldValue"><input type="text" id="TCH_issuerID" class="TCH_inputs" tabindex="0" size="30"><br /></div>
							</div>
							<div class="TCH_labelValue">
								<label for="TCH_issuerIISN">IISN:<span>*</span></label>
								<div class="TCH_fieldValue"><input type="text" id="TCH_issuerIISN" class="TCH_inputs" tabindex="0" size="30"><br /></div>
							</div>
							<div class="TCH_labelValue">
								<label for="TCH_issuerZone">Dropzone Path:<span>*</span></label>
								<div class="TCH_fieldValue"><input type="text" id="TCH_issuerZone" class="TCH_inputs" tabindex="0" size="30"><br /></div>
							</div>
						</div>
					</div>
					<!--for right article -->
					<div class="art_right">
						<div class="sec_header">Authentication</div>
						<div class="sec_right">
							<div class="TCH_labelValue">
								<label for="TCH_auth_mech">Mechanism:<span>*</span></label>
								<div class="TCH_fieldValue">
									<select id="TCH_auth_mech" class="TCH_inputs" tabindex="0">
										<option selected="selected">TCH Hosted Issuer Auth Web Service</option>
										<option>option 2</option>
										<option>option 3</option>
									</select>
								</div>
							</div>
							<div id="TCH_conf"><a href="javascript: void(0)">Configure Authentication</a></div>
						</div>
					</div>
					<div id="clearfix"></div>
					<!-- Second level article -->
					<!--for left article -->
					<div class="art_left">
						<div class="sec_header">Supported Token Requestors</div>
						<div class="sec_left">	
							<select id="TCH_supp_tckns" class="TCH_inputs" tabindex="0" size="5">
								<option>Paybriant BB&amp;T</option>
								<option>Level Up</option>
								<option selected="selected">Google Wallet</option>
								<option>Paybriant BB&amp;T</option>
								<option>option 2</option>
								<option>option 3</option> 
							</select>
							<div id="TCH_edit_tckns"><a href="#">Edit Token Requestor/Token Rules</a></div>
						</div>
					</div>
					<!--for right article -->
					<div class="art_right">
						<div class="sec_header">Bin Mapping</div>
						<div class="sec_right">
							<div class="TCH_gridContent">
								<input id="TCH_addMap" class="TCH_button" type="button" value="+ Add Mapping" onClick="window.location.href='#'" />
								<table id="grid"></table>
								<div id="TCH_binList"><span>4</span>Real BIN</div>
							</div>
						</div>
					</div>
				</div>	
			</fieldset>
			<div id="TCH_saveIssuer"><input id="TCH_saveIssuer" class="TCH_button" type="button" value="Save Issuer" /></div>
		</form>
	</div>
<ti:footer jsFiles="grid.locale-en,jquery.jqGrid.min,addIssuer,TCH_js,jquery.simplemodal" docReadyFn="issuerBinMappingBootStrap"/>