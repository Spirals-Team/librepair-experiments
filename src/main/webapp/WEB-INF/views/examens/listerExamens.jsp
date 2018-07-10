<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
	<head>
	
	</head>
	<body>
		<ul>
		<c:forEach items="${ examList}" var="exam">
			<li>${exam.titre }</li>
		</c:forEach>
		</ul>
	</body>
</html>