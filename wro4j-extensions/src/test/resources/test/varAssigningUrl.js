(function() {
	var rawUrl1 = 'http://java.sun.com/webapps/getjava/BrowserRedirect?host=java.com';
	var rawUrl2 = "http://java.sun.com/webapps/getjava/BrowserRedirect?host=java.com";
	var encodedUrl1 = "http%3A%2F%2Fjava.sun.com%2Fwebapps%2Fgetjava%2FBrowserRedirect%3Fhost%3Djava.com";
	console.log(rawUrl1);
	console.log(rawUrl2);
	console.log(encodedUrl1);
})();