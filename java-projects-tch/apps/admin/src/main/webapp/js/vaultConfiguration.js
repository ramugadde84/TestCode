// Vault configuration validation
function vaultConfigValidation(){
	var urlValidationRegx = /\b(https)/;
//Validation for switch end point URL.
	if($("#switchEndpointUrl").val() == ""){
		alert("Please enter Switch Endpoint URL.");
		return false;
	}
	if(!urlValidationRegx.test($("#switchEndpointUrl").val())){
		alert("Switch Endpoint is invalid and cannot be saved.");
		return false;
	}
//validation for file root directory
	if($("#batchFileRootDirectory").val() == ""){
		alert("Please enter Batch File Root Directory.");
		return false;
	}
	var filePathRegx = /^([A-Za-z]:)(\\[A-Za-z_\-\s0-9\.]+)+$/;
	if(!filePathRegx.test($("#batchFileRootDirectory").val())){
		alert("Batch File Root Directory is invalid.");
		return false;
	}
// Cloud switch id empty validation
	if($("#cloudSwitchId").val() == ""){
		alert("Please enter cloud switch id. it should not be empty");
		return false;
	}
// Vault switch id empty validation
	if($("#vaultSwitchId").val() == ""){
		alert("Please enter vault switch id. it should not be empty");
		return false;
	}
	document.vaultConfiguration.submit();
}