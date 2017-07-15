<%@include file="include/common.jsp"%>
<div id="clearfix"></div>
<div id="authDiv" class="art_right">
	<div class="sec_header">Authentication</div>
	<div class="sec_right">
		<div class="TCH_labelValue authmech">
			<label for="Mechanism">Mechanism:<span>*</span></label>
			<div class="TCH_fieldValue">
				<select id="authMechanism" name="authMechanism" class="TCH_inputs"
					tabindex="0">
					<c:forEach var="authenticationMechanism"
						items="${authenticationMechanisms}">
						<option value="${authenticationMechanism.id}"
							<c:if test="${issuer.authMechanism==authenticationMechanism.id }">selected="true"</c:if>>
							<c:out value="${authenticationMechanism.name}"></c:out>
						</option>
					</c:forEach>
				</select>
			</div>
			<ti:error attributeName="authMechanism" />
		</div>
		<div class="TCH_labelValue authOption1" style="display: none">
			<label for="TCH_realBin">Authentication Application URL:<span>*</span></label>
			<div class="TCH_fieldValue authOption1">
				<input type="text" id="TCH_realBin" class="TCH_inputs"
					name="authenticationAppUrl" tabindex="0" size="30"
					value="${issuer.authenticationAppUrl}"><br />
			</div>
			<div id="clearfix"></div>
			<ti:error attributeName="authenticationAppUrl" />
		</div>
		<div class="TCH_labelValue authOption5" style="display: none">
			<label for="TCH_realBin">Auth Endpoint:<span>*</span></label>
			<div class="TCH_fieldValue authOption5">
				<input type="text" id="TCH_realBin" class="TCH_inputs"
					name="authEndpoint" tabindex="0" size="30"
					value="${issuer.authEndpoint}"><br />
			</div>
			<ti:error attributeName="authEndpoint" />
		</div>
		<div class="TCH_labelValue authOption5" style="display: none">
			<label for="TCH_tokenBin">Account List Endpoint:<span>*</span></label>
			<div class="TCH_fieldValue authOption5">
				<input type="text" id="TCH_tokenBin" class="TCH_inputs"
					name="accountListEndpoint" tabindex="0" size="30"
					value="${issuer.accountListEndpoint}"><br />
			</div>
			<ti:error attributeName="accountListEndpoint" />
		</div>
		<div class="TCH_labelValue authOption3" style="display: none">
			<label for="TCH_succYes">Disable Credentials after Successful
				Login?<span>*</span>
			</label>
			<div class="TCH_fieldValue TCH_radiobtns">
				<input type="radio" id="TCH_succYes"
					name="disableCredentialsAfterLogin" class="TCH_inputs" tabindex="0"
					value="1"
					<c:if test="${issuer.disableCredentialsAfterLogin==true}">checked="checked"</c:if> /><span>Yes</span>
				<input type="radio" id="TCH_succNo"
					name="disableCredentialsAfterLogin"
					<c:if test="${issuer.disableCredentialsAfterLogin==false}">checked="checked"</c:if>
					class="TCH_inputs" tabindex="0" value="0" /><span>No</span>
			</div>
			<ti:error attributeName="disableCredentialsAfterLogin" />
		</div>
		<div class="TCH_labelValue authOption3 authOption5"
			style="display: none">
			<div class="autherr_Msg">
				Authentication Error Message<span>*</span>
			</div>

			<div class="TCH_fieldValue">
				<div class="autherr_Msg">
					<textarea rows="4" cols="50" id="authErrorMessage" name="authErrorMessage"><c:out value="${issuer.authErrorMessage}"></c:out></textarea>
				</div>
			</div>
			<ti:error attributeName="authErrorMessage" />
		</div>
		<div class="TCH_labelValue authOption3 authOption5"
			style="display: none">
			<div class="authlck_Msg">
				Authentication Lockout Message<span>*</span>
			</div>

			<div class="TCH_fieldValue">
				<div class="authlck_Msg">
					<textarea rows="4" cols="50" id="authLockoutMessage" name="authLockoutMessage"><c:out value="${issuer.authLockoutMessage}"></c:out></textarea>
				</div>
			</div>
			
			<ti:error attributeName="authLockoutMessage" />
		</div>
		<div class="TCH_labelValue failedLogin authOption3"
			style="display: none">
			<label for="TCH_tokenBin">Failed login attempts to trigger
				lockout:<span>*</span>
			</label>
			<div class="TCH_fieldValue authOption3">
				<input type="text" id="TCH_tokenBin" class="TCH_inputs"
					value="${issuer.failedAttemptsToTriggerLockout}" tabindex="0"
					name="failedAttemptsToTriggerLockout"><br />
			</div>
			<div id="clearfix"></div>
			<ti:error attributeName="failedAttemptsToTriggerLockout" />
		</div>
		<div class="TCH_labelValue authOption3 authOption5"
			style="display: none">
			<label for="TCH_uploadLogo authOption3">Upload Issuer Logo:<span>*</span></label>
			<div class="TCH_fieldValue authOption3 authOption5">
				<input type="file" id="issuerLogo" name="issuerLogo"
					class="TCH_inputs" tabindex="0" size="30"><br />
				
				<div class="notes authOption3 authOption5" style="display: none">
					<span>*</span>JPEG File only <br /> <span>*</span>Dimensions
					should not exceed 200 x 500<br />
				</div>
				<c:if test="${issuer.id!=null}">
				<c:if test="${issuer.fileName!=null}">
					<label for="TCH_issuerLogoFileName" >Uploaded File</label>
					<label><c:out value="${issuer.fileName}"></c:out></label>
				</c:if>
				</c:if>
				<input id="fileName" type="hidden" name="fileName" value="${issuer.fileName}">
			</div>
			<ti:error attributeName="issuerLogo" />
		</div>
	</div>
</div>
