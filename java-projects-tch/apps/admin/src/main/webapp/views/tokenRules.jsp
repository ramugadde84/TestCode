<div class="TCH_pageHeader">
	<h3>Token Requestor / Token Rules</h3>
</div>
<div id="TCH_fieldMessage"></div>
<div id="TCH_tknfield1">
	<fieldset>
		<legend>Token(s) Details</legend>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_push">BIN<span>*</span></label>
			<div class="TCH_fieldValue">
				<select id="TCH_push" class="TCH_inputs" tabindex="0">
			   </select>
			</div>
			<div id="tokenRulesErrors8"></div>
		</div>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_succYes">Push on creation?<span>*</span></label>
			<div class="TCH_fieldValue TCH_radiobtns">
				<input type="radio" id="tokenPushOnCreation" name="tokenPushOnCreation"
					class="TCH_inputs" tabindex="0" name="Yes" value="1" /> <span
					class="radText">Yes</span> 
					<input type="radio" id="tokenPushOnCreation"
					name="tokenPushOnCreation" class="TCH_inputs" tabindex="0" name="No" value="0" /> 
					<span class="radText">No</span>
			</div>
			<div id="tokenRulesErrors7"></div>
		</div>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_succYes">Pull Enabled?<span>*</span></label>
			<div class="TCH_fieldValue TCH_radiobtns">
				<input type="radio" id="tokenPullEnable" name="tokenPullEnable" tabindex="0"
					name="Yes" value="1"/> <span class="radText">Yes</span> <input
					type="radio" id="tokenPullEnable" name="tokenPullEnable" tabindex="0" name="No" value="0"/>
				<span class="radText">No</span>
			</div>
			<div id="tokenRulesErrors6"></div>
		</div>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_EXP">Expiration from Issuance:<span>*</span></label>
			<div class="TCH_fieldValue">
				<input type="text" id="tokenExpirationMinutes" name="tokenExpirationMinutes " class="TCH_inputs" tabindex="0"
					size="30"> <span>minutes</span>
			</div>
			<div id="tokenRulesErrors5"></div>
		</div>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_deToken">Expiration After Number of
				De-tokenized Requests:<span>*</span>
			</label>
			<div class="TCH_fieldValue">
				<input type="text" id="numberOfDetokenizedRequests" class="TCH_inputs" tabindex="0"
					size="30"><br />
			</div>
			<div id="tokenRulesErrors4"></div>
		</div>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_singleDollar">Expiration After Single Dollar
				Amount Transaction:<span>*</span>
			</label>
			<div class="TCH_fieldValue">
				<input type="text" id="tokenExpireAfterSingleDollarAmountTransaction" class="TCH_inputs"
					tabindex="0" size="30"><br />
			</div>
			<div id="tokenRulesErrors3"></div>
		</div>
	</fieldset>
</div>
<div id="TCH_tknfield2">
	<fieldset>
		<legend>Maximums</legend>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_DPI">Number of Token(s) per day:<span>*</span></label>
			<div class="TCH_fieldValue">
				<input type="text" id="dpiPerDay" class="TCH_inputs" tabindex="0"
					size="30"><br />
			</div>
			<div id="tokenRulesErrors2"></div>
		</div>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_DPIDollar">Dollar Amount for Expired
				Token(s):<span>*</span>
			</label>
			<div class="TCH_fieldValue">
				<input type="text" id="dollarAmountForExpireDpi" class="TCH_inputs"
					tabindex="0" size="30">
			</div>
			<div id="tokenRulesErrors1"></div>
		</div>
		<div class="TCH_labelValue divHeight">
			<label for="TCH_DPItime">Time After Expiration for Token
				Reuse:<span>*</span>
			</label>
			<div class="TCH_fieldValue">
				<input type="text" id="timeInMinutesAfterExpirationForReuse" class="TCH_inputs" tabindex="0"
					size="30"> <span class="minutes">minutes</span>
			</div>
			<div id="tokenRulesErrors"></div>
			<div>
			   <input type="hidden" id="hidden">
			</div>
		</div>
	</fieldset>
</div>
<div id="clearfix"></div>
<div id="webmodalContentBtn">
	<input id="TCH_saveChange" class="TCH_button saveChange" type="button"
		value="Save Changes" />
</div>
