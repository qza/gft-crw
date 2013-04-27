function Home() {

	var navigator = new Navigator();

	this.initAll = function() {
		loadTable();
		bindSubmit();
		initializeNavigation();
		bindCategoryLinks();
	};

	// Private members

	function loadTable() {
		$.get(getUrl(), function(response) {
			processResponse(response);
		});
	}

	function bindSubmit() {
		$("#product_form").on("submit", function(event) {
			event.preventDefault();
			doSubmit();
		});
	}

	function bindCategoryLinks() {
		$("a.catlink").on("click", function(event) {
			event.preventDefault();
			loadCategory($(this).attr("href"));
		});
	}

	function getUrl() {
		return 'products?pageNumber=' + $("#page_number").val();
	}

	function doSubmit() {
		var params = $("#product_form").serialize();
		submitProductSelection(params);
	}

	function submitProductSelection(params) {
		$.post(getUrl(), params, function(response) {
			processResponse(response);
		});
	}
	
	function loadCategory(categoryLink) {
		$.get(categoryLink, function(response) {
			processResponse(response);
		});
	}
	
	function processResponse(response) {
		if (response != null) {
			fillTable(response);
			navigator.toggleSelected();
		}
	}

	function fillTable(response) {
		$('#products_table').find('tbody').remove();
		var products = response.products;
		for ( var i = 0; i < products.length; i++) {
			fillRow(products[i]);
		}
		$('#products_table').data('model', products);
		$("#page_number").val(response.page.number);
		navigator.resetNavigation();
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
		row += '<input type="checkbox" name="cb" value="' + product._id.$oid
				+ '" ';
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
		var href = 'products?category=' + encodeURIComponent(name);
		return '<a class="catlink" href="' + href + '">' + name + '</a>';
	}

	function initializeNavigation() {
		navigator.initializeNavigation(function() {
			doSubmit();
		});
	}

};
