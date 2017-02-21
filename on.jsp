<%@ include file="/WEB-INF/jsp/eis/include.jsp"%>
<c:choose>
	<c:when test="${jsonResponse != null}">
		${jsonResponse}
	</c:when>
	<c:otherwise>
		<form:form commandName="sreActivityCommand" name="sreActivityCommand" method="post" cssClass="panel form-horizontal">
			<form:hidden path="task" />


			<!--Title -->
			<div class="panel-heading">
				<h1 role="heading" aria-level="1" class="panel-title">${sreActivityCommand.title}</h1>
			</div>
			<!--End of the Title -->
			<!--panel body +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
			<div class="panel panel-default" id="SREActivityPanel" style="box-shadow: none;">

				<div class="panel-body">
					<div class="row ">
						<div class="form-group col-sm-5 ">Listed below is the initial ATQ request</div>
						<div class="col-md-6">
							<button type="button" class="btn btn-default" onclick="">Return ATQ to Sales for More Information</button>
						</div>
						<div class="col-sm-6 form-group">
							<label for="comments" class="control-label col-sm-2">Comments:</label>
							<div class="col-sm-8">
								<form:textarea path="comments" cssClass="form-control input-sm" maxlength="2000" rows="3" placeholder="Comments" />
							</div>
						</div>
					</div>
					<!--first row -->
					<div class="row ">
						<div class="form-group col-sm-5 ">
							<eisTags:requiredLabel css="control-label col-sm-5" _for="salesPersonName" label="Sales Person:" />
							<div class="col-md-6">
								<form:input path="salesPersonName" cssClass="form-control input-sm locBack" maxlength="20" required="true" placeholder="Sales Person" />
							</div>
						</div>
					</div>
					<!--End of first row -->

					<!--second row -->
					<div class="row ">
						<div class="form-group col-sm-5 ">
							<eisTags:requiredLabel css="control-label col-sm-5" _for="agencyName" label="Agency :" />
							<div class="col-md-6">
								<form:input path="agencyName" cssClass="form-control input-sm locBack" maxlength="20" required="true" placeholder="Agency" />
							</div>
						</div>
					</div>
					<!--end of second row -->

					<!--Third Row -->
					<div class="row ">
						<div class="form-group col-sm-5 ">
							<eisTags:requiredLabel css="control-label col-sm-5" _for="romeOppurtunityNumber" label="ROME Opportunity #:" />
							<div class="col-md-6">
								<form:input path="romeOppurtunityNumber" cssClass="form-control input-sm locBack" maxlength="20" required="true" placeholder="ROME Opportunity" />
							</div>
						</div>
					</div>
					<!-- End of Third row -->

					<!-- Fourth Row -->
					<div class="row ">
						<div class="form-group col-sm-5 ">
							<eisTags:requiredLabel css="control-label col-sm-5" _for="atqNumber" label="Draft ATQ #:" />
							<div class="col-md-6">
								<form:input path="atqNumber" cssClass="form-control input-sm locBack" maxlength="50" required="true" placeholder="Draft ATQ" />
							</div>
						</div>
					</div>
					<!-- End of Fourth Row -->

					<!-- Fifth Row -->
					<div class="row ">
						<div class="form-group col-sm-5 ">
							<eisTags:requiredLabel css="control-label col-sm-5" _for="atqDueDate" label="Due Date :" />
							<div class="col-md-6">
								<form:input path="atqDueDate" cssClass="form-control input-sm locBack" maxlength="50" required="true" placeholder="Due Date " />
							</div>
						</div>
					</div>
					<!-- End of Fifth Row -->

					<!-- Sixth Row -->
					<div class="row ">
						<div class="form-group col-sm-5 ">
							<eisTags:requiredLabel css="control-label col-sm-5" _for="pricerName" label="Pricer Name:" />
							<div class="col-md-6">
								<form:input path="pricerName" cssClass="form-control input-sm locBack" maxlength="50" required="true" placeholder="Pricer Name" />
							</div>
						</div>
					</div>
					<!-- End of Sixth Row -->

				</div>
			</div>
			<!--panel body ends+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
			
			<!--Grid Div Starts -->
			

				<div class="panel-heading">
					<h2 class="panel-title">Existing SREs - CLIN List</h2>
				</div>
				<div style="display: none;" data-notice-contact-list='${sreActivityCommand.existingSREModelString}'></div>
				<div class="panel-body">
					<div id="filterBar"></div>
					<table id="sreActivityTable" class="datatable table table-striped table-hover table-condensed table-bordered" data-toggle="table" data-sort-name="rowPk" data-sort-order="desc" data-pagination="true" data-page-list="2,5,10,25,100,All" data-page-size="5" data-filter-control="true" data-side-pagination="client" data-checkbox-header="false" data-maintain-selected="true" data-click-to-select="false" data-select-all="false" data-primary-key="rowPk">
						<thead>
							<tr>
								<th data-visible="false" data-field='rowPk' data-sortable='true' id="rowPk">CLIN</th>
								<th data-visible="true" data-field='caseNumber' data-filter-control="input" data-sortable='true' id="caseNumber">Case Number</th>
								<th data-visible="true" data-field='manufactureName' data-filter-control="input" data-sortable='true' id="manufactureName">Manufacturer</th>
								<th data-visible="true" data-field='modelNumber' data-filter-control="input" data-sortable='true' id="modelNumber">Part Number</th>
								<th data-visible="true" data-field='modelPartDescription' data-filter-control="input" data-sortable='true' id="modelPartDescription">Description</th>
								<th data-visible="true" data-field='eolDate' data-filter-control="input" data-sortable='true' id="eolDate">EOL Date</th>
								<th data-visible="true" data-field='frequency' data-filter-control="input" data-sortable='true' id="frequency">Frequency</th>
								<th data-visible="true" data-field='mrcOptionTerm' data-filter-control="input" data-sortable='true' id="mrcOptionTerm">MRC Option</th>
								<th data-visible="true" data-field='quantity' data-filter-control="input" data-sortable='true' id="quantity">Quantity</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty sreActivityCommand.existingSREModels}">
									<c:forEach var="sreActivityValue" items="${sreActivityCommand.existingSREModels}" varStatus="loopStatus">
										<tr>
											<td>${sreActivityValue.rowPk}</td>
											<td>${sreActivityValue.caseNumber}</td>
											<td>${sreActivityValue.manufactureName}</td>
											<td>${sreActivityValue.modelNumber}</td>
											<td>${sreActivityValue.modelPartDescription}</td>
											<td>${sreActivityValue.eolDate}</td>
											<td>${sreActivityValue.frequency}</td>
											<td>${sreActivityValue.mrcOptionTerm}</td>
											<td>${sreActivityValue.quantity}</td>
   										</tr>
									</c:forEach>
								</c:when>
							</c:choose>
						</tbody>
					</table>
				</div>
			
			<!--Grid Div Ends -->
			<div class="panel-footer">
				<div class="row">
					<div class="col-sm-4">
						<button class="btn btn-default dropdown-toggle" type="button"  id="downloadSREActivity" data-toggle="dropdown"> Download CLIN list SREs to Excel  <span class="caret"></span>
						</button>
						</div>
						<!-- <ul class="dropdown-menu">
							<li><a href="#" class="downloadAll" id="downloadSREActivity">Download Excel</a></li>
						</ul>  -->
						<div class="col-md-5">
					   <button type="button" id="createnewquote" class="btn btn-default" onclick="">Upload Final ATQ in Excel Format</button>
					   </div>							
					</div>
					
					
					<div class="row">
						<div class="col-sm-4">
							<button type="button" id="createnewquote" class="btn btn-default">Download New SRE Attachment</button>
						</div>
						<div class="col-md-5">
							<button type="button" id="createnewquote" class="btn btn-default" onclick="">Quote Complete, Return to Sales</button>
						</div>
					</div>
					</div>
					
					
					
		</form:form>
		<!--end of the side panel -->
	</c:otherwise>
</c:choose>
