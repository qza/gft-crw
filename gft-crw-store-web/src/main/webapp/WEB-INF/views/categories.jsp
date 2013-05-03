<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<link rel='stylesheet' type='text/css' media='screen'
	href='<c:url value="/resources/css/style.css"/>' />

<link href='http://fonts.googleapis.com/css?family=Noto+Sans:400,700' rel='stylesheet' type='text/css'>

<script type='text/javascript'
	src='http://code.jquery.com/jquery-1.9.1.min.js'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/components/navigator.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/components/progress.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/products/view.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/products/controller.js"/>'></script>

<title>Categories</title>

</head>

<body>

	<div id="logo_div" style="display:none;">
		<img alt="logo" src="resources/css/logo.png">
	</div>

	<div id="content_div" style="display:none;">

		<div id="head_div">
			<div>
				<span id="stats"></span>
			</div>
		</div>
		
		<h1>Categories</h1>
		
		<div id="categories_div">
			
			<a href='#' class='category_link'>Toys & Games</a>
			<a href='#' class='category_link'>Sports & Outdoors</a>
			<a href='#' class='category_link'>Sports & Outdoors</a>
			<a href='#' class='category_link'>Home & Kitchen</a>
			<a href='#' class='category_link'>Patio, Lawn & Garden</a>
			<a href='#' class='category_link'>Baby</a>
			<a href='#' class='category_link'>Office & School Supplies</a>
			<a href='#' class='category_link'>Clothing & Accessories</a>
			<a href='#' class='category_link'>Arts, Crafts & Sewing</a>
			<a href='#' class='category_link'>Kitchen & Dining</a>
			<a href='#' class='category_link'>Musical Instruments</a>
			<a href='#' class='category_link'>Home Improvement</a>
			<a href='#' class='category_link'>Camera & Photo</a>
			<a href='#' class='category_link'>Automotive</a>
			<a href='#' class='category_link'>Health & Personal Care</a>
			<a href='#' class='category_link'>Furniture & Décor</a>
			<a href='#' class='category_link'>All Electronics</a>
			<a href='#' class='category_link'>Beauty</a>
			<a href='#' class='category_link'>Pet Supplies</a>
			<a href='#' class='category_link'>GPS & Navigation</a>
			<a href='#' class='category_link'>Watches</a>
			<a href='#' class='category_link'>Computers</a>
			<a href='#' class='category_link'>Software</a>
			<a href='#' class='category_link'>Jewelry</a>
			<a href='#' class='category_link'>Appliances</a>
			<a href='#' class='category_link'>Car Electronics</a>
			<a href='#' class='category_link'>Cell Phones & Accessories</a>
			<a href='#' class='category_link'>MP3 Players & Accessories</a>
			<a href='#' class='category_link'>Industrial & Scientific</a>
			
		</div>

	</div>

	<script type="text/javascript">
		$(function() {
			new CategoriesView({
				content: "content_div",
				progress : "progress_bar",
				stats : "stats",
			}).init();
		});
	</script>

</body>

</html>