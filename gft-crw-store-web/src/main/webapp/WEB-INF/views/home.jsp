<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<link rel='stylesheet' type='text/css' media='screen'
	href='<c:url value="/resources/css/style.css"/>' />

<link rel='stylesheet' type='text/css' media='screen'
	href='<c:url value="/resources/css/jquery/jquery-ui-1.10.3.custom.css"/>' />	

<link href='http://fonts.googleapis.com/css?family=Noto+Sans:400,700' rel='stylesheet' type='text/css'>

<script type='text/javascript'
	src='http://code.jquery.com/jquery-1.9.1.min.js'></script>
	
<script type='text/javascript'
	src='<c:url value="/resources/js/jquery/jquery-ui-1.10.3.custom.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/components/navigator.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/components/progress.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/products/view.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/products/controller.js"/>'></script>

<title>Products</title>

</head>

<body>

	<div id="categories_div">
	</div>

	<div id="logo_div" style="display:none;">
		<img alt="logo" src="resources/css/logo.png">
	</div>

	<div id="content_div" style="display:none;">

		<div id="head_div">
			<div>
				<span id="stats"></span>
			</div>
		</div>
		
		<h1>Products</h1>

		<form id="product_form" style="display:none">
			<table id="products_table"></table>
			<input id="page_number" type="hidden" name="pageNumber" value="0" />
		</form>
		
		<div id="progress_bar">
		</div>
		
	</div>

	<script type="text/javascript">
		$(function() {
			new ProductsView({
				url : "products",
				form : "product_form",
				content: "content_div",
				table : "products_table",
				progress : "progress_bar",
				page : "page_number",
				stats : "stats",
				logo: "logo_div"
			}).init();
		});
	</script>

</body>

</html>