<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Timeout test</title>
</head>
<body style="background-color: aqua">
<% 	
	response.setHeader("Cache-control", "public, max-age=60000");
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// Nothing to do
	}
%>
<div>If you read this, you must have been waiting 5 s</div>
<div style="border: 1px solid red"><esi:fragment name="block1">
	<div style="background-color: aqua">Content Block</div>
</esi:fragment></div>
</body>
</html>