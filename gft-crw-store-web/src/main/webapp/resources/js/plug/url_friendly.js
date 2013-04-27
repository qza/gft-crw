$.fn.slug = function() {
	return this.val().replace(/[^a-zA-Z 0-9-]+/g, '').toLowerCase().replace(
			/\s/g, '-');
};