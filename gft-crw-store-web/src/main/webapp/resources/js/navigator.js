var row = 0;
var row_count = 20;

function initializeNavigation() {
	$(document).keyup(function(e) {
		var code = e.which;
		if (code != 13) {
			e.preventDefault();
		}
		processKey(code);
	});
}

function processKey(code) {
	if (code == 38) {
		decrease();
		toggleSelected();
	}
	if (code == 40) {
		increase();
		toggleSelected();
	}
	if (code == 37 || code == 39) {
		toggle4Gift();
	}
	scrollToSelectedRow();
}

function scrollToSelectedRow() {
	var top = getRow().offset().top;
	top = top > 20 ? (top - 20) : top;
	$("body").animate({
		scrollTop : top
	}, 250);
}

function toggleSelected() {
	getRows().each(function() {
		$(this).removeClass("selected");
	});
	getRow().toggleClass("selected");
}

function toggle4Gift() {
	var row = getRow();
	row.toggleClass("for_gift");
	toggleCheckbox();
}

function toggleCheckbox() {
	var check = getRow().find('td.check input');
	var isCheck = check.prop("checked");
	check.prop("checked", !isCheck);
}

function getRow() {
	return getRows().eq(row);
}

function getRows() {
	return $('table tbody tr');
}

function increase() {
	row = hasNext() ? (row + 1) : row_count;
}

function decrease() {
	row = hasPrevious() ? (row - 1) : 0;
}

function hasPrevious() {
	return row > 0;
}

function hasNext() {
	return row < (row_count - 1);
}