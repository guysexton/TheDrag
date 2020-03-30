// JavaScript Document

var model =   {
	    "name" : "2020 BMW M2 Competition",
	    "img" : "https://www.cstatic-images.com/supersized/in/v1/440595/WBS2U7C06L7F11298/10130e51db7f61303ebf08d970a090f5.jpg",
	    "url" : "https://www.cars.com/vehicledetail/detail/795726481/overview/",
	    "vin" : "WBS2U7C06L7F11298",
	    "dealership" : "BMW of Austin",
	    "make" : "BMW",
	    "price" : 66210,
	    "mpg" : "18city/25hwy"
	  }


createModelInstance()

function createModelInstance(){
	document.getElementById('dealer-name').innerHTML = dealer.name
	
	document.getElementById('dealer-img').innerHTML = "<img class='np-img-expand' src='" + dealer.img + "' width='inherit' height='inherit' style='margin: 10px'>"
	
	document.getElementById('address').innerHTML = dealer.address
	
	document.getElementById('phone').innerHTML = dealer.phoneNum
	
	if(dealer.website == "")
		document.getElementById('link').hidden = true
	else
		document.getElementById('link').href = dealer.website
	
	document.getElementById('dealer-brand-card').href = "#"
	document.getElementById('dealer-brand').innerHTML = "This is a " +  dealer.makes[0] + " Dealership"
	document.getElementById('dealer-brand-paragraph').innerHTML = "Click here to learn more about " + dealer.makes[0] + "."
	
	var grid = $('#car-grid')
	
	var myGrid = dealer.cars
	
	for(var i = 1 in myGrid){
		var listing = `<li class="card np-element np-hover col-2 car-card" style="margin: 20px;"><a href="#">` + myGrid[i] + `</a></li>`
		
		grid.append(listing)
	}
}