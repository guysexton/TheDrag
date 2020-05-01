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
<%@ page import="thedrag.CarServlet"%>
<%@ page import="thedrag.ServletFactory"%>
<%@ page import="thedrag.NHTSACarServlet"%>

  <% 
  	String current_vin = "KEK";
  	if (request.getParameter("vin") != null) {
  		current_vin = request.getParameter("vin").toString();
  	}
  	
  	ServletFactory servletFactory = new ServletFactory();
  	DBServlet db = servletFactory.getServlet("cars");
  	NHTSACarServlet ns = new NHTSACarServlet(current_vin);
	List<String> cars = db.carVins;
	
	String name = "";
	String img = "";
	String url = "";
	String dealership = "";
	String make = "";
	String price = "";
	String mpg = ""; 
	
	//NHSTA attributes
	String bodyclass = "";
	String hp = "";

	for(String s : cars) {
		if (s.equals(current_vin)) {
			
			if (!db.getAttribute(current_vin, "name").toString().equals("")){
				name = db.getAttribute(current_vin, "name").toString();
			} else {
				name = "N/A";
			}
			
			if (!db.getAttribute(current_vin, "img").toString().equals("")){
				img = db.getAttribute(current_vin, "img").toString();
			} else {
				img = "N/A";
			}
			
			if (!db.getAttribute(current_vin, "url").toString().equals("")){
				url = db.getAttribute(current_vin, "url").toString();
			} else {
				url = "N/A";
			}
			
			if (!db.getAttribute(current_vin, "dealership").toString().equals("")){
				dealership = db.getAttribute(current_vin, "dealership").toString();
			} else {
				dealership = "N/A";
			}
			
			if (db.getAttribute(current_vin, "make") != null){
				make = db.getAttribute(current_vin, "make").toString();
			} else {
				make = "N/A";
			}
			
			if (db.getAttribute(current_vin, "price") != null){
				price = db.getAttribute(current_vin, "price").toString();
			} else {
				price = "N/A";
			}
			
			if (db.getAttribute(current_vin, "mpg") != null){
				mpg = db.getAttribute(current_vin, "mpg").toString();
			} else {
				mpg = "N/A";
			}
			
			if (!ns.getNHTSAAttribute("BodyClass").equals("")){
				bodyclass = ns.getNHTSAAttribute("BodyClass");
			} else {
				bodyclass = "N/A";
			}
			
			if (!ns.getNHTSAAttribute("EngineHP").toString().equals("")){
				hp = ns.getNHTSAAttribute("EngineHP");
			} else {
				hp = "N/A";
			}
			
			break;
		}
	}
	
  %>

<!DOCTYPE html>
<html lang="en">	
<head>

    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%=name%></title>
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

		  	String slug = dealership.replace('&','$').replace(' ','_').replace("'",".")+"~";
		  	
		  
		  	String listing = "<body><div><ul class=\"list-inline offset-xl-2 col-xl-8\"><li class=\"list-inline-item\"><h2 id=\"CarName\">" + name +"</h2></li></ul></div>";
		 	listing += "<div class=\"np-img-wrapper col-10 offset-1 \" style = \"width: 100%; height: 50%;\" ><div class=\"card np-element np-hover\"><img class=\"np-img-expand\" src=" + img + " alt=\"\"></div></div>";
		  	listing += "<br><div class=\"row\"><div class=\"offset-xl-1 col-xl-4\"> <a href=/html/make-instance.jsp?make=" + make + "><div class=\"card np-element np-hover\"><div class=\"card-body text-center\">";
		  	listing += "<h5 class=\"card-title\">Manufactured by " + make + "</h5><p class=\"card-text\">Click here to learn more about " + make + "</p></div></div></a><br>";
		  	listing += "<div class=\"card np-element\"><div class=\"card-body\"><h5 class=\"card-title\">Price</h5><h1 class=\"card-text\">$" + price + "</h1></div></div><br>";
		  	listing += "<div class=\"card np-element\"><div class=\"card-body\"><h5 class=\"card-title\">Body Class</h5><h1 class=\"card-text\">" + bodyclass + "</h1></div></div><br>";
		  	listing += "<div class=\"card np-element\"><div class=\"card-body\"><h5 class=\"card-title\">MPG</h5><h3 class=\"card-text\">" + mpg + "</h3></div></div></div>";
		  	
		  	listing += "<div class=\"col-xl-4 offset-xl-2\"><a href=\"/html/view-dealer.jsp?dealership=" + slug +"\"><div class=\"card np-element np-hover\"><div class=\"card-body text-center\"><h5 class=\"card-title\">Sold by " + dealership +"</h5><p class=\"card-text\">Click here find more cars from " + dealership + "</p></div></div></a><br>";
		 	listing += "<a href="+ url + "><div class=\"card np-element np-hover\"><div class=\"card-body text-center\"><h5 class=\"card-title\">Listing</h5><p class=\"card-text\">Click here to check out listing</p></div></div></a><br>";
		 	listing += "<div class=\"card np-element\"><div class=\"card-body\"><h5 class=\"card-title\">Horsepower</h5><h1 class=\"card-text\">" + hp + "</h1></div></div><br>";
		 	listing += "<div class=\"card np-element\"><div class=\"card-body\"><h5 class=\"card-title\">VIN</h5><h3 class=\"card-text\">" + current_vin + "</h3></div></div></div></div><br><br>";
		 	
			out.print(listing);

		  %>
		  
	
	

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