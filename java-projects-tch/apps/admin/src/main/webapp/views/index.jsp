<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Test</title>
</head>
<body>
It works!!! Now let's actually test the features.
<div style="clear:both;padding-top:10px">
    <label> Select the issuer id to which the operations below need to be applied.</label>
</div>
<div style="clear:both;padding-top:5px">
    <select id="issuerId">
        <option>issuer1</option>
        <option>issuer2</option>
    </select>
</div>
<div style="clear:both;padding-top:10px">
    <label> Add/Update a Test Issuer</label>
</div>
<div style="clear:both;padding-top:5px">
    <div style="float:left;width:200px">
        <label>Enter Id</label>
    </div>
    <div style="float:left;width:200px">
        <input id="id"/>
    </div>
    <div style="clear:both;padding-top:5px">
    </div>
    <div style="float:left;width:200px">
        <label>Enter Name</label>
    </div>
    <div style="float:left;width:200px">
        <input id="name"/>
    </div>
</div>
<div style="clear:both;padding-top:5px">
    <input type="button" id="add" value="Add/Update"/>
</div>
<div style="clear:both;padding-top:10px">
    <label> Search for Test Issuer by Name</label>
</div>
<div style="clear:both;padding-top:5px">
    <div style="float:left;width:200px">
        <label>Enter Name</label>
    </div>
    <div style="float:left;width:200px">
        <input id="searchName"/>
    </div>
</div>
<div style="clear:both;padding-top:5px">
    <input type="button" id="search" value="Search"/>
</div>
<div style="clear:both;padding-top:5px">
    <div id="result">
    </div>
</div>


<script src="<%=request.getContextPath()%>/js/jquery-1.10.2.min.js"></script>
<script>
    $(document).ready(function () {
        $('#add').click(function () {
            var myObj = {'testIssuer': {'id': $('#id').val(), 'name': $('#name').val()}, 'issuerId': $('#issuerId').val()};
            $.ajax(
                    {
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        url: '<%=request.getContextPath()%>/adm/testRest',
                        data: JSON.stringify(myObj),
                        mimeType: 'application/JSON',
                        method: 'POST'
                    }
            ).done(function (data) {
                        if (data == null) {
                            alert('Successfully Added/Updated');
                        }
                    });
        });
        $('#search').click(function () {
            $.get('<%=request.getContextPath()%>/adm/testRest/search', {'issuerId': $('#issuerId').val(), 'name': $('#searchName').val()},
                    function (data) {
                        if (data.length == 0) {
                            $('#result').html('No results found for your search');
                        }
                        else {
                            $('#result').html('Your Search Result is: id=' + data.id + ',name=' + data.name);
                        }
                    });
        });
    });
</script>
</body>
</html>