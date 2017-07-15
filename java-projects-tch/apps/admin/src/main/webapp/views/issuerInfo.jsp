<%@include file="include/common.jsp"%>
<div class="art_left">
	<div class="sec_header">Issuer Information</div>
	<div class="sec_left">
		<input type="hidden" name="id" id="id" value="${issuer.id}"/>
		<div class="TCH_labelValue">
			<label for="name">Name:<span>*</span></label>
			<div class="TCH_fieldValue">
				<input name="name" id="name" value="${issuer.name}" class="TCH_inputs" tabindex="0" size="30"/>
			</div>
			<ti:error attributeName="name" />
		</div>
		<div class="TCH_labelValue">
			<label for="iid">IID:<span>*</span></label>
			<div class="TCH_fieldValue">
				<input name="iid" id="iid" value="${issuer.iid}" class="TCH_inputs" tabindex="0" size="30"/>
			</div>
			<ti:error attributeName="iid" />
			
		</div>
		<div class="TCH_labelValue">
			<label for="name">IISN:<span>*</span></label>
			<div class="TCH_fieldValue">
				<input name="iisn" id="iisn" value="${issuer.iisn}" class="TCH_inputs" tabindex="0" size="30"/>
			</div>
			<ti:error attributeName="iisn" />
		</div>
		<div class="TCH_labelValue">
			<label for="name">Dropzone Path:<span>*</span></label>
			<div class="TCH_fieldValue">
				<input name="dropzonePath" id="dropzonePath" value="${issuer.dropzonePath}" class="TCH_inputs" tabindex="0" size="30"/>
			</div>
			<ti:error attributeName="dropzonePath" />
		</div>
	</div>
</div>