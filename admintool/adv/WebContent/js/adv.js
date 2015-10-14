'use strict';
var currentDate;
function showDatePicker(){
currentDate = new Date();
	$( "#datepicker" ).datepicker({
		inline: true,
		showOn: "button",
		buttonImage: "images/calendar.gif",
		buttonImageOnly: true,
		buttonText: "Select date",
		showWeek: true,
	    dateFormat: "mm/dd/yy",
	    //minDate: "-1d",
	    maxDate: "+0d",
	    changeMonth: true,
	    changeYear: true,		
		});
	$("#datepicker").datepicker("setDate", currentDate);
}

function submitForm() {
	
	var dateObject = document.getElementById('datepicker');
	document.forms[0].submit();
	
/*	var dateObject = {searchDate:$('#datepicker').val()};
	$.ajax({
	    url: "searchByDate",
	    method: 'POST',
	    data : dateObject
	}).done(function(data) {
		console.log(data);
	});*/
	
}

function companyClicked(id) {
	
	var obj = document.getElementById(id);
	
	var inputObj = document.getElementById('i'+id);
	
	inputObj.style.display='block';

	obj.innerText='';
}

function brandClicked(id) {

	var obj = document.getElementById(id);
	
	var inputObj = document.getElementById('i'+id);
	
	inputObj.style.display='block';
	
	obj.innerText='';
	
}

function saveRow(id) {
	
	var inputCpObj = document.getElementById('icp'+id);
	console.log('inputCpObj:'+inputCpObj.value);
	
	var inputBrObj = document.getElementById('ibr'+id);
	console.log('inputBrObj:'+inputBrObj.value);
	
	var categoryObj = document.getElementById('ca'+id);
	console.log('categoryObj:'+categoryObj.value);
	
	var subcategoryObj = document.getElementById('sca'+id);
	console.log('subcategoryObj:'+subcategoryObj.value);
	
	var lastUpdatedObj = document.getElementById('lu'+id);
	console.log('lastUpdatedObj:'+lastUpdatedObj.innerText);
	
	var crawlIdObj = document.getElementById("crawlId"+id);
	console.log('crawlIdObj:'+crawlIdObj.value);
	
	//validation
	if(inputCpObj.value=='' && inputBrObj.value=='')
	{
		alert('Please enter a value for Company and Brand');
		return false;
	}	
		
	inputCpObj.disabled=true;
	inputBrObj.disabled=true;
	categoryObj.disabled=true;
	subcategoryObj.disabled=true;
	var today = lastUpdatedObj.innerText;
	
	//creating json object
	var crawlBean = {
		crawlId : crawlIdObj.value,
		companyName: inputCpObj.value,
		brandName: inputBrObj.value,
		category: categoryObj.value,
		categoryId:'',
		subcategory: subcategoryObj.value,
		subcategoryId:'',
		lastUpdatedDate:today
	};
	
	
	//call ajax call to controller.
	$.ajax({
	    url: "save",
	    method: 'POST',
	    contentType:'application/json',
	    data: JSON.stringify(crawlBean)
	}).done(function(data) {
	      
		$("#info").text(data);
	});
	
	//document.forms[0].submit();
}

function displayTime() {
   
    var currentTime = new Date();
    console.log('currentTime='+currentTime);
	var curr_day = currentTime.getDate();
	var curr_month = currentTime.getMonth();
	var curr_year = currentTime.getFullYear();
    
    var hours = currentTime.getHours();
    var minutes = currentTime.getMinutes();
    var seconds = currentTime.getSeconds();

	
	if (curr_month < 10) {
        curr_month = "0" + curr_month;
    }
	
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    var str =  curr_day + "/" + curr_month + "/" + curr_year + " " + hours + ":" + minutes + ":" + seconds + " ";
    if(hours > 11){
        str += "PM";
    } else {
        str += "AM";
    }
    return str;
}


function editRow(id) {
				
	
	var inputCpObj = document.getElementById("icp"+id);
	inputCpObj.disabled=false;
	inputCpObj.focus();
	
	var inputBrObj = document.getElementById("ibr"+id);
	inputBrObj.disabled=false;
	
	var categoryObj = document.getElementById("ca"+id);
	categoryObj.disabled=false;
	
	var subcategoryObj = document.getElementById('sca'+id);
	subcategoryObj.disabled=false;
	
	var saveRowButtonObj = document.getElementById('saveRowId'+id);
	saveRowButtonObj.disabled=false;
	
	//var crawlIdObj = document.getElementById("crawlId"+id);
			
}

function resetRow(id) {
	var cpName = 'Company Name';
	var brName = 'Brand Name';
	
	var cpObj = document.getElementById('cp'+id);
	var brObj = document.getElementById('br'+id);
			
	var inputCpObj = document.getElementById('icp'+id);
	inputCpObj.value = '';
	inputCpObj.style.display='none';
	var inputBrObj = document.getElementById('ibr'+id);
	inputBrObj.value = '';
	inputBrObj.style.display='none';
	
	var cpObj = document.getElementById('cp'+id);
	cpObj.innerText = 'Company Name';
	var brObj = document.getElementById('br'+id);
	brObj.innerText = 'Brand Name';
	
	var categoryObj = document.getElementById('ca'+id);
	categoryObj.value = '';
	
	var subcategoryObj = document.getElementById('sca'+id);
	subcategoryObj.value = '';
	
	var lastUpdatedObj = document.getElementById('lu'+id);
	lastUpdatedObj.innerText='';
}