<%@ attribute name="attributeName" required="true" rtexprvalue="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "spring"    uri = "http://www.springframework.org/tags" %>

<c:if test="${errors[attributeName]!=null}">
	<div class="fieldErrMsg">
		<spring:message code="${attributeName}" var="attributeLabel"/>
		<label><spring:message code="${errors[attributeName]}" arguments="${attributeLabel}"></spring:message></label>			
	</div>
</c:if>