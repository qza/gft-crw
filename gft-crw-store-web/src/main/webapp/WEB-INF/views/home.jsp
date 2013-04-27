<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<link rel='stylesheet' type='text/css' media='screen'
	href='<c:url value="/resources/css/style.css"/>' />

<script type='text/javascript'
	src='http://code.jquery.com/jquery-1.9.1.min.js'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/navigator.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/script.js"/>'></script>

<title>Products</title>

</head>

<body>

	<script type="text/javascript">
		$(function() {
			new Home().initAll();
		});
	</script>

	<h1>Products</h1>

	<form id="product_form">
		<table id="products_table"></table>
		<input id="submit" type="submit" name="submit_button" value="submit" />
		<input id="page_number" type="hidden" name="page_number" value="1" />
	</form>
	
	<h2>Categories</h2>
	
	<div id="main_categories_div">
		<a class="catlink" href="products?category%3DPrime%20Instant%20Videos">Prime Instant Videos</a>
		<a class="catlink" href="products?category%3DLearn%20More%20About%20Amazon%20Prime">Learn More About Amazon Prime</a>
		<a class="catlink" href="products?category%3DAmazon%20Instant%20Video%20Store">Amazon Instant Video Store</a>
		<a class="catlink" href="products?category%3DYour%20Video%20Library">Your Video Library</a>
		<a class="catlink" href="products?category%3DWatch%20Anywhere">Watch Anywhere</a>
		<a class="catlink" href="products?category%3DMP3%20Music%20Store">MP3 Music Store</a>
		<a class="catlink" href="products?category%3DMusic%20on%20Kindle%20Fire">Music on Kindle Fire</a>
		<a class="catlink" href="products?category%3DCloud%20Player%20for%20Web">Cloud Player for Web</a>
		<a class="catlink" href="products?category%3DCloud%20Player%20for%20Android">Cloud Player for Android</a>
		<a class="catlink" href="products?category%3DCloud%20Player%20for%20iOS">Cloud Player for iOS</a>
		<a class="catlink" href="products?category%3DCloud%20Player%20for%20Home">Cloud Player for Home</a>
		<a class="catlink" href="products?category%3DYour%20Cloud%20Drive">Your Cloud Drive</a>
		<a class="catlink" href="products?category%3DGet%20the%20Desktop%20App">Get the Desktop App</a>
		<a class="catlink" href="products?category%3DCloud%20Drive%20Photos%20for%20Android">Cloud Drive Photos for Android</a>
		<a class="catlink" href="products?category%3DLearn%20More%20About%20Cloud%20Drive">Learn More About Cloud Drive</a>
		<a class="catlink" href="products?category%3DKindle">Kindle</a>
		<a class="catlink" href="products?category%3DKindle%20Paperwhite">Kindle Paperwhite</a>
		<a class="catlink" href="products?category%3DKindle%20Paperwhite%203G">Kindle Paperwhite 3G</a>
		<a class="catlink" href="products?category%3DKindle%20E-reader%20Accessories">Kindle E-reader Accessories</a>
		<a class="catlink" href="products?category%3DKindle%20Books">Kindle Books</a>
		<a class="catlink" href="products?category%3DNewsstand">Newsstand</a>
		<a class="catlink" href="products?category%3DKindle%20Owners'%20Lending%20Library">Kindle Owners' Lending Library</a>
		<a class="catlink" href="products?category%3DFire">Fire</a>
		<a class="catlink" href="products?category%3DFire%20HD">Fire HD</a>
		<a class="catlink" href="products?category%3DFire%20HD%208.9%22">Fire HD 8.9"</a>
		<a class="catlink" href="products?category%3DFire%20HD%208.9%22%204G">Fire HD 8.9" 4G</a>
		<a class="catlink" href="products?category%3DKindle%20Fire%20Accessories">Kindle Fire Accessories</a>
		<a class="catlink" href="products?category%3DKindle%20Cloud%20Reader">Kindle Cloud Reader</a>
		<a class="catlink" href="products?category%3DFree%20Kindle%20Reading%20Apps">Free Kindle Reading Apps</a>
		<a class="catlink" href="products?category%3DManage%20Your%20Kindle">Manage Your Kindle</a>
		<a class="catlink" href="products?category%3DApps">Apps</a>
		<a class="catlink" href="products?category%3DGames">Games</a>
		<a class="catlink" href="products?category%3DTest%20Drive%20Apps">Test Drive Apps</a>
		<a class="catlink" href="products?category%3DAmazon%20Apps">Amazon Apps</a>
		<a class="catlink" href="products?category%3DYour%20Apps%20and%20Devices">Your Apps and Devices</a>
		<a class="catlink" href="products?category%3DGame%20Downloads">Game Downloads</a>
		<a class="catlink" href="products?category%3DFree-to-Play%20Games">Free-to-Play Games</a>
		<a class="catlink" href="products?category%3DSoftware%20Downloads">Software Downloads</a>
		<a class="catlink" href="products?category%3DYour%20Games%20%26%20Software%20Library">Your Games &amp; Software Library</a>
		<a class="catlink" href="products?category%3DAudible%20Membership">Audible Membership</a>
		<a class="catlink" href="products?category%3DAudible%20Audiobooks%20%26%20More">Audible Audiobooks &amp; More</a>
		<a class="catlink" href="products?category%3DBestsellers">Bestsellers</a>
		<a class="catlink" href="products?category%3DNew%20%26%20Notable">New &amp; Notable</a>
		<a class="catlink" href="products?category%3DListener%20Favorites">Listener Favorites</a>
		<a class="catlink" href="products?category%3DWhispersync%20for%20Voice">Whispersync for Voice</a>
		<a class="catlink" href="products?category%3DBooks">Books</a>
		<a class="catlink" href="products?category%3DKindle%20Books">Kindle Books</a>
		<a class="catlink" href="products?category%3DChildren's%20Books">Children's Books</a>
		<a class="catlink" href="products?category%3DTextbooks">Textbooks</a>
		<a class="catlink" href="products?category%3DAudiobooks">Audiobooks</a>
		<a class="catlink" href="products?category%3DMagazines">Magazines</a>
		<a class="catlink" href="products?category%3DMovies%20%26%20TV">Movies &amp; TV</a>
		<a class="catlink" href="products?category%3DBlu-ray">Blu-ray</a>
		<a class="catlink" href="products?category%3DAmazon%20Instant%20Video">Amazon Instant Video</a>
		<a class="catlink" href="products?category%3DMusic">Music</a>
		<a class="catlink" href="products?category%3DMP3%20Downloads">MP3 Downloads</a>
		<a class="catlink" href="products?category%3DMusical%20Instruments">Musical Instruments</a>
		<a class="catlink" href="products?category%3DVideo%20Games">Video Games</a>
		<a class="catlink" href="products?category%3DGame%20Downloads">Game Downloads</a>
		<a class="catlink" href="products?category%3DTV%20%26%20Video">TV &amp; Video</a>
		<a class="catlink" href="products?category%3DHome%20Audio%20%26%20Theater">Home Audio &amp; Theater</a>
		<a class="catlink" href="products?category%3DCamera%2C%20Photo%20%26%20Video">Camera, Photo &amp; Video</a>
		<a class="catlink" href="products?category%3DCell%20Phones%20%26%20Accessories">Cell Phones &amp; Accessories</a>
		<a class="catlink" href="products?category%3DVideo%20Games">Video Games</a>
		<a class="catlink" href="products?category%3DMP3%20Players%20%26%20Accessories">MP3 Players &amp; Accessories</a>
		<a class="catlink" href="products?category%3DCar%20Electronics%20%26%20GPS">Car Electronics &amp; GPS</a>
		<a class="catlink" href="products?category%3DAppliances">Appliances</a>
		<a class="catlink" href="products?category%3DMusical%20Instruments">Musical Instruments</a>
		<a class="catlink" href="products?category%3DElectronics%20Accessories">Electronics Accessories</a>
		<a class="catlink" href="products?category%3DLaptops%2C%20Tablets%20%26%20Netbooks">Laptops, Tablets &amp; Netbooks</a>
		<a class="catlink" href="products?category%3DDesktops%20%26%20Servers">Desktops &amp; Servers</a>
		<a class="catlink" href="products?category%3DComputer%20Accessories%20%26%20Peripherals">Computer Accessories &amp; Peripherals</a>
		<a class="catlink" href="products?category%3DComputer%20Parts%20%26%20Components">Computer Parts &amp; Components</a>
		<a class="catlink" href="products?category%3DSoftware">Software</a>
		<a class="catlink" href="products?category%3DPC%20Games">PC Games</a>
		<a class="catlink" href="products?category%3DPrinters%20%26%20Ink">Printers &amp; Ink</a>
		<a class="catlink" href="products?category%3DOffice%20%26%20School%20Supplies">Office &amp; School Supplies</a>
		<a class="catlink" href="products?category%3DKitchen%20%26%20Dining">Kitchen &amp; Dining</a>
		<a class="catlink" href="products?category%3DFurniture%20%26%20D%C3%A9cor">Furniture &amp; Décor</a>
		<a class="catlink" href="products?category%3DBedding%20%26%20Bath">Bedding &amp; Bath</a>
		<a class="catlink" href="products?category%3DAppliances">Appliances</a>
		<a class="catlink" href="products?category%3DPatio%2C%20Lawn%20%26%20Garden">Patio, Lawn &amp; Garden</a>
		<a class="catlink" href="products?category%3DArts%2C%20Crafts%20%26%20Sewing">Arts, Crafts &amp; Sewing</a>
		<a class="catlink" href="products?category%3DPet%20Supplies">Pet Supplies</a>
		<a class="catlink" href="products?category%3DHome%20Improvement">Home Improvement</a>
		<a class="catlink" href="products?category%3DPower%20%26%20Hand%20Tools">Power &amp; Hand Tools</a>
		<a class="catlink" href="products?category%3DLamps%20%26%20Light%20Fixtures">Lamps &amp; Light Fixtures</a>
		<a class="catlink" href="products?category%3DKitchen%20%26%20Bath%20Fixtures">Kitchen &amp; Bath Fixtures</a>
		<a class="catlink" href="products?category%3DHardware">Hardware</a>
		<a class="catlink" href="products?category%3DBuilding%20Supplies">Building Supplies</a>
		<a class="catlink" href="products?category%3DGrocery%20%26%20Gourmet%20Food">Grocery &amp; Gourmet Food</a>
		<a class="catlink" href="products?category%3DWine">Wine</a>
		<a class="catlink" href="products?category%3DNatural%20%26%20Organic">Natural &amp; Organic</a>
		<a class="catlink" href="products?category%3DHealth%20%26%20Personal%20Care">Health &amp; Personal Care</a>
		<a class="catlink" href="products?category%3DBeauty">Beauty</a>
		<a class="catlink" href="products?category%3DToys%20%26%20Games">Toys &amp; Games</a>
		<a class="catlink" href="products?category%3DBaby">Baby</a>
		<a class="catlink" href="products?category%3DKids'%20Clothing">Kids' Clothing</a>
		<a class="catlink" href="products?category%3DBaby%20Clothing">Baby Clothing</a>
		<a class="catlink" href="products?category%3DVideo%20Games%20for%20Kids">Video Games for Kids</a>
		<a class="catlink" href="products?category%3DAmazon%20Mom">Amazon Mom</a>
		<a class="catlink" href="products?category%3DBaby%20Registry">Baby Registry</a>
		<a class="catlink" href="products?category%3DClothing">Clothing</a>
		<a class="catlink" href="products?category%3DShoes">Shoes</a>
		<a class="catlink" href="products?category%3DHandbags">Handbags</a>
		<a class="catlink" href="products?category%3DAccessories">Accessories</a>
		<a class="catlink" href="products?category%3DLuggage">Luggage</a>
		<a class="catlink" href="products?category%3DJewelry">Jewelry</a>
		<a class="catlink" href="products?category%3DWatches">Watches</a>
		<a class="catlink" href="products?category%3DExercise%20%26%20Fitness">Exercise &amp; Fitness</a>
		<a class="catlink" href="products?category%3DOutdoor%20Recreation">Outdoor Recreation</a>
		<a class="catlink" href="products?category%3DHunting%20%26%20Fishing">Hunting &amp; Fishing</a>
		<a class="catlink" href="products?category%3DCycling">Cycling</a>
		<a class="catlink" href="products?category%3DAthletic%20%26%20Outdoor%20Clothing">Athletic &amp; Outdoor Clothing</a>
		<a class="catlink" href="products?category%3DBoating%20%26%20Water%20Sports">Boating &amp; Water Sports</a>
		<a class="catlink" href="products?category%3DTeam%20Sports">Team Sports</a>
		<a class="catlink" href="products?category%3DFan%20Shop">Fan Shop</a>
		<a class="catlink" href="products?category%3DSports%20Collectibles">Sports Collectibles</a>
		<a class="catlink" href="products?category%3DGolf">Golf</a>
		<a class="catlink" href="products?category%3DAll%20Sports%20%26%20Outdoors">All Sports &amp; Outdoors</a>
		<a class="catlink" href="products?category%3DAutomotive%20Parts%20%26%20Accessories">Automotive Parts &amp; Accessories</a>
		<a class="catlink" href="products?category%3DAutomotive%20Tools%20%26%20Equipment">Automotive Tools &amp; Equipment</a>
		<a class="catlink" href="products?category%3DCar%20Electronics%20%26%20GPS">Car Electronics &amp; GPS</a>
		<a class="catlink" href="products?category%3DTires%20%26%20Wheels">Tires &amp; Wheels</a>
		<a class="catlink" href="products?category%3DMotorcycle%20%26%20ATV">Motorcycle &amp; ATV</a>
		<a class="catlink" href="products?category%3DIndustrial%20Supplies">Industrial Supplies</a>
		<a class="catlink" href="products?category%3DLab%20%26%20Scientific">Lab &amp; Scientific</a>
		<a class="catlink" href="products?category%3DJanitorial">Janitorial</a>
		<a class="catlink" href="products?category%3DSafety">Safety</a>		
	</div>

</body>

</html>