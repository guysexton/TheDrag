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

<% 
  	String make="ERROR";
  	if (request.getParameter("make") != null) {
	    make = request.getParameter("make").toString();
	}
  	String[] makeName=make.split("~");
  	
  	DBServlet db = new DBServlet();
  	make = make.split("~")[0].replace('_',' ');
	
  %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%=make%></title> <!-- TODO dealership name var-->

    <!-- Bootstrap -->
    <link href="../css/bootstrap-4.4.1.css" rel="stylesheet">
    <link href="../css/neumorph-full-dark.css" rel="stylesheet" type="text/css">
    <link href="../css/global.css" rel="stylesheet" type="text/css">
  </head>
  
  
  
  <body>
  <!--Navigation bar-->
		 <header class="navbar-dark">
		 
		 	<!-- Attempt to resize logo. Should be something else. -->
  <div class="col-xl-1"><a href = "/html/home.html"><img src="../assets/logo.png" style = "width: 100%; height: 120%" alt="" class="navbar-brand" ></a></div>
			<div class="col-xl-6 offset-xl-6 container"><a href="/html/makes.jsp?page=1" class="np-element col np-hover order-0">Browse by Make</a><a href="/html/models.jsp?page=1" class="np-element col order-1 offset-1 np-hover">Browse by Model</a><a href="/html/dealers.jsp?page=1" class="np-element col order-2 offset-1 np-hover">Browse by Dealer</a></div>			  
	 	 </header>
	<!--end of Navigation bar-->
	 	  
	  
   		<div class="container">
  		 <h1 class="modal-header" id="make-name"><%=make%></h1>
  		 </div>
	  
  <div class="row" style="padding:30px;">
		  <div class="offset-xl-1 col-xl-7">
			  <h1 class="text-center">Cars Available</h1>
			  <ul class="offset-2 car-grid offset-xl-0" id="car-grid">
				  <!-- <li class="card np-element np-hover col-2 car-card" style="margin: 20px;"><a href="#">TEMPVIN</a></li> -->
			  </ul>
		    
			  
          </div>
		  	<div class="np-element np-colorize offset-xl-1 col-xl-3">
				<div class="np-img-wrapper" width="100px" height="100px" id="make-img"></div>
		  	  <h1 class="text-center">Make Information</h1>
				<div class="np-divider"></div>
				<p><span><b>Number of Cars: </b><span id="numCars"></span></span></p>
				<p><span><b>Number of Dealerships: </b><span id="numDealerships"></span></span></p>
			  
			  <a href="#" id="make-brand-card">
			  <div class="card np-element np-hover">
			    <div class="card-body text-center">
			      <h5 class="card-title" id="make-brand"></h5>
			      <p class="card-text" id="make-brand-paragraph"></p>
		        </div>
		      </div>
		    </a>            </div>
</div>

	  <script>
	  
	  render()

	  function render(){
	  	document.getElementById('make-name').innerHTML = <%=make%>
	  	
	  	document.getElementById('make-img').innerHTML = "<img class='np-img-expand' src='" + db.getMakeAttribute(<%=make%>,"img") + "' width='inherit' height='inherit' style='margin: 10px'>"
	  	
	  	document.getElementById('numCars').innerHTML = db.getMakeAttribute(<%=make%>,"numCars")
	  	
	  	document.getElementById('numDealerships').innerHTML = db.getMakeAttribute(<%=make%>,"numDealerships")
	  	
	  	
	  	document.getElementById('make-brand-card').href = "#"
	  	document.getElementById('make-brand').innerHTML = "This is a " +  db.getMakeAttribute(<%=make%>,"cars")[0] + " Dealership"
	  	document.getElementById('make-brand-paragraph').innerHTML = "Click here to learn more about " + db.getDealershipAttribute(<%=dealer%>,"makes")[0]s + "."
	  	
	  	 
	  	var grid = $('#car-grid')
	  	
	  	var myGrid = make.cars

	  	for(var i in myGrid){
	  		var listing = `<li class="card np-element np-hover col-2 car-card" style="margin: 20px;"><a href="#">` + myGrid[i] + `</a></li>`
	  		
	  		grid.append(listing)
	  	}
	  }
	  
	  </script>
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
    <script src="../js/jquery-3.4.1.min.js"></script>

    <!-- Include all compiled plugins (below), or include individual files as needed --> 
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