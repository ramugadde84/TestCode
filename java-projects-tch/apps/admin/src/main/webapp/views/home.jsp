<%@include file="include/common.jsp" %>
<ti:header bodyId="home" title="Home Page" cssFiles="jquery-ui,ui.jqgrid" />
<ti:menu activeItem="home"/>
	<div id="clearfix"></div>
	<div id="TCH_welcomeDiv">
		<span><c:out value="${welcomeMessage}"></c:out></span>
	</div>
<ti:footer jsFiles="jquery.simplemodal"/>