<!DOCTYPE html>

<%@ attribute name="title" required="false" rtexprvalue="true"%>
<%@ attribute name="bodyId" required="false" rtexprvalue="true"%>
<%@ attribute name="cssFiles" required="false" rtexprvalue="true"%>
<%@ attribute name="noShowUserEnvDetails" required="false" rtexprvalue="true"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ tag body-content="scriptless"%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta content="NO-CACHE" http-equiv="CACHE-CONTROL" />
<meta content="NO-CACHE" http-equiv="PRAGMA" />
<sec:csrfMetaTags />
<c:choose>
	<c:when test="${not empty title}">
		<title>${title}</title>
	</c:when>
	<c:otherwise>
		<title>{{App Title}}</title>
	</c:otherwise>
</c:choose>

<link href="${staticContextPath}/css/styles.css" media="screen"
	rel="stylesheet" type="text/css">


<%-- Import all the explicitly specified (as attribute) CSS files here. comma seperated list --%>
<c:if test="${not empty cssFiles}">
	<c:forEach var="cssFile" items="${cssFiles}">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${staticContextPath}/css/${cssFile}.css" />
	</c:forEach>
</c:if>

<!-- Common Header -->


<%-- Optional body content specified within the header tag is evaluated and inserted here --%>
<jsp:doBody />

</head>

<body id="${bodyId}">
	<div class="TCH_wrapper">
		<div id="TCH_header">
			<img src="${staticContextPath}/images/tch-logo.png"
				alt="The Clearence House LLC" title="The Clearence House LLC" />
		
			<c:if test="${noShowUserEnvDetails==null}">
				<div class="DetailsContainer">
					<div id="TCH_envDetails"><span>Environment:</span>
					<span class="TCH_yellow"><c:out value="${environment}"></c:out></span></div>
					<div id="TCH_logDetails"><span>Logged in as:</span>
						<span class="TCH_yellow"><sec:authentication property="principal.username" /></span>
					</div>
					<div id="TCH_timeDetails">
						<fmt:formatDate pattern="EEEEEEEEE, MMM d, y - hh:mm:ss a" value="${timestamp}" />
					</div>
				</div>
				<sec:authentication property="principal.username"  var="currentUserId"/>
				<input type="hidden" id="currentUserId" value="${currentUserId}"/>
			</c:if>
		</div>