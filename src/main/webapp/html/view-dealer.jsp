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
  	dealer = dealer.split("~")[0].replace('_',' ').replace('$','&').replace(".","'");
	
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
		  	<p><span id="about"><%if(db.getDealershipAttribute(dealer,"about") != null)out.print(db.getDealershipAttribute(dealer,"about"));%></span></p>
		  	<br>
		  	<% 
		  		String address = (String)db.getDealershipAttribute(dealer,"address");
		  		if(!address.equals(""))
		  			out.print("<iframe width='100%' height='600' frameborder='0' style='border:0' src='https://www.google.com/maps/embed/v1/place?key=AIzaSyDH2SH8bI5WAjlplxVYOx7LK-Gr3MrjeK4&q=" + address.replace(" ","+") + "' allowfullscreen></iframe>");
		  	%>
		  	<br>
			  <h1 class="text-center">Cars Available</h1>
			  <ul class="offset-2 car-grid offset-xl-0" id="car-grid">
				  <!-- <li class="card np-element np-hover col-2 car-card" style="margin: 20px;"><a href="#">TEMPVIN</a></li> -->
				  
				  <%
				  	ArrayList<String> cars = (ArrayList<String>)db.getDealershipAttribute(dealer,"cars");
				  
				  	for(String c:cars){
				  		out.print("<li class='card np-element np-hover col-2 car-card' style='margin: 20px;'><a href='/html/car.jsp?vin=" + c + "'><div style='width:100%;height:100%;'>" + db.getCarAttribute(c,"name") + "</div></a></li>");
			  		}

				  %>
				  
			  </ul>
		    
			  
          </div>
		  	<div class="np-element np-colorize offset-xl-1 col-xl-3">
				<div class="np-img-wrapper" width="100px" height="100px" id="dealer-img"><%out.print("<img class='np-img-expand' src='" + db.getDealershipAttribute(dealer,"img") + "' width='inherit' height='inherit' style='margin: 10px'>");%></div>
		  	  <h1 class="text-center">Dealership Information</h1>
				<div class="np-divider"></div>
				
				<%
					String addr = (String)db.getDealershipAttribute(dealer,"address");
					
					String phone = (String)db.getDealershipAttribute(dealer,"phoneNum");
					if(phone.equals(" "))
						phone = " N/A ";
					
					String hrs = (String)db.getDealershipAttribute(dealer,"hours");
					if(hrs.equals(""))
						hrs=" N/A ";
				%>
				
				<p><span><b>Address: </b><span id="address"><%=addr%></span></span></p>
				<p><span><b>Phone: </b><span id="phone"><%=phone%></span></span></p>
				<p><span><b>Hours: </b><span id="hours"><%=hrs%></span></span></p>
			  <% 
			  String website = (String) db.getDealershipAttribute(dealer,"website");
			  if(!website.equals(""))
			  	out.print("<p><a id='link' href="+ website +" class='hov'><strong>Click here to visit dealership website</strong></a></p>");
			  
			  
			  ArrayList<String> makes = (ArrayList<String>)db.getDealershipAttribute(dealer,"makes");
			  int makeSize = makes.size();
			  
			  if(makeSize>0){
			  out.print("<a href='/html/make-instance.jsp?make=" + makes.get(0).replace('&','$').replace(' ','_') + "~' id='dealer-brand-card'>"+"<div class='card np-element np-hover'>" + "<div class='card-body text-center'>" + "<h5 class='card-title' id='dealer-brand'>This is a " +  makes.get(0) + " Dealership</h5>" + "<p class='card-text' id='dealer-brand-paragraph'>Click here to learn more about " + makes.get(0) + ".</p>" + "</div></div></a>" );}
			  
			  if(makeSize>1){
				  out.print("<br><p>This dealership also sells cars made by: ");
				  if(makeSize>2){
				  	for(int i=1;i<makeSize-2;i++){
						out.print("<a class='hov' href='/html/make-instance.jsp?make=" + makes.get(i).replace('&','$').replace(' ','_') + "~'>" + makes.get(i) + "</a>, ");  
				  	}
				  	out.print("and ");
				  }
				  out.print("<a class='hov' href='/html/make-instance.jsp?make=" + makes.get(makeSize-1).replace('&','$').replace(' ','_') + "~'>" + makes.get(makeSize-1) + "</a></p>");
			  }
			  %>
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