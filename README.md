# TheDrag
About: 
TheDrag is a website built for users who want a consolidated place that holds all the automobile information they would need. Whether its a college student looking prices and dealerships for their first car purchase, speed-demon car enthusiasts looking to find the next hot rod on the market, or a conserved environmentalist who needs to get to where they but wants to find the least carbon emissive vehicle that they can get; TheDrag serves to bring all that info right into your web surfing device. 

Website Link: https://thedrag.appspot.com/<br>
Models: Make, Car, Year

Team Members: Name, UTEID, Github Link<br>
Daniel Lazcano, dl32678, https://github.com/DanielLazcano<br>
Ethan Santoni-Colvin, ers2677, https://github.com/ethansantonicolvin<br>
Frank Le, Fpl227,https://github.com/fle734449<br>
Guy Sexton, gwm639,https://github.com/53Dude<br>
Jonathan Walsh, jdw4867, https://github.com/jdw4867<br>
Kishan Dayananda, kd8953, https://github.com/kish314


Estimated Completion Time:<br>
Daniel Lazcano = 20 Hours<br>
Ethan Santoni-Colvin = 10 Hours<br>
Frank Le = 10 Hours<br>
Guy Sexton = 20 Hours<br>
Jonathan Walsh = 15 Hours<br> 
Kishan Dayananda = 20 Hours


Actual Completion Time:<br>
Daniel Lazcano = 26 Hours<br>
Ethan Santoni-Colvin = 11 Hours<br>
Frank Le = 10 Hours<br>
Guy Sexton = 30 Hours<br>
Jonathan Walsh = 10 Hours<br>
Kishan Dayananda = 24 Hours

User Stories
 
Developer User Stories:
PHASE III
I’m browsing the dealers in my area and I would like to know if any dealerships specialize in selling Honda cars. 
Estimate: 2 hours
Actual: 30 mins
-This was implemented by filtering each dealer on the listing pages for the specific make(s) of cars they specialize in selling.

Tom suffers from an auto-immune disease. With the current stay-at-home order due to COVID-19, Tom needs a way to safely browse the cars available in his area without waiting on the phone for a salesperson to walk through each car for sale.
Estimate: 1 hour
Actual: 1 hour
-The user can select to filter dealerships by if they have cars available when browsing. Thus saving the time of physically visiting a dealership or calling in

Cathy hates when ads are targeted at her after visiting a site once. This has made Cathy avoid shopping for cars in the past. Cathy would like a place where she can view the data without the risk of being attacked with targeted ads.
Estimate: 4 hours
Actual: 5 hours
-Completed by establishing a web-scraper to pull the data from sites that track users with targeted ads. The data is then used from a database instead of accessing the original website. This protects the user from targeted ads as The Drag is not ad supported and does not collect information on users.

Cynthia is a cybersecurity researcher. She has never purchased a car before and is looking for a new car to share with her partner Denver. Cynthia has heard about shopping sites showing different prices to different users based on their browser, device, or location or other factors. She and Denver have observed this price difference in their own search and would like to know the real list price for a certain car, not just the price an algorithm decides based on varying user device factors.
Estimate: 3 hours
Actual: 3 hours
-The web scraper for The Drag is ignorant to all of the varying devices and locations the modern car buyer to be browsing for their new ride. Due to this fact, the site will only display one price for the car, not varying for multiple factors to try and trick the user into paying more than they should.

Ira loves Toyota! He has only ever driven cars from Toyota and does not plan to stop that any time soon. Ira is wondering what years Toyota manufactured cars to help in his search for the perfect Toyota. 
Estimate: 3 hours
Actual: 2 hours
-The web scraper was expanded in functionality to scrape the years that the each make has manufactured cars. This is displayed with the make to allow the user to determine the years a make was actively manufacturing cars.

PHASE II
I want a list of current manufacturers that are sold around my area.
Estimate: 2 Hours
Actual: 4 Hours
-Involved web scraping with the backend team and passing it to the front end team to dynamically create the instance pages in the model listing JSP files. 

I'm a die-hard Subaru fan, I need to know which models are made by Subaru.
Estimate: 5 Hours
Actual: 3 Hours
-The data for this was already acquired in the data scraping and implementation to actually render and display the data was very simple after the completion of the dealer and car instance pages.

As an undergraduate college student with no income, I want a way to find a cheap car under $20,000, so I can drive to school. 
Estimate: 3 Hours
Actual: 3 Hours
-This user story expresses a need to list an attribute of each of the cars that are listed as being available for sale. Its an essential part of the service we want to provide to the user; namely that they would be able to search for cars in their price range.

As a car enthusiast I want to know what exact body type of the car I’m interested in. 
Estimate: 3 Hours
Actual: 3 Hours
-This user story describes one of the main audiences that TheDrag is catering too and implementation is similar to user story 5 where API calls are made from NHSTA. 

I'm in the market for a car. More than just knowing the price, I want to know what the car will look like before I buy it. 
Estimate: 2 Hours
Actual: 2 hours
-Beyond knowing text attributes describing the car, a user would want to know what the car would look like to be interested in buying it, and thus this is a key user story to satisfy. Including images of the cars also enhances the aesthetics of the site. 

PHASE I
I hate really long pages of lists and really short pages of lists on websites. I wish there was an option like on other sites to go through pages to see a certain amount of things. 
Estimate: 4 Hours
Actual: 7 Hours
-User story essentially describes a need for pagination so that a single page doesn’t get too littered with elements/info that can also appear overwhelming to the average user. 
The estimated 4 hours was due to finding existing bootstrap JS support online and thinking that we only had to implement said support. However, we ran into issues with pulling JSONs and general data into said JS files. So we scrapped that and figured out how to implement it in JSP, increasing the estimated time to about 7 to 8 hours. 

As a car fanatic, I can easily get lost browsing these amazing car websites, like TheDrag! I need a way to get back to the home page.
Estimate: 2 Hours
Actual: 1 Hour
-We edited the navbar on the JSP files so that the website logo included an HREF that takes the user back to the home HTML page. Tricky part was editing the navbar but turned out to take less time than estimated. 

As a first time car buyer and opportunist, I am very concerned about missing a good deal. I would love a website that shows all the cars I'm interested in within my area and updates that list too! 
Estimate: 8 Hours
Actual: 15 Hours
-User story involves showing many listings in the Austin area. So we made a web-scraper to search through listings in the area to pull data and compile a database of listed cars. This list can be easily updated by running the scraper. The pricing data is also acquired through the scraper and then displayed on the car listing page.

As a user with age-related macular degeneration, seeing blurry or disproportionately sized images on websites puts a strain on my eyes. I would love some uniformity in image styling across a website! 
Estimate: 3 Hours
Actual: 4 Hours
-This user story is describing an improvement from last phase in terms of visual continuity that enhances the user experiences. Mainly involved some Adobe dreamweaver and string manipulation within the JSP files. The actual time for the general HTML/CSS styling was longer. (Other than the image styling as described in this user story). 

As a commuter to work, I want to know the highway mpg of certain models. 
Estimate: 3 Hours
Actual: 3 Hours
-This user story described an original idea/want on the conception of TheDrag. This involved a back-end member showing a front-end member how to do API calls from the NHTSA. Involved making servlets and making helper functions and instantiating them for every JSP model file. 

Customer User Stories:
PHASE III
As a car marketing junkie, I want to know if a car company with a logo I saw online is selling cars in my area.
Estimate: 1 hour
Actual: 0.5 hours
-This user story was resolved by adding a data source from our web-scraper to pull png images from carlogos.com and associate them with specific makes.

As a car shopping customer looking for a vendor listing a car of a specific name, I want to be able to search a specific car type and see where that car is being sold.
Estimate: 2 hours
Actual: 1 hours
-Implementing this functionality was a result of cross-referencing our MongoDB database under dealerships, while displaying information about a specific car instance.

I found a car that I want to buy at a dealership. Since I just moved into the area, I don’t really know my way around town. I want to be able to see the dealership’s location on a map.
Estimate: 2 hours
Actual: 1 hours
-This user story describes a desire to see an embedded map on each dealerships page. We solved this issue by using the google maps embed API.

As an avid car shopper, I want to be able to go back to square 1 on a car-shopping website after I have applied a lot of different filters and search queries.
Estimate: 0.5 hours 
Actual: 0.5 hours
-This just involved writing a button that would reload the search page with no passed forms, thereby resetting the page and clearing all filters/search queries.

As a first time car buyer, I don’t want to waste time going to a dealer that isn’t currently listing any cars. I want the ability to only display dealerships that are currently offering cars for sale.
Estimate: 1 hour
Actual: 1 hour
-Satisfying this user story involved reading the car listing attributes of each of our dealership instances in MongoDB, and modifying the website display depending on if cars were listed or scraped from our API.

PHASE II

N/A

PHASE I
As a concerned user of who is making my product I would like to see the developer team of the product. 
Estimate: 2 Hours
Actual: 1 Hour
-Created about page that shows developer team

I want to be clear about the purpose of the website. 
Estimate: 1 Hours
Actual: .5 Hours
-Landing page shows purpose of the site.

As a user with bad eyesight I want to be able to use the website easily for someone with my disability. 
Estimate: 2 Hours
Actual: 2 Hours
-We located a site-wide theme that is easy to read and cleanly displays all the info on each page.

As someone who browses the internet everyday, I would like a clean and polished UI. 
Estimate: 3 Hours
Actual: 3 Hours
-Describes a want from a user to not view a visually unappealing website. 

I would like to know the closest locations of dealerships selling a certain make. 
Estimate: 3 Hours
Actual: 1 hour
-User describes the functionality the website can give to them. 
