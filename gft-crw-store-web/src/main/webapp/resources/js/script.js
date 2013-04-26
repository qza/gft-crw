$(function() {
	$("#product_form").on("submit", function(event) {
		event.preventDefault();
		var params = $(this).serialize();
		submitProductSelection(params);
	});
});

function getUrl() {
	return 'products?page_number=' + $("#page_number").val();
}

function loadTable() {
	$.get(getUrl(), function(response) {
		if (response != null) {
			fillTable(response);
		}
	});
}

function submitProductSelection(params) {
	$.post(getUrl(), params, function(response) {
		if (response != null) {
			fillTable(response);
		} else {
			alert('Failure! No response');
		}
	});
}

function fillTable(response) {
	$('#table').find('tbody').remove();
	var products = response.products;
	for ( var i = 0; i < products.length; i++) {
		fillRow(products[i]);
	}
	$('#table').data('model', products);
	$("#page_number").val(response.page.number);
}


function fillRow(product) {
	var row = '<tr>';
	row += '<td><input type="checkbox" name="cb" value="'
			+ product._id.$oid + '"></td>';
	row += '<td>' + '<p>' + product.name + '</p>' + '<p>'
			+ product.category + '</p>' + '<p>' + product.price + '</p>'
			+ '<p>' + product.rating + '</p>' + '</td>';
	if (product.image) {
		row += '<td><img src="' + product.image + '"/>' + '</td>';
	}
	row += '</tr>';
	$('#table').append(row);
}
