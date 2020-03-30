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
    <title>Cars</title>
	<link href="../css/bootstrap-4.4.1.css" rel="stylesheet">
	<link href="../css/neumorph-full-dark.css" rel="stylesheet" type="text/css">
	<link href="../css/global.css" rel="stylesheet" type="text/css">
</head>
<body>
   <!--Navigation bar-->
		 <body class="navbar-dark">
		 	<!-- Attempt to resize logo. Should be something else. -->
  			<div class="col-xl-1"><a href = "/html/sitemap.html"><img src="../assets/logo.png" style = "width: 100%; height: 120%" alt="" class="navbar-brand" ></a></div>
			<div class="col-xl-6 offset-xl-6 container"><a href="/html/makes.html" class="np-element col np-hover order-0">Browse by Make</a><a href="/html/models.html" class="np-element col order-1 offset-1 np-hover">Browse by Model</a><a href="/html/dealers.html" class="np-element col order-2 offset-1 np-hover">Browse by Dealer</a></div>			    
			<script src="../js/jquery-3.4.1.min.js"></script>
			<script src="../js/popper.min.js"></script> 
		 	<script src="../js/bootstrap-4.4.1.js"></script>
		 	<br>
	 	 </body>
	<!--end of Navigation bar-->
	 
	<h1 class="offset-1 col-9 np-text-accent">Cars</h1>  
	<ul class="offset-2 car-grid" id="car-grid"></ul>	  
	<nav class="col-xl-4 offset-xl-2" style="align-content: center;" aria-label="Page navigation example">
    	<ul class="pagination" style="align-content: center;" id="pagination-wrapper"></ul>
  	</nav>
	  
	 
	<script>

		<%
			DBServlet db = new DBServlet();
			/* int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			if (pageNum == null) {
				pageNum = 1;
			} */
			
			int pageNum=1;
		%>

		var cars = <%=db.getJSCarsArray(pageNum)%>
		var tot_pages = <%=db.getCarsPgNumbers()%>
		




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
		
		window.location = window.location.href + '?pageNum=' + Number($(this).val());
		
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
	}) */
}  

function buildGrid() {
	var console = window.console
	console.log(cars[0])
	
 	var grid = $('#car-grid')
	
	//var data = pagination(state.querySet,state.page, state.items)
	
	var myGrid = <%=db.getJSCarsArray(pageNum)%>
	

		for(var i = 1 in myGrid){
			
			 if (i % 2 == 0){
				
				var listing = "<li class='card np-element np-hover col-4 model-card' style='margin-bottom: 70px; margin-right: 30px; height: 370px; float: left;' >"+
				"<a href='#'>"+
				"<h3 style='text-align: center;'>" + myGrid[i].name + "</h3>"
			
				
			} else {
				var listing = "<li class='card np-element np-hover col-4 model-card' style='margin-bottom: 70px;  height: 370px;' >"+
				"<a href='#'>"+
				"<h3 style='text-align: center;'>" + myGrid[i].name + "</h3>"
				
			}
			
			
			if(myGrid[i].img!=""){ listing = listing + "<div class='np-img-wrapper' width='50px' height='90px'>"+
				"<img class='np-img-expand' src='" + myGrid[i].img + "' width='200px' height='90px' style='margin: 10px'></div>"}
			
			if(myGrid[i].address!=""){ listing = listing + "<p><strong>Dealership:</strong> " + myGrid[i].dealership + "</p>"}
			if(myGrid[i].phoneNum!=""){ listing = listing + "<p><strong>Price:</strong> " + myGrid[i].price + "</p>"}
			if(myGrid[i].website!=""){ listing = listing + "<a href='" + myGrid[i].url + "'><strong>Check Out Dealership Listing</strong></a>"}
			listing = listing + "</a> </li>"
							
			
			grid.append(listing)
		}
	
	pageButtons(tot_pages)
}
</script>

<script src="../js/jquery-3.4.1.min.js"></script>
	 
	<script src="../js/popper.min.js"></script> 
	<script src="../js/bootstrap-4.4.1.js"></script>
  </body>
</html>