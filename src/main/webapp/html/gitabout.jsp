<!DOCTYPE html>

<%@ page import="thedrag.GithubStats" %>

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
  	
   	GithubStats githubStats = new GithubStats();
  	pageContext.setAttribute("commitText", githubStats.getListAsString());
  
  %>
    
 
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

    
    
    <div class="container">
    	<h1 class="description">Description</h1>
    	
    	<p> TheDrag is a website built for users who want a consolidated place that holds all the automobile information they would need. 
    	Whether its a college student looking prices and dealerships for their first car purchase, speed-demon car enthusiasts looking to 
    	find the next hot rod on the market, or a conserved environmentalist who needs to get to where they but wants to find the least carbon emissive vehicle that 
    	they can get; TheDrag serves to bring all that info right into your web surfing device. </p>
    	
    	<h1 class="results">Interesting results</h1>
    	<p>
    	While we can't answer this part fully yet since Phase I is about making static pages to show our vision and collecting only a bit of data, we can still show some interesting results that might happen from scraping our data sources.
    	Scraping our data from cars.com yields data on car models and dealership instances. We can change how our web scraper works so when it programmatically scrapes data we have seperate JSON files for dealers and models each with its own unique attributes.
    	We can then use google images API and/or google maps API to get the images we need for the model instance and/or dealer instance. Furthermore, getting data from the NHTSA and CarMD API's provides us on even more info on car model instances that cars.com does not provide.
    	We can add the additional data to our model instances. 
    	
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
                   <p class="card-text">Year: Senior<br>
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
                   <p class="card-text">Year: Junior<br>Post-Grad: Live a comfortable life working at a tech company in Austin, my hometown and the best city in the US<br>Drives: 2017 Accord Sport</p>
                </div>
				</div>
          </div>
          <div class="col-md-4">
             <div class="np-element np-hover np-shadow-double">
                <img class="card-img-top" src="../assets/team/frank.png" alt="Daniel Lazcano">
                <div class="card-body">
                  <h4 class="card-title">Frank Le</h4>
			   <p class="card-text">Year: Junior<br>Post-Grad: Become a QA Developer and pursue a career as a Voice Actor<br>
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
                   <p class="card-text">Year: Junior<br>Minor: UI/UX Design<br>Post-Grad: Work in consumer electronics then MBA at Stanford<br>Drives: 2018 Toyota C-HR<br></p>
                </div>
             </div>
          </div>
          <div class="col-md-4">
             <div class="np-element np-hover np-shadow-double">
                <img class="card-img-top" src="../assets/team/jonathan.png" alt="Jonathan Walsh">
                <div class="card-body">
                  <h4 class="card-title">Jonathan Walsh</h4>
                   <p class="card-text">Year: Junior<br>Second Major: Government<br>Minor: Public Policy: Science & Technology<br>Post-Grad: Attend Yale Law School<br>Drives: 2015 Mini Cooper S</p>
                </div>
				</div>
          </div>
          <div class="col-md-4">
             <div class="np-element np-hover np-shadow-double">
                <img class="card-img-top" src="../assets/team/kishan.png" alt="Kishan Dayananda">
                <div class="card-body">
                   <h4 class="card-title">Kishan Dayananda</h4>
                   <p class="card-text">Year: Junior<br>Post-Grad: Become a software developer<br>
                   Drives: 2013 Honda Accord<br></p>
                </div>
				</div>
          </div>
       </div>
       
       <h1 class="display-4">Github Statistics</h1>
       
       <h3>Commits, Adds, and Deletes</h3>
       <p>${commitText}</p>
       <h3>Issues</h3>
       
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
