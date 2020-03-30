<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.*"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ page import="com.mongodb.client.FindIterable"%>
<%@ page import="com.mongodb.client.MongoDatabase"%>
<%@ page import="com.mongodb.client.MongoCollection"%>
<%@ page import="com.mongodb.MongoClient"%>
<%@ page import="com.mongodb.BasicDBObject"%>
<%@ page import="com.mongodb.MongoClientURI"%>
<%@ page import="com.mongodb.ServerAddress"%>
<%@ page import="com.mongodb.MongoCredential"%>
<%@ page import="com.mongodb.MongoClientOptions"%>

<%@ page import="thedrag.DBServlet"%>


<!DOCTYPE html>
<html lang="en">	
<head>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dealerships</title>
    <!-- Bootstrap -->
	<link href="../css/bootstrap-4.4.1.css" rel="stylesheet">
	<link href="../css/neumorph-full-dark.css" rel="stylesheet" type="text/css">
	<link href="../css/global.css" rel="stylesheet" type="text/css">
  </head>
  <body>
    	<!-- body code goes here -->

   <!--Navigation bar-->
		 <body class="navbar-dark">
		 
		 	<!-- Attempt to resize logo. Should be something else. -->
  <div class="col-xl-1"><a href = "/html/sitemap.html"><img src="../assets/logo.png" style = "width: 100%; height: 120%" alt="" class="navbar-brand" ></a></div>
			<div class="col-xl-6 offset-xl-6 container"><a href="/html/makes.html" class="np-element col np-hover order-0">Browse by Make</a><a href="/html/models.html" class="np-element col order-1 offset-1 np-hover">Browse by Model</a><a href="/html/dealers.html" class="np-element col order-2 offset-1 np-hover">Browse by Dealer</a></div>			    
	
			<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
			<script src="../js/jquery-3.4.1.min.js"></script>
		
			<!-- Include all compiled plugins (below), or include individual files as needed -->
			<script src="../js/popper.min.js"></script> 
		 	<script src="../js/bootstrap-4.4.1.js"></script>
		 	<br>
	 	 </body>
	<!--end of Navigation bar-->
	  
	  
		<h1 class="offset-1 col-9 np-text-accent">Dealers</h1>
	  
	  <ul class="offset-2 dealer-grid" id="dealer-grid">
		  
		  <!-- <li class="card np-element np-hover col-4 dealer-card" style="margin: 20px;"><a href="#">
			  <h3 style="text-align: center;">Dealer</h3>
			  <div class="np-img-wrapper" width="50px" height="50px"><img class="np-img-expand" src="https://pictures.dealer.com/p/penskeroundrocktoyota/0040/6f8b9746ac8eb3968fc541d0834b047cx.jpg" width="inherit" height="inherit" style="margin: 10px"></div>
			  <p><strong>Address:</strong> #### Street St, Austin, TX 78705</p>
			  <p><strong>Phone:</strong> (###) ###-####</p>
			  <p><strong>Website:</strong><a href="#"> www.address.com</a></p>
			  </a>
		  </li> -->
	  </ul>	  
	  
	  <nav class="col-xl-4 offset-xl-2" style="align-content: center;" aria-label="Page navigation example">
    <!-- Add class .pagination-lg for larger blocks or .pagination-sm for smaller blocks-->
    <ul class="pagination" style="align-content: center;" id="pagination-wrapper">
    </ul>
  </nav>
	  
	  
<script>


<%
	DBServlet db = new DBServlet();
	
	int pageNum;

	if (request.getParameter("pg") == null) {
	    pageNum=1;
	} else {
	    pageNum = Integer.parseInt(request. getParameter("pg"));
	}

	
%>

var dealers = <%=db.getJSDealerArray(pageNum)%>
var tot_pages = <%=db.getDealerPgNumbers()%>


testFunc()

function testFunc(){
	var MongoClient = require('mongodb').MongoClient;
	var url = "mongodb+srv://jdwalsh21:BI6SfPDyhGX8ihAU@thedragapiscrapes-2duen.gcp.mongodb.net/test?authSource=admin&replicaSet=TheDragAPIScrapes-shard-0&readPreference=primary&appname=MongoDB%20Compass&ssl=true"


	MongoClient.connect(url, function(err, db) {
	  if (err) throw err;
	  var dbo = db.db("mydb");
	  dbo.collection("dealerships").findOne({}, function(err, result) {
	    if (err) throw err;
	    console.log(result.name);
	    db.close();
	  });
	});
}




buildGrid()

/*     function pagination(querySet,page,items) {
	var trimStart = (page - 1) * items
	var trimEnd = trimStart + items
	
	var trimmedData = Array.from(querySet).slice(trimStart, trimEnd)
	
	var pages = tot_pages
	
	return{
		'querySet':trimmedData,
		'pages':pages
	} 
} */

function pageButtons(pages){
	
	var winStart = (<%=pageNum%> - Math.floor(5/2));
	var winEnd = (<%=pageNum%> + Math.floor(5/2));
	
	if(winStart < 1){
		winStart=1
		winEnd=5
	}
	
	if(winEnd > tot_pages){
		winStart = tot_pages - (5 - 1)
		
		if(winStart < 1){
			winStart=1
		}
		winEnd=pages
	}
	
	var wrapper = document.getElementById('pagination-wrapper')
	wrapper.innerHTML = ''
	
		if(<%=pageNum%>>1){
		wrapper.innerHTML += `<li class="page-item"> <button class="first np-element np-hover" style="margin:5px;" href="#" aria-label="Previous"> <span aria-hidden="true">&laquo;</span> <span class="sr-only">First</span> </button> </li>`}
		else{
			wrapper.innerHTML += `<li class="page-item"> <button class="first np-element" style="margin:5px;" href="#" aria-label="Previous" disabled> <span aria-hidden="true">&laquo;</span> <span class="sr-only">First</span> </button> </li>`
		}
	
	wrapper.innerHTML += `<li class="page-item"> <button class="prev np-element np-hover" style="margin:5px;" href="#" aria-label="Previous"> <span aria-hidden="true">&lt;</span> <span class="sr-only">Previous</span> </button> </li>`
	
	for(var page = winStart; page <= winEnd ; page++){
		if(page==<%=pageNum%>)
			wrapper.innerHTML += `<li><button value=${page} class="np-element np-shadow-inverse np-hover-inverse" style="margin:5px;">${page}</button></li>`
		else
			wrapper.innerHTML += `<li><button value=${page} class="page np-element np-hover" style="margin:5px;">${page}</button></li>`
	}
	
		wrapper.innerHTML += `<li class="page-item"> <button class="next np-element np-hover" style="margin:5px;" href="#" aria-label="Next"> <span aria-hidden="true">&gt;</span> <span class="sr-only">Next</span> </button> </li>`
	
		if(<%=pageNum%> < tot_pages){
		wrapper.innerHTML += `<li class="page-item"> <button class="last np-element np-hover" style="margin:5px;" href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Last</span> </button> </li>`}
		else{
			wrapper.innerHTML += `<li class="page-item"> <button class="last np-element" style="margin:5px;" href="#" aria-label="Next" disabled> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Last</span> </button> </li>`
		}
	
	$('.page').on('click',function(){
		
		// Get the current page
		var curr_page = window.location.href,
		    next_page = "";

		// If current page has a query string, append action to the end of the query string, else
		// create our query string
		if(curr_page.indexOf("?") > -1) {
		    next_page = curr_page.split("?")[0] + "?pg=" + $(this).val();
		} else {
		    next_page = curr_page+ "?pg=" + $(this).val();
		}

		// Redirect to next page
		window.location = next_page;
		
		/* document.getElementById('dealer-grid').innerHTML=''
		
		state.page = Number($(this).val())
		
		buildGrid() */
	})
	
	 /* $('.prev').on('click',function(){
		document.getElementById('dealer-grid').innerHTML=''
		
		if(state.page>1){state.page = state.page-1}
		else{state.page=pages}
		
		buildGrid()
	})
	
	$('.next').on('click',function(){
		document.getElementById('dealer-grid').innerHTML=''
		
		if(state.page<pages){state.page = state.page+1}
		else{state.page=1}
		
		buildGrid()
	})
	
	$('.first').on('click',function(){
		document.getElementById('dealer-grid').innerHTML=''
		
		state.page=1
		
		buildGrid()
	})
	
	$('.last').on('click',function(){
		document.getElementById('dealer-grid').innerHTML=''
		
		state.page=pages
		
		buildGrid()
	})  */
}  

function buildGrid() {
	var console = window.console
	console.log(dealers[0])
	
 	var grid = $('#dealer-grid')
	
	//var data = pagination(state.querySet,state.page, state.items)
	
	var myGrid = <%=db.getJSDealerArray(pageNum)%>
	
	for(var i in myGrid){
		
				
		 var listing = "<li class='card np-element np-hover col-4 dealer-card' style='margin: 20px;height:275px;' >"+
						"<a href='/view-dealer?dealership=" + myGrid[i].name + ">"+
						"<h3 style='text-align: center;'>" + myGrid[i].name + "</h3>"
						
		if(myGrid[i].img!=""){ listing = listing + "<div class='np-img-wrapper' width='50px' height='50px'>"+
							"<img class='np-img-expand' src='" + myGrid[i].img + "' width='inherit' height='inherit' style='margin: 10px'></div>"}
		if(myGrid[i].address!=""){ listing = listing + "<p><strong>Address:</strong> " + myGrid[i].address + "</p>"}
		if(myGrid[i].phoneNum!=""){ listing = listing + "<p><strong>Phone:</strong> " + myGrid[i].phoneNum + "</p>"}
		if(myGrid[i].website!=""){ listing = listing + "<a href='" + myGrid[i].website + "'><strong>Visit Dealer Website</strong></a>"}
		listing = listing + "</a> </li>" 
						
		
		grid.append(listing) 
		}
	
	pageButtons(tot_pages)
}
</script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="../js/jquery-3.4.1.min.js"></script>
	 
<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/popper.min.js"></script> 
	<script src="../js/bootstrap-4.4.1.js"></script>
  </body>
</html>