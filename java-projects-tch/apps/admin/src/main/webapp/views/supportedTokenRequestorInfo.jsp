<%@include file="include/common.jsp"%>
<div class="art_left">
	<div class="sec_header">Supported Token Requestors</div>
	<div class="notes">
		<span>*</span> Select at least one Token Requestor
		<div style="clear:both;padding-top:10px">
		<ti:error attributeName="selectedTokenRequestors" />
		</div>
	</div>
	
	<div class="sec_left divheight">
		<div id="TCH_supp_tckns" class="TCH_inputs">
			<c:forEach var="tokenRequestor" items="${tokenRequestors}">
				<input type="checkbox" name="selectedTokenRequestors"
					value="${tokenRequestor.id}" <c:if test="${issuer.currentTokenRequestors[tokenRequestor.id]!=null}">checked="checked"</c:if> />
				<span><c:out value="${tokenRequestor.name}"></c:out></span>
				<br>
			</c:forEach>
		</div>
	</div>
</div>
