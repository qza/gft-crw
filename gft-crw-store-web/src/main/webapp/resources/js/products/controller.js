function ProductsController(props) {

	var url = props.url;
	
	this.submit = function(params, callback) {
		$.post(url, params, function(response) {
			callback(response);
		}).fail(function() {
			alert("Error posting data");
		});
	};
	
	this.load = function(params, callback) {
		$.get(url, params, function(response) {
			callback(response);
		}).fail(function() {
			alert("Error getting data");
		});
	};
	
	this.loadCategory = function(link, callback) {
		$.get(link, function(response) {
			callback(response);
		}).fail(function() {
			alert("Error getting category");
		});
	};

}