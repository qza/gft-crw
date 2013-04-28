function Navigator(table_id, row_count) {

	var row = 0;
	var table = $("#" + table_id);

	this.init = function(keyProcessor) {
		$(document).keyup(function(e) {
			e.preventDefault();
			var code = e.which;
			keyProcessor(code);
		});
	};

	this.reset = function() {
		row = 0;
		scrollToTop();
	};

	this.getRows = function() {
		return table.find('tr');
	};

	this.getRow = function() {
		return this.getRows().eq(row);
	};

	this.scrollToRow = function(top_pad) {
		var top = this.getRow().offset().top;
		top = top > top_pad ? (top - top_pad) : top;
		$("body").animate({
			scrollTop : top
		}, 200);
	};

	this.increase = function() {
		row = hasNext() ? (row + 1) : (row_count - 1);
	};

	this.decrease = function() {
		row = hasPrevious() ? (row - 1) : 0;
	};

	// Private members

	function scrollToTop() {
		$("body").animate({
			scrollTop : 1
		}, 200);
	}

	function hasPrevious() {
		return row > 0;
	}

	function hasNext() {
		return row < (row_count - 1);
	}

}