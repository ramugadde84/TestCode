<!-- JSTL Tags -->
<%@ taglib prefix="c"           uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"         uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"          uri="http://java.sun.com/jsp/jstl/functions" %>

<!--  Sprng Tag library -->
<%@ taglib prefix="form"        uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "spring"    uri = "http://www.springframework.org/tags" %>

<!-- Spring Security Tag library -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Custom Tag Library -->
<%@ taglib prefix="ti"          tagdir="/WEB-INF/tags" %>

<!-- Set common scope variables -->
<c:set var="controllerContextPath"  value="${pageContext.request.contextPath}/app"    scope="request" />
<c:set var="staticContextPath"      value="${pageContext.request.contextPath}"          scope="request" />