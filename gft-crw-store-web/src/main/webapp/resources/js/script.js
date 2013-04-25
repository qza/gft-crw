$(function(){
	$("#product_form").on("submit", function(event) {
		event.preventDefault();
		var params = $(this).serialize();
		submitProductSelection(params);
	});
});

function loadTable() {
	var url = 'products?page_number=' + $("#page_number").val();
	$.get(productsUrl(), function(response) {
		if (response != null) {
			fillTable(response);
		}
	});
}

function submitProductSelection(params) {
	var url = 'products_save;
	$.post(productsUrl(), params, function(response) {
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
		var row = '<tr>';
		row += '<td><input type="checkbox" name="cb" value="'
				+ products[i]._id.$oid + '"></td>';
		row += '<td>' + products[i].name + '</td>';
		row += '<td>' + products[i].category + '</td>';
		row += '<td>' + products[i].price + '</td>';
		row += '<td>' + products[i].rating + '</td>';
		if (products[i].image) {
			row += '<td><img src="' + products[i].image + '"/>' + '</td>';
		}
		row += '</tr>';
		$('#table').append(row);
	}
	$('#table').data('model', products);
	$("#page_number").val(response.page.number);
}
