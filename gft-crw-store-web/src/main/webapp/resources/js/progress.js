function Progress() {

	var step = 0;
	var state = 0;

	this.init = function(count) {
		step = 100 / count;
		var top = bar().offset().top;
		$("body").animate({
			scrollTop : top
		}, 200);
	};

	this.progress = function() {
		state += step;
		var newWidth = state * bar().width() / 100;
		bar_div().animate({
			width : newWidth
		}, 30);
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

	function bar() {
		return $("#progress_bar");
	}

	function bar_div() {
		return bar().find("div");
	}

}