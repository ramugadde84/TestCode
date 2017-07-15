<%@include file="include/common.jsp"%>

<ti:header bodyId="issuer" title="User Maintenance"
	cssFiles="themes/redmond/jquery-ui,ui.jqgrid,custom" />
<ti:menu activeItem="user" />
<!-- Main Content -->
<div class="TCH_pageHeader">
	<label>Vault Management System</label>
</div>
<div id="clearfix"></div>
<hr class="TCH_ruler"/>
<form id="TCH-issuerMgmt" name="userConfiguration">
	<sec:csrfInput />
	<div id="TCH_fieldMessage" style="display: none;"></div>
	<input type="hidden" id="id" name="id"/>
	<input type="hidden" id="isEdit"/>
	<fieldset>
		<legend>TCH User Management</legend>
		<div class="TCH_gridContent">
			<div class="searchIssuerContent">
				<div class="TCH_labelValue">
					<label for="TCH_userid">Search User ID:<span></span></label>
					<div class="TCH_fieldValue">
						<input type="text" id="searchUserId" class="TCH_inputs" tabindex="0" />
						<span>
							<input class="searchIcon" type="button" alt="Search User ID" title="Search User ID" id="searchBtn"/>
						</span>
						<input id="TCH_addTCHUser" class="TCH_button" type="button" value="Add TCH User" />
						<input id="TCH_addIssuerUser" class="TCH_button" type="button" value="Add Issuer User" />
					</div>
				</div>
			</div>
			<div id="clearfix"></div>
			<table id="grid"></table>
			<div id="TCH_userManageList"><span id="numUsers"></span>Users</div>
		</div>	
	</fieldset>
</form>

<!--Add User modal content -->
<form id="userForm" name="userForm">
	<sec:csrfInput />
	<div id="TCH_addUserModal">
		<fieldset>
			<legend>TCH User Management</legend>
			<div class="TCH_pageHeader" id="userModalTitle"></div>
			<div class="editmodalContent">
				<div class="TCH_labelValue divHeight">
					<label for="TCH_userid">User ID:<span>*</span></label>
					<div class="TCH_fieldValue"><input type="text" name="userId" id="userId" class="TCH_inputs" value="" tabindex="0" size="30"><br /></div>
					<div id="userIdErrorMsg" class="fieldErrMsg">
					</div>
				</div>
				<div class="TCH_labelValue divHeight">
					<label for="TCH_firstname">First Name:<span>*</span></label>
					<div class="TCH_fieldValue"><input type="text" name="firstName" id="firstName" class="TCH_inputs" value="" tabindex="0" size="30"><br /></div>
					<div id="firstNameErrorMsg" class="fieldErrMsg">
					</div>
				</div>
				<div class="TCH_labelValue divHeight">
					<label for="TCH_lastname">Last Name:<span>*</span></label>
					<div class="TCH_fieldValue"><input type="text" name="lastName" id="lastName" class="TCH_inputs" value="" tabindex="0" size="30"><br /></div>
					<div id="lastNameErrorMsg" class="fieldErrMsg">
					</div>
				</div>
				<div class="TCH_labelValue tchUser" id="tchUserRolesDiv">
					<label for="TCH_roles">Roles:<span>*</span></label>
					<div class="TCH_fieldValue">
						<input type="checkbox" value="3" name="authorizedRoles" id="authorizedRoles_3"/><span>Issuer User Management</span><br />
						<input type="checkbox" value="1" name="authorizedRoles" id="authorizedRoles_1"/><span>TCH User Management</span><br />
						<input type="checkbox" value="2" name="authorizedRoles" id="authorizedRoles_2"/><span>Vault Configuration</span><br />
					</div>
					<div id="rolesErrorMsg" class="fieldErrMsg">
					</div>
				</div>
				<div class="TCH_labelValue issuerUser divHeight">
					<label for="TCH_roles">Roles:<span>*</span></label>
					<div class="TCH_fieldValue">
						<input type="checkbox" checked="checked" disabled="disabled" name="issuerRoles"/><span>Issuer User Management</span><br />
					</div>
				</div>
				<div class="TCH_labelValue issuerUser divHeight2">
					<label for="TCH_lastname">Issuer:<span>*</span></label>
					<div class="TCH_fieldValue">
						<select id="iisn" name="iisn">
							<option value=''>Select Issuer</option>
							<c:forEach var="issuer" items="${issuers}">
								<option value="${issuer.iisn}"><c:out value="${issuer.issuerName}"></c:out></option>
							</c:forEach>
						</select>
					</div>
					<div id="iisnErrorMsg" class="fieldErrMsg">
					</div>
				</div>
			</div>
			<input type="hidden" name="isIssuerUser" id="isIssuerUser" value="N"/>
		</fieldset>
		<div id="savemodalContentBtn">
				<input id="TCH_saveChange" class="TCH_button editIsuChange" type="button" value="Save User" />
		</div>
	</div>
</form>

<!-- Delete User modal content -->
<div id="TCH_delIssuerModal">
	<div class="TCH_pageHeader" id="delModalHeader"></div>
	<div class="deletemodalContent">
		<div class="TCH_labelValue">
			<label>Are you sure you want to Delete?</label>
		</div>
	</div>
	<div id="delmodalContentBtn">
		<input id="TCH_delUser" class="TCH_button saveChange" type="button" value="Delete" />
		<input id="TCH_cancelDelete" class="TCH_button saveChange modalClose" type="button" value="Cancel" />
	</div>
</div>



<script>
	var userConfigurationJson = <c:out escapeXml="false" value="${users}"/>;
</script>

<ti:footer
	jsFiles="grid.locale-en,jquery.jqGrid.min,jquery.simplemodal,common,user"
	docReadyFn="userBootstrap" />