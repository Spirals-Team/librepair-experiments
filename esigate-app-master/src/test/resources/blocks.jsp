<?xml version="1.0" encoding="ISO-8859-1" ?>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Exemple d'inclusion d'un bloc</title>
<base href="http://localhost:8080/esigate-app-provider/block.jsp" />
</head>
<body style="background-color: yellow">
Page servie par l'application
<br />
Le tag include permet d'int�grer des blocs de contenu fournis par le provider

	<div style="background-color: aqua">Bloc de contenu<br />
	<img src="images/smile.jpg" />&lt;--image g�r�e par le provider</div>

NB : l'image est servie directement par le serveur distant grace �
l'utilisation du tag base.

	<div style="background-color: aqua">Autre bloc de contenu<br />

</body>
</html>