function Home() {

	var navigator = new Navigator();

	this.bindSubmit = function() {
		var home = this;
		$("#product_form").on("submit", function(event) {
			event.preventDefault();
			home.doSubmit(home, event);
		});
	};

	this.doSubmit = function(home) {
		var params = $(this).serialize();
		home.submitProductSelection(params);
	};

	this.bindCategoryLinks = function() {
		var home = this;
		$("a.catlink").on("click", function(event) {
			event.preventDefault();
			home.loadCategory($(this));
		});
	};

	this.getUrl = function() {
		return 'products?pageNumber=' + $("#page_number").val();
	};

	this.loadTable = function() {
		var home = this;
		$.get(this.getUrl(), function(response) {
			home.processResponse(response);
		});
	};

	this.loadCategory = function(categoryLink) {
		var home = this;
		$.get(categoryLink.attr("href"), function(response) {
			home.processResponse(response);
		});
	};

	this.processResponse = function(response) {
		if (response != null) {
			this.fillTable(response);
			navigator.toggleSelected();
		}
	};

	this.submitProductSelection = function(params) {
		var home = this;
		$.post(this.getUrl(), params, function(response) {
			if (response != null) {
				home.fillTable(response);
			} else {
				alert('Failure! No response');
			}
		});
	};

	this.fillTable = function(response) {
		$('#products_table').find('tbody').remove();
		var products = response.products;
		for ( var i = 0; i < products.length; i++) {
			this.fillRow(products[i]);
		}
		$('#products_table').data('model', products);
		$("#page_number").val(response.page.number);
		navigator.resetNavigation();
	};

	this.fillRow = function(product) {
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
		row += '<p>' + this.makeCategoryLink(product.category) + '</p>';
		row += '<p>' + product.price + '</p>';
		row += '<p>' + product.rating + '</p>';
		row += '</td>';
		if (product.image) {
			row += '<td><img src="' + product.image + '"/>' + '</td>';
		}
		row += '</tr>';
		$('#products_table').append(row);
	};

	this.makeCategoryLink = function(name) {
		var href = 'products?' + encodeURIComponent("category=" + name);
		return '<li><a class="catlink" href="' + href + '">' + name
				+ '</a></li>';
	};

	this.initializeNavigation = function() {
		var home = this;
		navigator.initializeNavigation(function() {
			home.doSubmit(home);
		});
	};

};
