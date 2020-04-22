<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.*"%>
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

   <!--NEW Navigation bar-->
		 <body class="navbar-dark">
			<div class="col-xl-1"><a href = "/html/home.html"><img src="../assets/logo.png" style = "width: 100%; height: 120%" alt="" class="navbar-brand" ></a></div>
			<div class="col-xl-6 offset-xl-6 container">
				<a href="/html/gitabout.jsp" class="np-element col order-0 np-hover">About</a>
				<a href="/html/makes.jsp?page=1" class="np-element col order-1 offset-1 np-hover">Browse by Make</a>
				<a href="/html/cars.jsp?page=1" class="np-element col order-2 offset-1 np-hover">Browse by Car</a>
				<a href="/html/dealers.jsp?page=1" class="np-element col order-3 offset-1 np-hover">Browse by Dealer</a>
		
				
			</div>			  
			<script src="../js/jquery-3.4.1.min.js"></script>
			<script src="../js/popper.min.js"></script> 
		 	<script src="../js/bootstrap-4.4.1.js"></script>
		 	<br>
	 	 </body>
	<!--end of NEW Navigation bar-->
	
  <%   
  
  	String param_pages = "";
  
  	int pageNum=1;
  	if (request.getParameter("page") != null) {
	    pageNum = Integer.parseInt(request. getParameter("page"));
	}
  	
  	DBServlet db = new DBServlet();
	List<String> cars = db.carVins;
	List<String> pageCars = new ArrayList<>();
	
	String sort_type = null;
 	if (request.getParameter("sort") != null ) {
 		param_pages += "&sort=";
 		
	    sort_type  = request.getParameter("sort");
	 	if (sort_type.equals("year_lowhigh")){
	 		cars = db.sortCarYearsLowHigh();
	 		param_pages += "year_lowhigh";
	 	} 
	 	
	 	if (sort_type.equals("year_highlow")) {
	 		cars = db.sortCarYearsHighLow();
	 		param_pages += "year_highlow";
	 	}
	    
	}
 	

 	if (request.getParameter("filter") != null) {
 		param_pages += "&filter=";
	    String filter_type  = request.getParameter("filter");
	    char filter_value = filter_type.charAt(0);
	    param_pages += Character.toString(filter_value);
	    cars = db.filterCarYears(filter_value);
	}
 	
 	if (request.getParameter("search") != null) {
 		param_pages += "&search=";
	    String search_term  = request.getParameter("search");
	    param_pages += search_term;
	    String replaced_searchterm = search_term.replace("_", " ");
	   	cars = db.carSearch(replaced_searchterm); 
	}
 	
 	

	
	int totalPgs = (int)Math.ceil(cars.size()/10)+1;
	int windowStart = (pageNum-1)*10;
	int windowEnd = Math.min((windowStart+10),cars.size());	
	
	for(int i = windowStart; i < windowEnd; i++){
		pageCars.add(cars.get(i));
	}
  %>
  
  
      <h1 class="offset-1 col-9 np-text-accent">Cars</h1>
       <body class="navbar-dark">
       <br>
			<div class="" style = 'text-align: left'>
					
				<a class="np-element col order-0 offset-1">Sort by year: </a>
				<a href="/html/cars.jsp?page=1&amp;sort=year_lowhigh" class="np-element col order-1 offset-0 np-hover">Low - High</a>
				<a href="/html/cars.jsp?page=1&amp;sort=year_lowhigh" class="np-element col order-1 offset-0 np-hover">High - Low</a>
				
				<a class="np-element col order-2 offset-1">Filter by year: </a>
				<a href="/html/cars.jsp?page=1&amp;filter=H" class="np-element col order-2 offset-0 np-hover">2017</a>
				<a href="/html/cars.jsp?page=1&amp;filter=J" class="np-element col order-2 offset-0 np-hover">2018</a>
				<a href="/html/cars.jsp?page=1&amp;filter=K" class="np-element col order-2 offset-0 np-hover">2019</a>
				<a href="/html/cars.jsp?page=1&amp;filter=L" class="np-element col order-2 offset-0 np-hover">2020</a>
				<a href="/html/cars.jsp?page=1&amp;filter=M" class="np-element col order-2 offset-0 np-hover">2021</a>
				<a class="col order-3 offset-1"> 
					<input type="text" class="np-element offset-0" id = "user_search_input" >
					<button class="np-element offset-0 np-hover">Search</button>
				</a>
				
			</div>			  
			<script src="../js/jquery-3.4.1.min.js"></script>
			<script src="../js/popper.min.js"></script> 
		 	<script src="../js/bootstrap-4.4.1.js"></script>
		 	<br>
	 	 </body>
      
      
      
      
	  <ul class="offset-2 car-grid" id="car-grid"> 
		  <% 
		  
		  for (int i = 0; i < pageCars.size(); i++) {
			String listing = "";
			  
			if (i%2 == 0) {
				listing = "<li class='card np-element np-hover col-4 model-card' style='margin-bottom: 70px; margin-right: 30px; height: 370px; float: left;' >"+
						"<a href='/html/car.jsp?vin=" + pageCars.get(i).toString() + "'>" +
						"<h3 style='text-align: center;'>" + db.getCarAttribute(pageCars.get(i), "name").toString() + "</h3>";
			} else {
				listing = "<li class='card np-element np-hover col-4 model-card' style='margin-bottom: 70px;  height: 370px;' >"+
						"<a href='/html/car.jsp?vin=" + pageCars.get(i).toString() + "'>" +
						"<h3 style='text-align: center;'>" + db.getCarAttribute(pageCars.get(i), "name").toString() + "</h3>";
			}
			
			if(db.getCarAttribute(pageCars.get(i), "img") != ""){
				listing += "<div class='np-img-wrapper' width='50px' height='90px'>"+
						"<img class='np-img-expand' src='" + db.getCarAttribute(pageCars.get(i), "img").toString() + "' width='200px' height='90px' style='margin: 10px'></div>";
			}
			if(db.getCarAttribute(pageCars.get(i), "dealership") != ""){
				listing += "<p><strong>Dealership:</strong> " + db.getCarAttribute(pageCars.get(i), "dealership").toString() + "</p>";
			}
			if(db.getCarAttribute(pageCars.get(i), "price") != null){
				if (!db.getCarAttribute(pageCars.get(i), "price").toString().equals("")){
					listing += "<p><strong>Price: $</strong>" + db.getCarAttribute(pageCars.get(i), "price").toString() + "</p>";
				} else {
					listing += "<p><strong>No Price Listed</strong>" + db.getCarAttribute(pageCars.get(i), "price").toString() + "</p>";
				}
			} else {
				listing += "<p><strong>No Price Listed</strong></p>";
			}
			
			if(db.getCarAttribute(pageCars.get(i), "url") != ""){
				listing += "<a href='" + db.getCarAttribute(pageCars.get(i), "url").toString() + "'><strong>Check Out Dealership Listing</strong></a></a> </li>";
			}
			  
			out.print(listing);
			  
		  }
		  
		  
		  %>
		  
	  </ul>

	  <nav class="col-xl-4 offset-xl-2" style="align-content: center;" aria-label="Page navigation example">
      <ul class="pagination" style="align-content: center;" id="pagination-wrapper"></ul>
      </nav>
	
	
	<script>
	
	pageButtons()

	function pageButtons(){
		
		var winStart = (<%=pageNum%> - Math.floor(5/2));
		var winEnd = (<%=pageNum%> + Math.floor(5/2));
		
		if(winStart < 1){
			winStart=1
			winEnd=5
		}
		
		if(winEnd > <%=totalPgs%>){
			winStart = <%=totalPgs%> - (4)
			
			if(winStart < 1){
				winStart=1
			}
			winEnd=<%=totalPgs%>
		}
		
		var wrapper = document.getElementById('pagination-wrapper')
		wrapper.innerHTML = ''
		
			if(<%=pageNum%>>1){
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element np-hover" style="margin:5px;" href="cars.jsp?page=1<%=param_pages%>" aria-label="First"> <span aria-hidden="true">&laquo;</span> <span class="sr-only">First</span> </a> </li>`
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element np-hover" style="margin:5px;" href="cars.jsp?page=<%=pageNum-1%><%=param_pages%>" aria-label="Previous"> <span aria-hidden="true">&lt;</span> <span class="sr-only">Previous</span> </a> </li>`
			} else {
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element" style="margin:5px;" href="cars.jsp?page=1<%=param_pages%>" aria-label="First" disabled> <span aria-hidden="true">&laquo;</span> <span class="sr-only">First</span> </a> </li>`
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element" style="margin:5px;" href="cars.jsp?page=<%=totalPgs%><%=param_pages%>" aria-label="Previous" disabled> <span aria-hidden="true">&lt;</span> <span class="sr-only">Previous</span> </a> </li>`
			}
		
		for(var page = winStart; page <= winEnd ; page++){
			if(page==<%=pageNum%>){
				var temp = `<li><a class="np-element np-shadow-inverse np-hover-inverse" href="cars.jsp?page=`
						temp+=page
						temp+= "<%=param_pages%>"
						temp+=`" style="margin:5px;">` + page + `</a></li>`
				wrapper.innerHTML +=  temp
				} else {
				var temp = `<li><a class="np-element np-hover-" href="cars.jsp?page=`
					temp+=page
					temp += "<%=param_pages%>"
					temp+=`" style="margin:5px;">` + page + `</a></li>`
					wrapper.innerHTML +=  temp
			}
		}
		
			if(<%=pageNum%> < <%=totalPgs%>){
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element np-hover" style="margin:5px;" href="cars.jsp?page=<%=pageNum+1%><%=param_pages%>" aria-label="Next"> <span aria-hidden="true">&gt;</span> <span class="sr-only">Next</span> </a> </li>`
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element np-hover" style="margin:5px;" href="cars.jsp?page=<%=totalPgs%><%=param_pages%>" aria-label="last"> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Last</span> </a> </li>`
			} else {
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element" style="margin:5px;" href="cars.jsp?page=1<%=param_pages%>" aria-label="Next"> <span aria-hidden="true">&gt;</span> <span class="sr-only">Next</span> </a> </li>`
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element" style="margin:5px;" href="cars.jsp?page=<%=totalPgs%><%=param_pages%>" aria-label="Last" disabled> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Last</span> </a> </li>`
			}
	}
	
	</script>

<script src="../js/jquery-3.4.1.min.js"></script>
	 
	<script src="../js/popper.min.js"></script> 
	<script src="../js/bootstrap-4.4.1.js"></script>
  </body>
  <footer>
  		<br>
		<div class="np-divider-fat"></div>
		<p style="text-align: center;">Copyright © 2020 · All Rights Reserved · The Drag</p>
		<p style="text-align: center;"><a href="/html/home.html">Home</a> · <a href="/html/gitabout.jsp">About</a> · <a href="/html/makes.jsp?page=1">Makes</a> · <a href="/html/cars.jsp?page=1">Cars</a> · <a href="/html/dealers.jsp?page=1">Dealers</a></p>
	</footer>
</html>