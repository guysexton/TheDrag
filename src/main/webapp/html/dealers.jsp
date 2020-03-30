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
	 
	<%  
	String spageid=request.getParameter("page"); 
	int pageLimit=Integer.parseInt(spageid); 
	int pageid=Integer.parseInt(spageid);  
	int total=10;  
	if(pageid==1){}  
	else{  
	    pageid=pageid-1;  
	    pageid=pageid*total+1;  
	}  
	DBServlet db = new DBServlet();
	List<String> dealers = db.dealerNames; 
	List<String> pageDealers = new ArrayList<>();
	for(int i = pageid-1; i < total*pageLimit; i++){
		pageDealers.add(dealers.get(i));
	}
	  
	out.print("<h1>Page No: "+spageid+"</h1>");   
	for(String s: pageDealers){  
	    out.print("<tr><td>"+ db.getDealershipAttribute(s, "name"));  
	}  
	out.print("</table>");  
	%>  
<a href="dealers.jsp?page=1">1</a>  
<a href="dealers.jsp?page=2">2</a>  
<a href="dealers.jsp?page=3">3</a>  

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="../js/jquery-3.4.1.min.js"></script>
	 
<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../js/popper.min.js"></script> 
	<script src="../js/bootstrap-4.4.1.js"></script>
  </body>
</html>