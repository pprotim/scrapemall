<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html data-ng-app='adminApp'>
<head>

<script src="js/angular/angular.js"></script>
<script src="js/angular/angular-animate.js"></script>
<script src="js/angular-bootstrap/ui-bootstrap-tpls.js"></script>
<script src="js/crawl.js"></script>

<!-- Pagination js files -->
<script src="js/angularUtils/dirPagination.js"></script>

<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />

<style type="text/css">
div.ui-datepicker{
 font-size:12px;
}
</style>
<script type="text/javascript">
	  	  
	setFromDate = function(fromDateReturn){
	alert('fromDateReturn='+fromDateReturn);
		$( "#searchDateId" ).datepicker('setDate', fromDateReturn);
	}
	var date = '';
	$(function() {
		showDatePicker();
	});
	
	function changeColor(id)
	{
	  document.getElementById(id).style.color = "#ddf1ff"; // forecolor
	  document.getElementById(id).style.backgroundColor = "#6c0031"; // backcolor
	}

</script>
</head>

	<div align="center" style='background-color:#00EF21;'><H1 style='align:center;width: 90%;'> Control Center </H1></div>
	<div data-ng-controller='CrawlController'>
		<div class="login-banner" id="login-banner">
			<div class="form-box bg-gray" id="login-box">
				<div class="header">
					<h3>Adv Control Center</h3>
				</div>
			
			</div>
			<form method="POST" name='searchForm'  novalidate>
			<%-- action="<c:url value='/searchByDate'/>"  --%>
				<table border=1 width="80%" align="center"> 
					<tr>
						<td class="search" align="center" width="40%"> Search by Date (MM/DD/YYYY) </td>
						<td width="40%"><input id="datepicker" name="searchDate" type="text" data-ng-model="crawl.searchDate"/> </td>
						<td width="20%"><button type="button" id='searchId' class="btn btn-primary" data-ng-click="submitCrawlForm()">Submit</button></td>
					</tr>
				</table>
			</form>
			<div id='info' style='color:red;text:bold;'></div>
			<br>
			<div id='dateErrorId' style='color:red;text:bold;'>${errorDate}</div>
			
			<table width="97%">
				<tr>
				 	<td>
					  	<div data-ng-show=showProgressBar id="progressBarId" ><img src="images/ajax-loader.gif" 
					  		style="position: center;margin: auto;top: 0px;right: 500px; center: 0px; bottom: 0px;"></div>
					  	<div data-ng-show=zeroRecords style='text-align: center;' 
					  		class="alert alert-success"><strong>No Records Found</strong></div>
					  	<div data-ng-show=showResultTable>
					  		<%@include  file="crawl.html" %>
					  	</div>
					</td>
			     </tr>
			</table>
			
		</div>
	
	</div>
	
	<%@include  file="crawl_modal.html" %>
	<table width="100%" border=0>
		<tr>
		 	<td>
			  	<div data-ng-controller="PaginationDemoCtrl" class="other-controller">
		          <div class="text-center">
		          <dir-pagination-controls boundary-links="true" on-page-change="pageChangeHandler(newPageNumber)" template-url="jsp/dirPagination.tpl.html"></dir-pagination-controls>
		          </div>
		        </div>
			</td>
	     </tr>
	</table>

	
</html>