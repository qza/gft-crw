function Progress(holder) {

	var step = 0;
	var state = 0;

	this.init = function(count) {
		step = 100 / count;
		scrollTo(bar().offset().top);
		create();
	};

	this.progress = function() {
		state += step;
		var newWidth = state * bar().width() / 100;
		bar_div().animate({
			width : newWidth
		}, 25);
	};

	this.hide = function() {
		bar().hide();
	};

	this.show = function() {
		bar().show();
	};

	this.done = function(callback) {
		bar_div().promise().done(function() {
			callback();
		});
	};

	function create() {
		bar().html("");
		bar().append("<div></div>");
	}

	function scrollTo(yCord) {
		$("body").animate({
			scrollTop : yCord
		}, 200);
	}

	function bar() {
		return $("#" + holder);
	}

	function bar_div() {
		return bar().find("div");
	}

}