<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<link rel='stylesheet' type='text/css' media='screen'
	href='<c:url value="/resources/css/style.css"/>' />

<link href='http://fonts.googleapis.com/css?family=Noto+Sans:400,700' rel='stylesheet' type='text/css'>

<script type='text/javascript'
	src='http://code.jquery.com/jquery-1.9.1.min.js'></script>

<script type='text/javascript'
	src='<c:url value="/resources/js/report/socket.js"/>'></script>


<title>Report</title>

</head>

<body>

	<h1>Report</h1>
	
	<div id="report_div">
		<textarea id="responseText" rows="100" cols="200"></textarea>
	</div>

	<script type="text/javascript">

	</script>

</body>

</html>