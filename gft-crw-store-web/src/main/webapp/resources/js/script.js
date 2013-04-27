$(function() {
	bindSubmit();
	initializeNavigation();
});

function bindSubmit() {
	$("#product_form").on("submit", function(event) {
		event.preventDefault();
		var params = $(this).serialize();
		submitProductSelection(params);
	});
}

function getUrl() {
	return 'products?pageNumber=' + $("#page_number").val();
}

function loadTable() {
	$.get(getUrl(), function(response) {
		if (response != null) {
			fillTable(response);
			toggleSelected();
		}
		resetNavigation();
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
	$('#products_table').find('tbody').remove();
	var products = response.products;
	for ( var i = 0; i < products.length; i++) {
		fillRow(products[i]);
	}
	$('#products_table').data('model', products);
	$("#page_number").val(response.page.number);
	resetNavigation();
}

function fillRow(product) {
	var row = '';
	var is4g = product.for_gift && product.for_gift == true;
	if (is4g) {
		row += '<tr class="for_gift">';
	} else {
		row += '<tr>';
	}
	row += '<td class="check">';
	row += '<input type="checkbox" name="cb" value="' + product._id.$oid + '" ';
	row += (is4g ? 'checked' : '') + '/>';
	row += '</td>';
	row += '<td>';
	row += '<p>' + product.name + '</p>';
	row += '<p>' + makeCategoryLink(product.category) + '</p>';
	row += '<p>' + product.price + '</p>';
	row += '<p>' + product.rating + '</p>';
	row += '</td>';
	if (product.image) {
		row += '<td><img src="' + product.image + '"/>' + '</td>';
	}
	row += '</tr>';
	$('#products_table').append(row);
}

function makeCategoryLink(name) {
	var href = encodeURIComponent("products?category=" + name);
	return '<li><a class="catlink" href="' + href + '">' + name + '</a></li>';
}