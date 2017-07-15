<%@include file="include/common.jsp"%>
<ti:header bodyId="customer" title="User Maintenance"
	cssFiles="themes/redmond/jquery-ui,ui.jqgrid,styles" />
<ti:menu activeItem="customer" />
<!-- Main Content -->
<div class="TCH_pageHeader">
	<label>Vault Management System</label>
</div>
<div id="clearfix"></div>
<hr class="TCH_ruler"/>
<form id="TCH-userMgmt">
	<sec:csrfInput />
	<fieldset>
		<legend>Issuer Customer ID Management</legend>
		<div class="TCH_gridContent">
			<div class="searchIssuerContent">
				<sec:authorize access="hasRole('ROLE_TCH_USER_MGMT')">
					<div class="TCH_labelValue isuSelect">
						<label for="IsuSelect">Issuer:<span></span></label>
						<div class="TCH_fieldValue">
							<select id="iisn" name="iisn">
								<option value=''>Select Issuer</option>
								<c:forEach var="issuer" items="${issuers}" varStatus="status">
									<option value="${issuer.iisn}"
									><c:out value="${issuer.issuerName}"></c:out></option>
								</c:forEach>
							</select>
							
							<span>
								<input id="srcissn" class="filterIcon" type="button" alt="Filter Issuer" title="Filter Issuer" />
							</span>
						</div>
					</div>
				</sec:authorize>
				<sec:authentication property="principal.iisn" var="iisn"/>
				<c:if test="${iisn!=null}">
					<input id="iisn" name="iisn" type="hidden" value="${iisn}"/>
				</c:if>
				<div class="TCH_labelValue">
					<label for="TCH_userid">Search User ID:<span></span></label>
					<div class="TCH_fieldValue">
						<input type="text" id="TCH_userid" class="TCH_inputs" tabindex="0" />
						<span>
							<input id="srcicn" class="searchIcon" type="button" alt="Search User ID" title="Search User ID" />
						</span>
					</div>
				</div>
			</div>
				<!--Password Details modal content -->
			<div id="TCH_pwdDetailsModal">
				<div class="TCH_pageHeader"><h3>New Password Details</h3></div>
				<div class="savemodalContent">
					<div class="TCH_labelValue">
						<label for="TCH_realBin">User ID:<span></span></label>
						<div class="TCH_fieldValue"><input type="text" id="userId"  disabled="disabled" class="TCH_inputs" tabindex="0" size="30"><br /></div>
					</div>
					<div class="TCH_labelValue">
						<label for="TCH_tokenBin">Password:<span></span></label>
						<div class="TCH_fieldValue"><input type="text" id="password"  disabled="disabled" class="TCH_inputs" tabindex="0" size="30"><br /></div>
					</div>
				</div>
				<div id="savemodalContentBtn"><input id="TCH_saveChange" class="TCH_button saveChange modalClose" type="button" value="Close" /></div>
			</div>
			
			<div id="TCH_confirmationModal">
		<div class="TCH_pageHeader"><h3>User Confirmation</h3></div>
		<div class="deletemodalContent">
			<div class="TCH_labelValue">
				<label>Are you sure you want to <span id="lockUnlockMsg"></span> the customer?</label>
				<input id="url" type="hidden"/>
				<input id="id" type="hidden"/>
			</div>
			<div id="deleteMessage" class="fieldErrMsg"></div>
		</div>
		<div id="delmodalContentBtn">
			<input id="lockUnlockConfirm" class="TCH_button saveChange" type="button" value="OK" />
			<input id="lockUnlockCancel" class="TCH_button saveChange modalClose" type="button" value="Cancel" />
		</div>
	</div>
			
			<div id="clearfix"></div>
			<table id="grid"></table>
			<div id="TCH_userManageList"><span id="numCustomers"></span>Users</div>
		</div>	
	</fieldset>
</form>
<ti:footer jsFiles="grid.locale-en,jquery.jqGrid.min,jquery.simplemodal,customerManagement" docReadyFn="customerManagementBootstrap" />				