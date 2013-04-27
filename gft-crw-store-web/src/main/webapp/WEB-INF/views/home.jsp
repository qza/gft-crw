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
	src='<c:url value="/resources/js/progress.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/script.js"/>'></script>

<title>Products</title>

</head>

<body>

	<div id="form_div">

		<h1>Products</h1>

		<div id="progress_bar">
			<div></div>
		</div>

		<form id="product_form">
			<table id="products_table"></table>
			<input id="submit" type="submit" name="submit_button" value="submit" />
			<input id="page_number" type="hidden" name="page_number" value="1" />
		</form>

	</div>

	<script type="text/javascript">
		$(function() {
			new Home().initAll();
		});
	</script>

</body>

</html>