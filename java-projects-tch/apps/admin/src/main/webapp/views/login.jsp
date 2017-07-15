<%@include file="include/common.jsp"%>
<ti:header bodyId="login" title="Login Page" noShowUserEnvDetails="yes" />
<div id="TCH_fieldMessage">
	<c:if test="${logoutMessage!=null}">
		<p>
			<spring:message code="${logoutMessage}" />
		</p>
	</c:if>
</div>
<div id="TCH_loginDiv">
	<form method="post" action="${controllerContextPath}/login" name="loginForm">
		<sec:csrfInput />
		<fieldset>
			<legend>Enter your login information</legend>
			<div class="TCH_labelValue">
				<label for="TCH_loginName">User ID:<span>*</span></label>
				<div class="TCH_fieldValue">
					<input type="text" name="loginId" autocomplete="off"
						id="TCH_loginName" class="TCH_inputs" tabindex="0" size="30"><br />
					<ti:error attributeName="loginId" />
				</div>
			</div>
			<div class="TCH_labelValue">
				<label for="TCH_loginPass">Password:<span>*</span></label>
				<div class="TCH_fieldValue">
					<input type="password" name="password" autocomplete="off"
						id="TCH_loginPass" class="TCH_inputs" tabindex="0" size="30"><br />
					<ti:error attributeName="password" />
				</div>
			</div>
			<input id="TCH_btnSubmit" class="TCH_button" type="submit"
				value="Login" />
			<div id="TCH_fieldError">
				<c:if test="${loginError!=null}">
					<p>
						<spring:message code="${loginError}" />
					</p>
				</c:if>
			</div>

		</fieldset>
	</form>
</div>
<ti:footer sessionTimerNotRequired="yes" />