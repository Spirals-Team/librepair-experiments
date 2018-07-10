<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
	<head>
	
	</head>
	<body>
		<ul>
		<c:forEach items="${ listeStagiaires}" var="st">
			<li>${st.nom }</li>
		</c:forEach>
		</ul>
	</body>
</html>