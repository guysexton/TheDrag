<!DOCTYPE html>

<%@ page import="thedrag.CodeStats" %>
<%@ page import="thedrag.IssueStats" %>

<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>About Us</title>

    <!-- Bootstrap -->
    <link href="../css/bootstrap-4.4.1.css" rel="stylesheet">
    <link href="../css/neumorph-full-dark.css" rel="stylesheet" type="text/css">
    <link href="../css/global.css" rel="stylesheet" type="text/css">
  </head>
  <body>
  
  <%
    	CodeStats codeStats = new CodeStats();
      	pageContext.setAttribute("commitText", codeStats.getListAsString());
      	IssueStats issueStats = new IssueStats();
      	pageContext.setAttribute("issueText", issueStats.getListAsString());
    %>
    
 
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
    	<h1 class="description">Description</h1>
    	
    	<p> TheDrag is a website built for users who want a consolidated place that holds all the automobile information they would need. 
    	Whether its a college student looking prices and dealerships for their first car purchase, speed-demon car enthusiasts looking to 
    	find the next hot rod on the market, or a conserved environmentalist who needs to get to where they but wants to find the least carbon emissive vehicle that 
    	they can get; TheDrag serves to bring all that info right into your web surfing device. </p>
    	
    	<h1 class="results">Interesting results</h1>
    	<p>
    	Being able to pull data from NHTSA, cars.com, carlogos.com, and more aided the team in regard to the topic of cars in the following ways:
<br>    -Nearly every if not all automobiles are going to have the same attributes such as pictures, colors, MPG, pricing, VINS etc. 
Multiple sources for the same attributes will help give the user a better general idea of a model without having to go to multiple websites 
to find the pieces of information. <br>
-It was very easy to find unique identifiers for each of our instances, VINs are unique to each car, no two car dealerships have the exact same name, and neither do any two makes. This made setting up our data structures to be directly addressible quite simple.
<br>
-JSPs are not as bad as we originally thought as long as you minimize the amount of Java code directly written in the JSP to a few method calls.
<br>-JavaScript interfacing with Java via JSPs can be a nightmare though
    	
    	</p>
	   <h1 class="display-4">Our Pit Crew</h1>
       <br>
       <hr>
       <br>
       <div class="row">
          <div class="col-md-4">
             <div class="np-element np-hover np-shadow-double">
                <img class="card-img-top" src="../assets/team/daniel.png" alt="Daniel Lazcano">
                <div class="card-body">
                   <h4 class="card-title">Daniel Lazcano</h4>
                   <p class="card-text">Primary Responsibility: Front End (Instance Pages)<br>
                   
                   Year: Senior<br>
                   Post-Grad: Write a book and get a cybersecurity engineering job in Austin<br>
                   Drives: 2010 Toyota Corolla</p>
                </div>
             </div>
          </div>
          <div class="col-md-4">
             <div class="np-element np-hover np-shadow-double">
                <img class="card-img-top" src="../assets/team/ethan.png" alt="Ethan Santoni-Colvin">
                <div class="card-body">
                   <h4 class="card-title">Ethan Santoni-Colvin</h4>
                   <p class="card-text">Primary Responsibility: Back End (OOP/REST API)<br>
                   Year: Junior<br>Post-Grad: Live a comfortable life working at a tech company in Austin, my hometown and the best city in the US<br>Drives: 2017 Accord Sport</p>
                </div>
				</div>
          </div>
          <div class="col-md-4">
             <div class="np-element np-hover np-shadow-double">
                <img class="card-img-top" src="../assets/team/frank.png" alt="Daniel Lazcano">
                <div class="card-body">
                  <h4 class="card-title">Frank Le</h4>
			   <p class="card-text">Primary Responsibility: Back End (QA)<br>
			   
			   Year: Junior<br>Post-Grad: Become a QA Developer and pursue a career as a Voice Actor<br>
			     Drives: 2018 Toyota Corolla<br>"I know nothing about cars..."</p>
                </div>
				</div>
          </div>
       </div>
       <br/>
       <br/>
       <div class="row">
          <div class="col-md-4">
             <div class="np-element np-hover np-shadow-double">
                <img class="card-img-top" src="../assets/team/guy.png" alt="Guy Sexton">
                <div class="card-body">
                   <h4 class="card-title">Guy Sexton</h4>
                   <p class="card-text">Primary Responsibilties: Front End (UI/UX)<br>
                   Year: Junior<br>Minor: UI/UX Design<br>Post-Grad: Work in consumer electronics then MBA at Stanford<br>Drives: 2018 Toyota C-HR<br></p>
                </div>
             </div>
          </div>
          <div class="col-md-4">
             <div class="np-element np-hover np-shadow-double">
                <img class="card-img-top" src="../assets/team/jonathan.png" alt="Jonathan Walsh">
                <div class="card-body">
                  <h4 class="card-title">Jonathan Walsh</h4>
                   <p class="card-text">Primary Responsibilities: Back End (OOP/REST API)<br>
                   
                   Year: Junior<br>Second Major: Government<br>Minor: Public Policy: Science & Technology<br>Post-Grad: Attend Yale Law School<br>Drives: 2015 Mini Cooper S</p>
                </div>
				</div>
          </div>
          <div class="col-md-4">
             <div class="np-element np-hover np-shadow-double">
                <img class="card-img-top" src="../assets/team/kishan.png" alt="Kishan Dayananda">
                <div class="card-body">
                   <h4 class="card-title">Kishan Dayananda</h4>
                   <p class="card-text">Primary Responsibilities: Phase Leader (Front End/Deliverables)<br>
                   Year: Junior<br>Post-Grad: Become a software developer<br>
                   Drives: 2013 Honda Accord<br></p>
                </div>
				</div>
          </div>
       </div>
       
       <h1 class="display-4">Github Statistics</h1>
       
       <h3>Commits, Adds, and Deletes</h3>
       <p>${commitText}</p>
       <br>
       <p>Number of unit tests: 23 evenly distributed among team</p>
       <h3>Issues</h3>
       <p>${issueText}</p>
       
       
       <h1 class="data">Our Sources</h1>
    	<p>
    	NHTSA API:
    	<a href="https://vpic.nhtsa.dot.gov/api/">https://vpic.nhtsa.dot.gov/api/</a>
    	</p>
    	<p>
    	We scraped data from this API by using the OkHttp library in Java. The API allows us to get access JSON files of details such as body type, horsepower, etc. on a specific car by decoding a VIN.
    	</p>
    	<br>
    	<p>
    	Carlogos.com:
    	<a href=" https://www.carlogos.org/">https://www.carlogos.org/</a>
    	</p>
    	<p>
    	We scrape the logos for different car makes from this website.
    	</p>
    	<br>
    	<p>
    	Web Scraping Cars.com:
    	<a href="https://www.cars.com/">https://www.cars.com/</a>
    	</p>
    	<p>
    	We wrote a web scraper that scrapes cars.com to pull cars for sale within 20 miles of UT campus. We also scrape pricing and dealership data, the make of the car, etc. To help us bucket our data.
    	The data is then parsed out and pushed to our MongoDB database for persistant storage and use.
    	</p>
    	<br>
    	<h1 class="tools">Our Tools</h1>
    	<p>
    	Eclipse - The main IDE everyone used to code the Java, CSS, HTML, JSP, XML, etc. files and to run the website on the localhost through Google App Engine integration.
    	</p>
    	<p>
    	Google AppEngine - The PaaS used to host and do webby stuff.
    	</p>
    	<p>
    	Adobe DreamWeaver - The Front-End team used this visual to code editor for streamlined editing/development of Bootstrap, CSS, and HTML files.
    	</p>
    	<p>
    	Bootstrap - A bootstrap design was found online and implemented into our front-end web design.
    	</p>
    	<p>
    	Maven - Used as our project builder and to quickly add third party libraries for us to use in our project
    	</p>
    	<p>
    	OKHTTP by Square - A request-response API that allows us to collect data on our instances through making a request to other APIs.
    	</p>
    	<p>
    	JSOUP - An open source Java HTMLparser library that we used to scrape websites. We used this tool to scrape two of our data sources, cars.com and carlogos.org for the information we needed for our models.
    	</p>
    	<p>
    	Jackson - A java based library we used to map our java objects (data we scraped for our models) to JSON. After our web scraper finished scraping the websites and stored the data into objects, we mapped them to three separate JSON files which we would later store into our database.
    	</p>
    	<p>
    	JUNIT - Unit testing framework for java. We used this to test our java code, specifically our web scraper.
    	</p>
    	<p>
    	Postman - API used to create collections and test API calls. We used this to test our RESTful API and getting data from APIs such as NHTSA. 
    	</p>
    	<p>
    	MongoDB - Our database program. We use this to store information about the cars, dealerships, and makes listed on our site. Our cluster is built on Atlas, MongoDB's free cloud storage.
    	</p>
    	<p>
    	Selenium IDE - Our front-end/GUI automated testing framework. We used its chrome extension version and exported JUNIT test cases as talked about in the above testing portion of this report. 
    	</p>
		
		<br>
    	
    	<h1 class="repo">Our GitHub</h1>
    	<p><a href="https://github.com/53Dude/TheDrag">https://github.com/53Dude/TheDrag</a></p>
       <br/>
       <br/>
       <hr>
       <div class="row">
          <div class="text-center col-lg-6 offset-lg-3">
             <p>Copyright &copy; 2020 &middot; All Rights Reserved &middot; <a href="#" >The Drag</a></p>
          </div>
       </div>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
    <script src="../js/jquery-3.4.1.min.js"></script>

    <!-- Include all compiled plugins (below), or include individual files as needed --> 
    <script src="../js/popper.min.js"></script>
    <script src="../js/bootstrap-4.4.1.js"></script>
  </body>
  <footer>
		<div class="np-divider-fat"></div>
		<p style="text-align: center;">Copyright © 2020 · All Rights Reserved · The Drag</p>
		<p style="text-align: center;"><a href="/html/home.html">Home</a> · <a href="/html/gitabout.jsp">About</a> · <a href="/html/makes.jsp?page=1">Makes</a> · <a href="/html/cars.jsp?page=1">Cars</a> · <a href="/html/dealers.jsp?page=1">Dealers</a></p>
	</footer>
</html>
