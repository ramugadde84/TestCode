<%@ attribute name="activeItem" required="true" rtexprvalue="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- This is common overall application -->
<div id="TCH_menu">
	<ul id="menu-bar">
		<li <c:if test="${activeItem=='home'}">class="active"</c:if>><a href="${controllerContextPath}/home">Home</a></li>
		<li <c:if test="${activeItem=='issuer'}">class="active"</c:if>><sec:authorize url="/app/issuer/**"><a href=#>Issuer Maintenance <span>v</span></a></sec:authorize>
			<ul id="submenu-bar">
				<li ><sec:authorize url="/app/issuer/**"><a href="${controllerContextPath}/issuer/list">Issuer Configuration</a></sec:authorize></li>
			</ul>
		</li>
		<li <c:if test="${activeItem=='tokenRequestor'}">class="active"</c:if>><sec:authorize url="/app/tokenRequestor/**"><a href=#>Token Requestor Maintenance <span>v</span></a></sec:authorize>
			<ul id="submenu-bar">
				<li <c:if test="${activeItem=='tokenRequestor'}">class="active"</c:if>><sec:authorize url="/app/tokenRequestor/**"><a href="${controllerContextPath}/tokenRequestor/list">Token Requestor Configuration</a></sec:authorize></li>
			</ul>
		</li>
		<li <c:if test="${activeItem=='user' || activeItem=='customer'}">class="active"</c:if>><sec:authorize url="/app/user/any/**"><a href="#">User Maintenance <span>v</span></a></sec:authorize>
			 <ul id="submenu-bar">
			 	<sec:authorize url="/app/user/customer/**">
				 <li><a href="${controllerContextPath}/user/customer/list">Issuer User Management</a></li>
				</sec:authorize>
				<sec:authorize url="/app/user/usermgmt/**">
				 <li><a href="${controllerContextPath}/user/usermgmt/list">TCH User Management</a></li>
				</sec:authorize>
			 </ul>
		</li> 
		<div id="TCH_logoutBtn">
			<a href="#" id="logout">Logout</a>
		</div> 
	</ul>
</div>
<div id="clearfix"></div>	
<form name="logOutForm" action="${controllerContextPath}/logout" method="POST">
	<sec:csrfInput />
	<input type="hidden" id="isSessionExpired" name="isSessionExpired"/> 
</form>
<!-- End -->
