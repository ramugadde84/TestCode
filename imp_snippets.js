# TestCode
####################################
1)Split function:
####################################
var splittValues = fileName.split(",");
splittValues.forEach(function(item) {
});

######################################
2)How to get Input check box values
######################################
var selected = new Array();
$('input:checked').each(function() {
	selected.push($(this).attr('id'));
});

