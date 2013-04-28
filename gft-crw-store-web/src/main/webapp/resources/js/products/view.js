function ProductsView(props) {

	var ctr = new ProductsController(props);

	var nav = new Navigator(props.table, 20);

	var form = elem(props.form);

	var table = elem(props.table);

	var page = elem(props.page);
	
	var stats = elem(props.stats);

	this.init = function() {
		form.hide();
		$.get(props.url, function(response) {
			processResponse(response);
		}).done(function() {
			bindActions();
			initNavigation();
		}).fail(function() {
			alert("Error getting data");
		});
	};

	function bindActions() {
		bindForm();
		bindCategories();
	}

	function bindForm() {
		form.on("submit", function() {
			return doSubmit();
		});
	}

	function bindCategories() {
		$(".catlink").on("click", function() {
			var addr = $(this).attr("href");
			ctr.loadCategory(addr, function(response) {
				processResponse(response);
			});
			return false;
		});
	}

	function initNavigation() {
		nav.init(function(code) {
			if (code == 13) {
				doSubmit();
			}
			if (code == 38) {
				nav.decrease();
				toggleSelected();
			}
			if (code == 40) {
				nav.increase();
				toggleSelected();
			}
			if (code == 37 || code == 39) {
				toggle4Gift();
			}
			nav.scrollToRow();
		});
	}

	function toggleSelected() {
		nav.getRows().each(function() {
			$(this).removeClass("selected");
		});
		nav.getRow().toggleClass("selected");
	}

	function toggle4Gift() {
		var row = nav.getRow();
		row.toggleClass("for_gift");
		toggleCheckbox();
	}

	function toggleCheckbox() {
		var check = nav.getRow().find('td.check input');
		var isCheck = check.prop("checked");
		check.prop("checked", !isCheck);
	}

	function doSubmit() {
		var params = form.serialize();
		ctr.submit(params, function(response) {
			processResponse(response);
		});
		return false;
	}

	function processResponse(response) {
		if (response) {
			fillTable(response, function(){
				nav.reset();
				toggleSelected();
				bindCategories();
				fillStats(response);
			});
		}
	}

	function fillTable(response, callback) {
		var progress = new Progress(props.progress);
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
			page.val(response.page.number);
			showHide(form, progress);
			table.promise().done(function() {
				callback();
			});
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
		table.append(row);
	}

	function makeCategoryLink(name) {
		var href = 'products?category=' + encodeURIComponent(name);
		return '<a class="catlink" href="' + href + '">' + name + '</a>';
	}
	
	function fillStats(response){
		stats.html("");
		var content = "";
		var rstats = response.stats;
		content += "<p> Record count: <b>" + rstats.recordCount + " </b></p>";
		content += "<p> Visited count: <b>" + rstats.visitedCount + "</b></p>";
		content += "<p> For gift count: <b>" + rstats.forGiftCount + "</b></p>";
		stats.html(content);
	}

	function showHide(elem1, elem2) {
		elem2.hide();
		elem1.show();
	}
	
	function elem(id){
		return $("#" + id);
	}

}