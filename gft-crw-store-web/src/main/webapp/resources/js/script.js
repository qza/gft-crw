function getUrl() {
	return 'products?page_number=' + $("#page_number").val();
}

$(function() {
	$("#product_form").on("submit", function(event) {
		event.preventDefault();
		var params = $(this).serialize();
		submitProductSelection(params);
	});
	initializeNavigation();
});

function loadTable() {
	$.get(getUrl(), function(response) {
		if (response != null) {
			fillTable(response);
			toggleSelected();
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
	var row = '';
	var is4g = product.for_gift && product.for_gift == true;
	if (is4g) {
		row += '<tr class="for_gift">';
	} else {
		row += '<tr>';
	}
	row += '<td class="check"><input type="checkbox" name="cb" value="'
			+ product._id.$oid + '" ' + (is4g ? 'checked' : '') + '></td>';
	row += '<td>' + '<p>' + product.name + '</p>' + '<p>' + product.category
			+ '</p>' + '<p>' + product.price + '</p>' + '<p>' + product.rating
			+ '</p>' + '</td>';
	if (product.image) {
		row += '<td><img src="' + product.image + '"/>' + '</td>';
	}
	row += '</tr>';
	$('#table').append(row);
}

