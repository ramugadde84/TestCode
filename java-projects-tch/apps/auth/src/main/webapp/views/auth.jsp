<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include/common.jsp"%>
<ti:header />
<div data-role="page" id="pageLogin">

	<div data-role="header" data-position="fixed">
		<div>
			<h4>LOGIN</h4>
		</div>
	</div>
	<!-- /header -->

	<div class="ui-content" role="main" id="loginPage">

		<div class="logo">
			<img
				src='data:image/jpeg;base64,<c:out escapeXml="true" value="${login.issuerLogo}"/>'
				alt='No image configured' />
		</div>

		<form id="frmLogin">
			<div>
				<label for="username">Username:</label> <input type="text"
					name="username" id="username" value="" placeholder="Username" autocomplete="off"
					required />
			</div>

			<div>
				<label for="password">Password:</label> <input type="password"
					name="password" id="password" value="" placeholder="Password" autocomplete="off"
					required />
			</div>
			<div>
				<input type="hidden" name="iisn" id="iisn" value="${login.iisn}" />
				<input type="hidden" name="trId" id="trId" value="${login.trId}" />
				<input type="hidden" name="ciid" id="ciid" value="${login.ciid}" />
				<input type="hidden" id="redirectUrl" value="${login.redirectUrl}" />
			</div>

			<div style="text-align: right">
				<button type="button" data-inline="true" data-mini="true"
					id="cancelLogin">Cancel</button>
				<a id="login" data-role="button" data-inline="true" data-theme="e"
					data-mini="true" data-transition="slide">Login</a>
			</div>
		</form>
	</div>
	<!-- /content -->
	<div data-role="popup" id="errorDialog">
		<div data-role="header">
			<h4>Error!</h4>
		</div>
		<div data-role="content" id="errorHeading">
			<p>The UserName and Password Combination is invalid.</p>
			<div class="right">
				<a href="#" data-role="button" data-inline="true" data-mini="true"
					data-rel="back"> Ok </a>
			</div>
		</div>
	</div>

</div>
<!-- /page -->

<div data-role="page" id="pageSelectPi">

	<div data-role="header" data-position="fixed">
		<div>
			<h4>MANAGE ACCOUNTS IN WALLET</h4>
		</div>
	</div>
	<!-- /header -->

	<div class="ui-content" role="main">

		<div class="logo">
			<img
				src='data:image/jpeg;base64,<c:out escapeXml="true" value="${login.issuerLogo}"/>'
				alt='No image configured' />
		</div>

		<form id="frmSelectPi">

			<div data-role="fieldcontain" id="paymentInstrumentContainer">
				<fieldset data-role="controlgroup" data-theme="d"
					id="paymentInstruments"></fieldset>
				<br>

				<div style="text-align: right" id="paymentInstrumentsControls" >
					<a id="cancelRegistration" data-role="button" data-inline="true"
						data-mini="true"> Cancel</a> <a id="submitPi" data-rel="popup"
						data-role="button" data-inline="true" data-theme="e"
						data-mini="true" data-position-to="window" data-transition="pop">Submit</a>
				</div>
			</div>

		</form>
	</div>
	<!-- /content -->
	<div data-role="popup" id="confirmDialog" data-dismissible="false">
		<div data-role="header">
			<h4>Confirmation!</h4>
		</div>
		<div data-role="content" id="confirmHeading">
			<p>Are you sure that you want to update the accounts ?</p>
			<div style="text-align: right">
				<a href="#" data-role="button" data-inline="true" data-mini="true"
					id="cancelInstrumentSave"> No </a> 
				<a href="#pageWallet"
					data-role="button" data-inline="true" data-mini="true"
					data-theme="e" data-direction="reverse" data-transition="pop"
					id="submitPaymentInstruments"> Yes </a>
			</div>
		</div>
	</div>
	<div data-role="popup" id="errorDialogPi" data-dismissible="false">
		<div data-role="header">
			<h4>Error!</h4>
		</div>
		<div data-role="content" id="errorDialogHeading">
			<p>Error Occured.</p>
			<div class="right">
				<a href="#" data-role="button" data-inline="true" data-mini="true"
					data-rel="back" id="cancelEmptyPi"> Ok </a>
			</div>
		</div>
	</div>
</div>
<ti:footer />