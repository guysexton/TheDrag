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
  	String dealer="ERROR";
  	if (request.getParameter("dealership") != null) {
	    dealer = request.getParameter("dealership").toString();
	}
  	String[] dealerName=dealer.split("~");
  	
  	DBServlet db = new DBServlet();
  	dealer = dealer.split("~")[0].replace('_',' ').replace('$','&');
	
  %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%=dealer%></title> <!-- TODO dealership name var-->

    <!-- Bootstrap -->
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

   		<div class="container">
  		 <h1 class="modal-header" id="dealer-name"><%=dealer%></h1>
  		 </div>
	  
  <div class="row" style="padding:30px;">
		  <div class="offset-xl-1 col-xl-7">
			  <h1 class="text-center">Cars Available</h1>
			  <ul class="offset-2 car-grid offset-xl-0" id="car-grid">
				  <!-- <li class="card np-element np-hover col-2 car-card" style="margin: 20px;"><a href="#">TEMPVIN</a></li> -->
				  
				  <%
				  	ArrayList<String> cars = (ArrayList<String>)db.getDealershipAttribute(dealer,"cars");
				  
				  	for(String c:cars){
				  		out.print("<li class='card np-element np-hover col-2 car-card' style='margin: 20px;'><a href='/html/car.jsp?vin=" + c + "'>" + db.getCarAttribute(c,"name") + "</a></li>");
			  		}

				  %>
				  
			  </ul>
		    
			  
          </div>
		  	<div class="np-element np-colorize offset-xl-1 col-xl-3">
				<div class="np-img-wrapper" width="100px" height="100px" id="dealer-img"><%out.print("<img class='np-img-expand' src='" + db.getDealershipAttribute(dealer,"img") + "' width='inherit' height='inherit' style='margin: 10px'>");%></div>
		  	  <h1 class="text-center">Dealership Information</h1>
				<div class="np-divider"></div>
				<p><span><b>Address: </b><span id="address"><%out.print(db.getDealershipAttribute(dealer,"address"));%></span></span></p>
				<p><span><b>Phone: </b><span id="phone"><%out.print(db.getDealershipAttribute(dealer,"phoneNum"));%></span></span></p>
			  <% 
			  String website = (String) db.getDealershipAttribute(dealer,"website");
			  if(!website.equals(""))
			  	out.print("<p><a id='link' href="+ website +"><strong>Click here to visit dealership website</strong></a></p>");
			  
			  
			  ArrayList<String> makes = (ArrayList<String>)db.getDealershipAttribute(dealer,"makes");
			  
			  if(makes.size()>0){
			  out.print("<a href='/html/makes.jsp?make-instance=" + makes.get(0) + "~' id='dealer-brand-card'>"+"<div class='card np-element np-hover'>" + "<div class='card-body text-center'>" + "<h5 class='card-title' id='dealer-brand'>This is a " +  makes.get(0) + " Dealership</h5>" + "<p class='card-text' id='dealer-brand-paragraph'>Click here to learn more about " + makes.get(0) + ".</p>" + "</div></div></a>" );}%>
			  </div>
</div>


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