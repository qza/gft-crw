function ProductsView(props) {

	var ctr = new ProductsController(props);

	var nav = new Navigator(props.table, 20);

	var progress = new Progress(props.progress);

	var form = elem(props.form);

	var table = elem(props.table);

	var page = elem(props.page);

	var stats = elem(props.stats);

	var content = elem(props.content);

	var logo = elem(props.logo);

	var title = $("h1");

	var initialTitle = title.text();

	this.init = function() {
		initializeView();
		getPage();
	};
	
	function initializeView() {
		logo.fadeIn(2000, function() {
			logo.fadeOut(1500, function() {
				content.fadeIn(500);
			});
		});
	}
	
	function getPage(pageNumber){
		showProgress();
		var pNum = pageNumber? pageNumber : page.val(); 
		$.get(props.url+"?pageNumber=" + pNum, function(response) {
			form.show();
			processResponse(response);
		}).done(function() {
			bindActions();
			initNavigation();
		}).fail(function() {
			alert("Error getting data");
		});
	}

	function bindActions() {
		bindForm();
		bindCategories();
		bindRows();
	}

	function bindForm() {
		form.on("submit", function() {
			return doSubmit();
		});
		title.on("click", function() {
			$("#category").val("");
			showProgress();
			getPage(0);
		});
		title.on("mouseover", function(){
			$(this).css('cursor', 'pointer');
		});
		title.on("mouseout", function(){
			$(this).css('cursor', 'default');
		});
	}

	function bindCategories() {
		$(".catlink").on("click", function() {
			showProgress();
			var addr = $(this).attr("href");
			ctr.loadCategory(addr, function(response) {
				processResponse(response);
			});
			return false;
		});
	}

	function bindRows() {
		table.find("tr").on("click", function() {
			goToRow($(this));
		});
		table.find("tr td input[type='checkbox']").on("click", function() {
			var row = $(this).parent().parent(); 
			goToRow(row);
			var isCheck = $(this).prop("checked");
			if(isCheck == true){
				row.addClass("for_gift");
			} else {
				row.removeClass("for_gift");
			}
		});
	}

	function goToRow(row) {
		var i = row.attr("id").replace("row_", "");
		nav.setRow(i - 1);
		toggleSelected();
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
			nav.scrollToRow(70);
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
	
	function getIds() {
		var ids = "";
		var cbs = $("input[name='cb']");
		var cbs_len = cbs.length;
		for (var i = 0 ; i < cbs_len ; i++ ){
			ids += "ids=" + cbs[i].getAttribute("value");
			if(i < (cbs_len - 1)){
				ids += "&";
			}
		}
		return ids;
	}

	function doSubmit() {
		showProgress();
		var params = form.serialize() + "&" + getIds();
		ctr.submit(params, function(response) {
			processResponse(response);
		});
		return false;
	}

	function processResponse(response) {
		if (response) {
			fillTable(response, function() {
				nav.reset();
				toggleSelected();
				bindCategories();
				bindRows();
				fillStats(response);
				hideProgress();
			});
		}
	}
	
	function fillTable(response, callback) {
		table.find('tbody').remove();
		var products = response.data.content;
		progress.setCount(products.length);
		for ( var i = 0; i < products.length; i++) {
			fillRow(i, products[i]);
			progress.progress();
		}
		progress.done(function() {
			table.data('model', products);
			page.val(response.data.number);
			table.promise().done(function() {
				callback();
			});
		});
	}

	function fillRow(i, product) {
		var row = '';
		var is4g = product.for_gift && product.for_gift == true;
		if (is4g) {
			row += '<tr id="row_' + (i + 1) + '" class="for_gift">';
		} else {
			row += '<tr id="row_' + (i + 1) + '">';
		}
		row += '<td class="check">';
		row += '<input type="checkbox" name="cb" value="' + product.id
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

	function fillStats(response) {
		stats.html("");
		var content = "";
		content += "<p> Products : <b>" + response.stats.recordCount + " </b> </p>";
		content += "<p> Gifts: <b>" + response.stats.forGiftCount + "</b> : : </p>";
		content += "<p> Page : <b>" + getPageNumber() + "</b> of <b>" + response.data.totalPages + "</b> : : </p>";
		stats.html(content);
	}
	
	function getPageNumber(){
		var number = $("#page_number").val();
		return parseInt(number);
	}

	function showProgress() {
		$('body').css('cursor', 'wait');
		progress.init();
		stats.fadeOut(200);
		table.fadeOut(200, function() {
			progress.getBar().fadeIn(300, function(){
				title.text("Loading... ");
			});
		});
	}
	
	function hideProgress() {
		$('body').css('cursor', 'default');
		progress.getBar().fadeOut(50, function() {
			table.fadeIn(100, function() {
				stats.fadeIn(100);
				title.text(initialTitle);
			});
		});
	}

	function elem(id) {
		return $("#" + id);
	}

}