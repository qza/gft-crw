function Progress(holder) {

	var step = 0;
	var state = 0;

	this.init = function(count) {
		state = 0;
		scrollTo(bar().offset().top);
		create();
	};
	
	this.setCount = function(count) {
		step = 100 / count;
		bar().css("background-color", "#e0e0e0");
	};
	
	this.progress = function() {
		state += step;
		var newWidth = state * bar().width() / 100;
		bar_div().animate({
			width : newWidth
		}, 25);
	};
	
	this.getBar = function(){
		return bar();
	};

	this.done = function(callback) {
		bar_div().promise().done(function() {
			bar().css("background-color", "white");
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