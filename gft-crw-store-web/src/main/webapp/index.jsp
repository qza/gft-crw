<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<link rel='stylesheet' type='text/css' media='screen'
	href='<c:url value="/resources/css/style.css"/>' />
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/resources/css/jquery-ui-custom.css"/>" />

<script type='text/javascript'
	src='<c:url value="/resources/js/script.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/resources/js/jquery.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/resources/js/jquery-ui-custom.min.js"/>'></script>
<script type='text/javascript'
	src='<c:url value="/resources/js/jquery.jqGrid.js"/>'></script>


<title>Products</title>
</head>

<body>
	<h1>Products</h1>

	<table id="table"></table>

	<div id="pager"></div>

	<script type="text/javascript">
		jQuery("#table").jqGrid(
				{
					url : 'products',
					datatype : "json",
					colNames : [ 'Name', 'Category', 'Price', 'Rating', 'Url', 'Related', 'Tags'],
					colModel : [ {
						name : 'name',
						index : 'name',
						width : 100
					}, {
						name : 'category',
						index : 'category',
						width : 70
					}, {
						name : 'price',
						index : 'price',
						width : 50,
						align : "right"
					}, {
						name : 'rating',
						index : 'rating',
						width : 50
					}, {
						name : 'url',
						index : 'url',
						width : 100
					}, {
						name : 'related',
						index : 'related',
						width : 100
					}, {
						name : 'tags',
						index : 'tags',
						width : 100,
						sortable : false
					} ],
					rowNum : 50,
					rowList : [ 50, 100, 500, 1000 ],
					pager : '#pager',
					sortname : 'name',
					viewrecords : true,
					sortorder : "desc",
					caption : "Product data"
				});
		jQuery("#table").jqGrid('navGrid', '#pager', {
			edit : false,
			add : false,
			del : false
		});
	</script>
</body>

</html>