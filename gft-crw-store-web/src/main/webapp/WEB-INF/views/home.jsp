<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<link rel='stylesheet' type='text/css' media='screen'
	href='<c:url value="/resources/css/style.css"/>' />

<script type='text/javascript'
	src='<c:url value="/resources/js/jquery.js"/>'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/script.js"/>'></script>

<title>Products</title>

</head>

<body>

	<h1>Products</h1>

	<form id="product_form">
		<table id="table"></table>
		<input id="submit" type="submit" name="submit_button" value="submit" />
		<input id="page_number" type="hidden" name="page_number" value="1" />
	</form>

	<script type="text/javascript">
		loadTable();
	</script>

</body>

</html>