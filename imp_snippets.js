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

+++++++++++++++++++++++++++++++++
3)Comparision of Dates
+++++++++++++++++++++++++++++++
var d1 = Date.parse("2012-11-01");
var d2 = Date.parse("2012-11-04");
if (d1 < d2) {
}
