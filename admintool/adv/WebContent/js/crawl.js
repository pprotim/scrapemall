'use strict';

var app = angular.module('adminApp', ['ui.bootstrap','angularUtils.directives.dirPagination']);
var currentDate = new Date();
app.factory('SearchCrawlByDateService', function($http) {
	   return {
	        getCrawlData : function(dataObject) {
	        	return $http.get("searchByDate", {params:dataObject}, {timeout: 300000});
	        	
	        }
	   }
});

app.factory('GetCategoryService', function($http) {
	   return {
	        getCategoryData : function(dataObject) {
	        	return $http.get("getListCategory", null);
	        }
	   }
});

app.factory('GetSubCategoryService', function($http) {
	   return {
		   	getSubCategoryData : function(dataObject) {
	        	return $http.get("getListSubCategory", null);
	        }
	   }
});

app.factory('GetSubCategoryServiceByCategoryId', function($http) {
	   return {
		   	getSubCategoryByCategoryId : function(dataObject) {
	        	return $http.get("getListSubCategoryByCategoryId", {params:dataObject});
	        }
	   }
});


app.controller('CrawlController',['$scope', '$rootScope','$http', '$interval', '$q','$modal', '$log','SearchCrawlByDateService','GetCategoryService','GetSubCategoryService','GetSubCategoryServiceByCategoryId',
                                  function($scope, $rootScope,$http, $interval, $q, $modal, $log, SearchCrawlByDateService,GetCategoryService,GetSubCategoryService, GetSubCategoryServiceByCategoryId) {
	
		$scope.showResultTable = false;
	    $scope.zeroRecords = false;
	    $scope.showProgressBar = false;
	    $rootScope.resultRows = [];
	    $scope.tableColumns = ['logo','companyName','brandName','categoryId','category','subcategoryId','subcategory','lastUpdatedDate', 'SAVE','EDIT'];
	    
	    $scope.crawl = {
	    	searchDate : $.datepicker.formatDate('mm/dd/yy', new Date())
	    }
	    $scope.selectCategory='OPTIONS';
	    
	    var linkFields = new Array('logo');
	    $scope.shouldAddLink = function(column) {
			 for (var i = 0; i < linkFields.length; i++) { 
			    if(column === linkFields[i]) {
			    	return true;
			    }
			}
			return false;
		}
	    
	    var hiddenFields = new Array('crawlId');
	    $scope.shouldBeHidden = function(column) {
			 for (var i = 0; i < hiddenFields.length; i++) { 
			    if(column === hiddenFields[i]) {
			    	return true;
			    }
			}
			return false;
		}
	    
	    $scope.rowClicked = function(row) {
	    	alert('row clicked '+row);
	    	return false;
	    }
	    
	    $scope.convertColumnCase = function(colmn) {
	    	var convertedColmn = colmn.replace(/([A-Z])/g, ' $1').replace(/^./, function(str){ return str.toUpperCase(); })
			return convertedColmn;
	    }
	    
	    var hiddenColumns = ['categoryId','subcategoryId'];
	    $scope.toHide = function(colmn) {
	    	for (var i = 0; i < hiddenColumns.length; i++) { 
	    		if(colmn == hiddenColumns[i]) {
	    			return false;
	    		}
	    	}
	    	return true;
	      }
	    
		$scope.submitCrawlForm = function () {
	    	
			$("#info").text('');
			
	    	$scope.showProgressBar = true;
	    	$scope.showResultTable = false;
	    	$scope.zeroRecords = false;
	    	
			var searchDateVar = $scope.crawl.searchDate;
			if(searchDateVar==='') {
				alert('Invalid Search Date!!!'+searchDateVar);
				console.log('Invalid Search Date!!!'+searchDateVar);
				return false;
			}
			
			var dataObject = {
					searchDate : searchDateVar,
			};
			
			$scope.listCategory = [];
			GetCategoryService.getCategoryData(dataObject).then(function(result) {
				$scope.listCategory = result.data;
				//console.log('$scope.listCategory='+$scope.listCategory);
		    });
			
			$scope.listSubCategory = [];
			GetSubCategoryService.getSubCategoryData(dataObject).then(function(result) {
				$scope.listSubCategory = result.data;
				//console.log('$scope.listSubCategory='+$scope.listSubCategory);
		    });
			
			SearchCrawlByDateService.getCrawlData(dataObject).then(function(result) {
				$scope.resultRows = [];
		        var errors = result.error;
	     	    if(typeof errors!=='undefined'){
	     		   alert("Error getting document details");
	     	    }
	     	    else {
	     	    	if(result.data.length > 0){
	     	    		$scope.showResultTable = true;
	     	    		$scope.resultRows = result.data;
	     	    		$scope.zeroRecords = false;
	     	    		console.log('$scope.resultRows='+$scope.resultRows);
	     	    		//$scope.$watch('currentPage + numPerPage', updateFilteredItems);
	     			   
	     		   } else {
	     			   $scope.showResultTable = false;
	     			   $scope.resultRows = [];
	     			   $scope.zeroRecords = true;
	     		   }
	     	    }
	     	    $scope.showProgressBar = false;
		    });
		}//submitForm function
		
		
		$scope.listSubCategoryDropdown = {};
		$scope.onChangeGetSubCategoryList = function(categoryId, index) {
	    	console.log('Received categoryId='+categoryId);
	    	console.log('Received index='+index);
	    	
	    	var dataObject = {
	    		categoryId : categoryId,
			};
	    	
	    	GetSubCategoryServiceByCategoryId.getSubCategoryByCategoryId(dataObject).then(function(result) {
				//$scope.listSubCategory = result.data;
	    		$scope.listSubCategoryDropdown[index] = result.data;
	    		var subcategoryInitialObj = document.getElementById('scaInitial'+index);
	    		subcategoryInitialObj.style.display = 'none';
	    		var subcategoryObj = document.getElementById('sca'+index);
	    		subcategoryObj.style.display = 'block';
				console.log('$scope.listSubCategoryDropdown By GetSubCategoryServiceByCategoryId method ='+$scope.listSubCategoryDropdown[index]);
	    		//console.log('Got the sub category dropdown.....:'+$scope.listSubCategoryDropdown);
		    });
	    	
	    	return false;
	    }
	    
	    
		$scope.editRow = function(id) {
			
			console.log('id='+id);
			
			var inputCpObj = document.getElementById("icp"+id);
			inputCpObj.disabled=false;
			inputCpObj.focus();
			
			var inputBrObj = document.getElementById("ibr"+id);
			inputBrObj.disabled=false;
			
			var categoryObj = document.getElementById("ca"+id);
			categoryObj.disabled=false;
			
			var subcategoryInitialObj = document.getElementById('scaInitial'+id);//scaInitial
			subcategoryInitialObj.disabled=false;
			
			var subcategoryObj = document.getElementById('sca'+id);
			subcategoryObj.disabled=false;
			
			var saveRowButtonObj = document.getElementById('saveRowId'+id);
			saveRowButtonObj.disabled=false;
			
			//var crawlIdObj = document.getElementById("crawlId"+id);
					
		}//editRow function...
		

		$scope.saveRow = function(id) {
			
			$("#info").text('');
			
			console.log('=====================Search Date!!!'+$scope.crawl.searchDate);
			console.log('=====================INDEX VALUE!!!'+id);			
			console.log('Going to update the record with logo:'+$scope.resultRows[id].logo);
			console.log('Going to update the record with crawlId:'+$scope.resultRows[id].crawlId);
			console.log('Going to update the record with companyName:'+$scope.resultRows[id].companyName);
			console.log('Going to update the record with brandName:'+$scope.resultRows[id].brandName);
			console.log('Going to update the record with category:'+$scope.resultRows[id].category);
			console.log('Going to update the record with categoryId:'+$scope.resultRows[id].categoryId);
			console.log('Going to update the record with subcategory:'+$scope.resultRows[id].subcategory);
			console.log('Going to update the record with subcategoryId:'+$scope.resultRows[id].subcategoryId);
			
			var inputLogoObj = document.getElementById('logo'+id);
			console.log('inputLogoObj URL:'+inputLogoObj.src);
			
			var inputCpObj = document.getElementById('icp'+id);
			console.log('inputCpObj:'+inputCpObj.value);
			
			var inputBrObj = document.getElementById('ibr'+id);
			console.log('inputBrObj:'+inputBrObj.value);
			
			var categoryObj = document.getElementById('ca'+id);
			console.log('categoryObj.value:'+categoryObj.value);
			console.log('categoryObj.text:'+categoryObj.options[categoryObj.selectedIndex].text);
			
			var subcategoryObj = document.getElementById('sca'+id);
			console.log('subcategoryObj.value:'+subcategoryObj.value);
			console.log('subcategoryObj.text:'+subcategoryObj.options[subcategoryObj.selectedIndex].text);
			
			var lastUpdatedObj = document.getElementById('lu'+id);
			console.log('lastUpdatedObj:'+lastUpdatedObj.innerText);
			
			var crawlIdObj = document.getElementById("crawlId"+id);
			console.log('crawlIdObj:'+crawlIdObj.value);
			
			//validation
			if(inputCpObj.value=='' || inputBrObj.value=='' || categoryObj.value=='' || subcategoryObj.value=='')
			{
				alert('Please enter a value for: Company, Brand, Category and SubCateogry');
				return false;
			}
			
			if(categoryObj.value=='? string:OPTIONS ?' || categoryObj.options[categoryObj.selectedIndex].text=='' 
				|| subcategoryObj.value=='? string:OPTIONS ?' || subcategoryObj.options[subcategoryObj.selectedIndex].text=='')
			{
				alert('Please SELECT a value for: Category and SubCateogry');
				return false;
			}	
				
			inputCpObj.disabled=true;
			inputBrObj.disabled=true;
			categoryObj.disabled=true;
			subcategoryObj.disabled=true;
			var today = lastUpdatedObj.innerText;
			
			//creating json object
			var crawlBean = {
				logo : inputLogoObj.src,
				crawlId : crawlIdObj.value,
				companyName: inputCpObj.value,
				brandName: inputBrObj.value,
				category: categoryObj.options[categoryObj.selectedIndex].text,
				categoryId:categoryObj.value,
				subcategory: subcategoryObj.options[subcategoryObj.selectedIndex].text,
				subcategoryId:subcategoryObj.value,
				lastUpdatedDate:today
			};
			
			
			//call ajax call to controller.
			$.ajax({
			    url: "save",
			    method: 'POST',
			    contentType:'application/json',
			    data: JSON.stringify(crawlBean)
			}).done(function(data) {
			      
				//$("#info").text(data);
				
				console.log('After update the record with new values:'+data.crawlBean);
				//find the record to be updated
				var recordNumber = ($scope.pageSize * ($scope.currentPage-1))+(id);
				console.log('recordNumber which needs to be udpated in resultRows is recordNumber='+recordNumber);
				$scope.resultRows[recordNumber] = data.crawlBean;
					
				/*for ( var i = 0; i < $scope.resultRows.length; i++) {
					console.log("i value="+i+", id value="+id)
					if(i == recordNumber) {
						console.log('After updating the record with crawlId:'+$scope.resultRows[id].crawlId);
						console.log('After updating the record with companyName:'+$scope.resultRows[id].companyName);
						console.log('After updating the record with brandName:'+$scope.resultRows[id].brandName);
						console.log('After updating the record with category:'+$scope.resultRows[id].category);
						console.log('After updating the record with categoryId:'+$scope.resultRows[id].categoryId);
						console.log('After updating the record with subcategory:'+$scope.resultRows[id].subcategory);
						console.log('After updating the record with subcategoryId:'+$scope.resultRows[id].subcategoryId);
					}
				}*/
				
				console.log('After updating the record with logo:'+$scope.resultRows[id].logo);
				console.log('After updating the record with crawlId:'+$scope.resultRows[id].crawlId);
				console.log('After updating the record with companyName:'+$scope.resultRows[id].companyName);
				console.log('After updating the record with brandName:'+$scope.resultRows[id].brandName);
				console.log('After updating the record with category:'+$scope.resultRows[id].category);
				console.log('After updating the record with categoryId:'+$scope.resultRows[id].categoryId);
				console.log('After updating the record with subcategory:'+$scope.resultRows[id].subcategory);
				console.log('After updating the record with subcategoryId:'+$scope.resultRows[id].subcategoryId);
			      
				$("#info").text(data.message);
			});
			
		}
		
		$scope.items = [];
		
		$scope.animationsEnabled = true;

		$scope.open = function (item) {
			console.log(item);

			$scope.items = item;
			
		    var modalInstance = $modal.open({
		      animation: $scope.animationsEnabled,
		      templateUrl: 'myModalContent.html',
		      controller: 'ModalInstanceCtrl',
		      size: 'lg',
		      resolve: {
		        items: function () {
		          return $scope.items;
		        }
		      }
		    });

		modalInstance.result.then(function (selectedItem) {
		      $scope.selected = selectedItem;
		    }, function () {
		      $log.info('Modal dismissed at: ' + new Date());
		    });
		  };

		$scope.toggleAnimation = function () {
		    $scope.animationsEnabled = !$scope.animationsEnabled;
		  };
		  
		$scope.downloadImage = function (argument) {
			
			console.log('argument='+argument);
		}
		
		//angularUtils Pagination starts below....
		$scope.pageSize = 5;
		$rootScope.currentPage = 1;
		$scope.pageChangeHandler = function(num) {
			console.log('meals page changed to ' + num);
		};
		
		
}]);//crawlController ends....

	

	// Please note that $modalInstance represents a modal window (instance) dependency.
	// It is not the same as the $modal service used above.

	angular.module('adminApp').controller('ModalInstanceCtrl', function ($scope, $modalInstance, items) {

	  $scope.items = items;
	  $scope.selected = {
	    item: $scope.items[0]
	  };

	  $scope.ok = function () {
	    $modalInstance.close($scope.selected.item);
	  };

	  $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
	});
	


angular.module('adminApp').controller('ModalDemoCtrl', function ($scope, $modal, $log) {

});

angular.module('adminApp').controller('PaginationDemoCtrl', function($scope){

		  $scope.pageChangeHandler = function(num) {
		    console.log('going to page ' + num);
		    $("#info").text('');
		  };
	
});