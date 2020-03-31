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
    	Being able to pull data from NHTSA, carmd, google, and more aided the team in regard to the topic of cars in the following ways:
<br>    -Nearly every if not all automobiles are going to have the same attributes such as pictures, colors, MPG, pricing, VINS etc. 
Multiple sources for the same attributes will help give the user a better general idea of a model without having to go to multiple websites 
to find the pieces of information. <br>-Other attributes found through scraping and APIs such as the VIN will later help to find specific automobiles with all the information of it.
    	
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
       <h3>Issues</h3>
       <p>${issueText}</p>
       
       <h1 class="data">Our Sources</h1>
    	<p>
    	NHTSA API:
    	<a href="https://vpic.nhtsa.dot.gov/api/">https://vpic.nhtsa.dot.gov/api/</a>
    	</p>
    	<p>
    	We scraped data from this API by using the OkHttp library in Java. The API allows us to get access JSON files of makes and models by going to a corresponding url and or decoding a VIN.
    	For Phase I of our project we have a java program MakeServlet that we ran to collect the data of three instances of make and wrote them to a corresponding JSON file, that we showed statically on the instance pages.
    	</p>
    	<br>
    	<p>
    	CarMD API:
    	<a href="https://api.carmd.com/member/docs#image">https://api.carmd.com/member/docs#image</a>
    	</p>
    	<p>
    	We plan to scrape data on our models through this API by using HTTP request and decoding the VIN number for car models. We use this API for additional info such as car image, type, etc
    	</p>
    	<br>
    	<p>
    	Web Scraping Cars.com:
    	<a href="https://www.cars.com/">https://www.cars.com/</a>
    	</p>
    	<p>
    	We scraped data from this website using a web scraper which outputs the data we want into a JSON File. We plan to store the JSON file in a database where we can call on the information we need.
    	This source provides us with car model instances and dealership instances as well as important attributes for each model instance.
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
    	JSON Simple by Google - Used to encode the JSON file from CSV.
    	</p>
    	<p>
    	OKHTTP by Square - A request response API that allows us to collect data on our instances through making a request to other APIs.
    	</p>
    	<p>
    	Web Scraper by webscraper.io - A chrome extension that allows us to scrape data from websites easily. We can choose elements we want to  scrape and running the app allows us to download the scraped data as a CSV file which we can convert to a JSON file ourselves programmatically. We plan to only use this tool for Phase I, going forward we will program our own web scraper.
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
</html>
