<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta content="NO-CACHE" http-equiv="CACHE-CONTROL"/>
   		<meta content="NO-CACHE" http-equiv="PRAGMA"/>
		<title>${title}</title>
		<c:set var="staticImportPath" value="page.request.contextPath"/>
		<!-- Standard CSS Imports go here -->
		<link href="${staticContextPath}/css/style.css" media="screen" rel="stylesheet" type="text/css"/>
	</head>
	<body>
		
		<jsp:include page="${page}.jsp"/>
		<!-- Standard JS Imports go here -->
		<script type="text/javascript" src="${staticImportPath}/js/jquery-1.10.2.min.js"></script>
		<!-- Custom JS imports -->
		<c:if test="${customJsImport !=null }">
			<jsp:include page="${customJsImport}.jsp"/>
		</c:if>
		$(document).ready(function() {
			<c:if test="${jsBootstrap!=null}">
				${jsBootstrap}();
			</c:if>
		});
	</body>
</html>