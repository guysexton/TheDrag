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
  
  <% 
  	int pageNum=1;
  	if (request.getParameter("page") != null) {
	    pageNum = Integer.parseInt(request.getParameter("page"));
	}
  	
  	boolean Z2A = false;
  	if (request.getParameter("Z2A") != null) {
	    Z2A = Boolean.parseBoolean(request.getParameter("Z2A"));
	}
  	
  	String make = "";
  	if (request.getParameter("make") != null) {
	    make = request.getParameter("make");
	}
  	
  	boolean carsListed = false;
  	if (request.getParameter("carsListed") != null) {
	    carsListed = Boolean.parseBoolean(request.getParameter("carsListed"));
	}
  	
  	String search = "";
  	if (request.getParameter("search") != null) {
	    search = request.getParameter("search").replace("%20"," ");
	}
  	
  	DBServlet db = new DBServlet();
	List<String> dealers = db.dealerNames;
	
	if(Z2A)
		Collections.reverse(dealers);
	
	if(carsListed){
		List<String> newDealers = new ArrayList<String>();
		for(String dealer:dealers){
			if(!((ArrayList<String>)db.getDealershipAttribute(dealer, "cars")).isEmpty())
				newDealers.add(dealer);
		}
		dealers = newDealers;
	}
	
	if(!make.equals("")){
		List<String> newDealers = new ArrayList<String>();
		for(String dealer:dealers){
			if(((ArrayList<String>)db.getDealershipAttribute(dealer, "makes")).contains(make))
				newDealers.add(dealer);
		}
		dealers = newDealers;
	}
	
	if(!search.equals("")){
		dealers = db.dealerSearch(dealers, search);
	}
	
		
	List<String> pageDealers = new ArrayList<>();
	int totalPgs = (int)Math.ceil(dealers.size()/10)+1;
	int windowStart = (pageNum-1)*10;
	int windowEnd = Math.min((windowStart+10),dealers.size());	
	
	for(int i = windowStart; i < windowEnd; i++){
		pageDealers.add(dealers.get(i));
	}
  %>
  
  <body>
    	<!-- body code goes here -->

  <!--NEW Navigation bar-->
		 <body class="navbar-dark">

		 	<!-- Attempt to resize logo. Should be something else. -->
			<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
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


		<h1 class="offset-1 col-9 np-text-accent">Dealers</h1>
		
		<div class="offset-1 col-10">
			<form name="sfs" action="/html/dealers.jsp" method="post">
			  <span class="np-form-group" style="display:inline-block;">
				<label><strong>Sort:</strong></label>
				<input type="radio" class="np-form-element" name="Z2A" value="false" <%if(!Z2A) out.print("checked");%>>A-Z 
				<input type="radio" class="np-form-element" name="Z2A" value="true" <%if(Z2A) out.print("checked");%>>Z-A
				&nbsp;
				<strong>Cars Available: </strong><input type="checkbox" class="np-form-element" name="carsListed" value="true" <%if(carsListed) out.print("checked='checked'");%>>
				&nbsp;
				<strong>Filter by Make:</strong><select class="np-form-element" id="make" name="make" value="Filter by Make">
					<%
						List<String> makeList = db.makeNames;
						Collections.sort(makeList);
						makeList.add(0,"");
						for(String m : makeList){
							if(make.equals(m))
								out.print("<option value='" + m.replace(" ","%20") + "' selected>" + m + "</option>");
							else
								out.print("<option value='" + m.replace(" ","%20") + "'>" + m + "</option>");
						}
					%>
				</select>
				
				<input class="np-btn" type="submit" value="Apply Filters">
				<a class="np-btn hov" href="/html/dealers.jsp?page=1">Clear Filters</a>
			   </span>
			   <span class="np-form-group offset-2" style="display:inline-block;">
				<label for="search">Search:</label>
				<input class="np-form-element" type="text" id="search" name="search" value="<%=search%>">
				<input class="np-btn" type="submit" value="Search">
			   </span>
			</form>
		</div>

	  <ul class="offset-2 dealer-grid" id="dealer-grid">

		  <!-- <li class="card np-element np-hover col-4 dealer-card" style="margin: 20px;"><a href="#">
			  <h3 style="text-align: center;">Dealer</h3>
			  <div class="np-img-wrapper" width="50px" height="50px"><img class="np-img-expand" src="https://pictures.dealer.com/p/penskeroundrocktoyota/0040/6f8b9746ac8eb3968fc541d0834b047cx.jpg" width="inherit" height="inherit" style="margin: 10px"></div>
			  <p><strong>Address:</strong> #### Street St, Austin, TX 78705</p>
			  <p><strong>Phone:</strong> (###) ###-####</p>
			  <p><strong>Website:</strong><a href="#"> www.address.com</a></p>
			  </a>
		  </li> -->
		  
		  <% 
		  
		  
		  for(String s:pageDealers){
			  String name = db.getDealershipAttribute(s, "name").toString();
			  String slug = name.replace('&','$').replace(' ','_').replace("'",".")+"~";
			  
			  String listing= "<li class='card np-element np-hover col-4 dealer-card' style='margin: 20px;height:275px;' >"+
						"<a href='/html/view-dealer.jsp?dealership=" + slug + "' style='margin:0px;display:block;width:100%;height:100%;'>"+
						"<h3 style='text-align: center;'>" + name + "</h3>";
					
			String image = db.getDealershipAttribute(s, "img").toString();
			String address = db.getDealershipAttribute(s, "address").toString();
			String phoneNum = db.getDealershipAttribute(s, "phoneNum").toString();
			String website = db.getDealershipAttribute(s, "website").toString();
			
			listing += "<div class='np-img-wrapper' width='50px' height='50px' style='display:block;'>";
			if(!image.equals(""))
				listing += "<img class='np-img-expand' src='" + image + "' width='inherit' height='inherit' style='margin: 10px'>";
			listing+="</div>";
				
			if(!address.equals(""))
				listing += "<p><strong>Address:</strong> " + address + "</p>";
			else
				listing += "<p><strong>Address:</strong> N/A </p>";
				
			if(!phoneNum.equals(" "))
				listing += "<p><strong>Phone:</strong> " + phoneNum + "</p>";
			else
				listing += "<p><strong>Phone:</strong> N/A </p>";
				
			if(!website.equals(""))
				listing += "<a href='" + website + "'><strong>Visit Dealer Website</strong></a>";
			else
				listing += "<p><strong>No Dealer Website Listed</strong></p>";
				
			listing += "</a> </li>";
			
			out.print(listing);
		  }
		  
		  %>
		  
	  </ul>

	  <nav class="col-xl-4 offset-xl-2" style="align-content: center;" aria-label="Page navigation example">
    <!-- Add class .pagination-lg for larger blocks or .pagination-sm for smaller blocks-->
    <ul class="pagination" style="align-content: center;" id="pagination-wrapper">
    </ul>
  </nav>
	<script>
	
	<%
		String vars = "&Z2A="+Z2A+"&make="+make+"&carsListed="+carsListed+"&search="+search;
	%>
	
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
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element np-hover" style="margin:5px;" href="dealers.jsp?page=1<%=vars%>" aria-label="First"> <span aria-hidden="true">&laquo;</span> <span class="sr-only">First</span> </a> </li>`
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element np-hover" style="margin:5px;" href="dealers.jsp?page=<%=pageNum-1%><%=vars%>" aria-label="Previous"> <span aria-hidden="true">&lt;</span> <span class="sr-only">Previous</span> </a> </li>`
			} else {
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element" style="margin:5px;" href="dealers.jsp?page=1<%=vars%>" aria-label="First" disabled> <span aria-hidden="true">&laquo;</span> <span class="sr-only">First</span> </a> </li>`
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element" style="margin:5px;" href="dealers.jsp?page=<%=totalPgs%><%=vars%>" aria-label="Previous" disabled> <span aria-hidden="true">&lt;</span> <span class="sr-only">Previous</span> </a> </li>`
			}
		
		for(var page = winStart; page <= winEnd ; page++){
			if(page==<%=pageNum%>){
				var temp = `<li><a class="np-element np-shadow-inverse np-hover-inverse" href="dealers.jsp?page=`
						temp+=page
						temp+=`<%=vars%>" style="margin:5px;">` + page + `</a></li>`
				wrapper.innerHTML +=  temp
				} else {
				var temp = `<li><a class="np-element np-hover-" href="dealers.jsp?page=`
					temp+=page
					temp+=`<%=vars%>" style="margin:5px;">` + page + `</a></li>`
					wrapper.innerHTML +=  temp
			}
		}
		
			if(<%=pageNum%> < <%=totalPgs%>){
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element np-hover" style="margin:5px;" href="dealers.jsp?page=<%=pageNum+1%><%=vars%>" aria-label="Next"> <span aria-hidden="true">&gt;</span> <span class="sr-only">Next</span> </a> </li>`
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element np-hover" style="margin:5px;" href="dealers.jsp?page=<%=totalPgs%><%=vars%>" aria-label="last"> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Last</span> </a> </li>`
			} else {
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element" style="margin:5px;" href="dealers.jsp?page=1<%=vars%>" aria-label="Next"> <span aria-hidden="true">&gt;</span> <span class="sr-only">Next</span> </a> </li>`
				wrapper.innerHTML += `<li class="page-item"> <a class="np-element" style="margin:5px;" href="dealers.jsp?page=<%=totalPgs%><%=vars%>" aria-label="Last" disabled> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Last</span> </a> </li>`
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
