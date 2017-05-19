function isBlank(value) {
	if(typeof value === "undefined" || value == null || $.trim(value) == "") {
		return true;
	}
	
	return false;
}