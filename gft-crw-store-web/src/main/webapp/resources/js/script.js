function Home() {

	var navigator = new Navigator();

	this.initAll = function() {
		$("#product_form").hide();
		$.get(getUrl(), function(response) {
			processResponse(response);
		}).done(function() {
			bindSubmit();
			bindCategoryLinks();
			initializeNavigation();
		}).fail(function() {
			alert("Error getting data");
		});
	};

	// Private members

	function bindSubmit() {
		$("#product_form").on("submit", function(event) {
			event.preventDefault();
			doSubmit();
		});
	}

	function bindCategoryLinks() {
		$(".catlink").on("click", function() {
			var addr = $(this).attr("href");
			loadCategory(addr);
			return false;
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
		}).fail(function() {
			alert("Error posting data");
		});
	}

	function loadCategory(categoryLink) {
		$.get(categoryLink, function(response) {
			processResponse(response);
		}).fail(function() {
			alert("Error loading category data");
		});
	}

	function processResponse(response) {
		if (response != null) {
			fillTable(response);
			bindCategoryLinks();
			navigator.resetNavigation();
		}
	}

	function fillTable(response) {
		var progress = new Progress();
		var form = $("#product_form");
		var table = $('#products_table');
		showHide(progress, form);
		table.find('tbody').remove();
		var products = response.products;
		progress.init(products.length);
		for ( var i = 0; i < products.length; i++) {
			fillRow(products[i]);
			progress.progress();
		}
		progress.done(function() {
			table.data('model', products);
			$("#page_number").val(response.page.number);
			showHide(form, progress);
		});
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

	function showHide(elem1, elem2) {
		elem2.hide();
		elem1.show();
	}

};
