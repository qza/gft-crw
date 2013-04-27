function Navigator() {

	var row = 0;
	var row_count = 20;

	this.initializeNavigation = function(enterProcessor) {
		var nav = this;
		$(document).keyup(function(e) {
			var code = e.which;
			if (code != 13) {
				e.preventDefault();
			} else {
				enterProcessor();
			}
			nav.processKey(code);
		});
	};

	this.resetNavigation = function() {
		this.row = 0;
		this.scrollToSelectedRow();
		this.getRow().toggleClass("selected");
	};

	this.processKey = function(code) {
		if (code == 38) {
			this.decrease();
			this.toggleSelected();
		}
		if (code == 40) {
			this.increase();
			this.toggleSelected();
		}
		if (code == 37 || code == 39) {
			this.toggle4Gift();
		}
		this.scrollToSelectedRow();
	};

	this.scrollToSelectedRow = function() {
		var top = this.getRow().offset().top;
		top = top > 20 ? (top - 20) : top;
		$("body").animate({
			scrollTop : top
		}, 200);
	};

	this.toggleSelected = function() {
		this.getRows().each(function() {
			$(this).removeClass("selected");
		});
		this.getRow().toggleClass("selected");
	};

	this.toggle4Gift = function() {
		var row = this.getRow();
		row.toggleClass("for_gift");
		this.toggleCheckbox();
	};

	this.toggleCheckbox = function() {
		var check = this.getRow().find('td.check input');
		var isCheck = check.prop("checked");
		check.prop("checked", !isCheck);
	};

	this.getRow = function() {
		return this.getRows().eq(row);
	};

	this.getRows = function() {
		return $('#products_table tbody tr');
	};

	this.increase = function() {
		row = this.hasNext() ? (row + 1) : (row_count - 1);
	};

	this.decrease = function() {
		row = this.hasPrevious() ? (row - 1) : 0;
	};

	this.hasPrevious = function() {
		return row > 0;
	};

	this.hasNext = function() {
		return row < (row_count - 1);
	};

}