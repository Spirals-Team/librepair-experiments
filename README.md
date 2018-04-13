<div>
	<img src="https://travis-ci.org/Master-DL/projet-ivvq-2017-2018-bfml.svg?branch=master" />
	<img src="https://sonarcloud.io/api/project_badges/measure?project=fr.m2dl.ivvq.bfml%3Asortir.ce.soir&metric=alert_status" />
</div>

# SORTIR CE SOIR 

Sortir ce soir est une application web qui permet à ses usagers de trouver un endroit où sortir.
Les usagers pourront appliquer à leurs recherches un certain nombre de filtre, de manière à trier les lieux qui répondent à leurs exigences.
Il sera possible, par exemple, de trouver un endroit à une certaine heure de la nuit où on peut sortir en famille (pas d’alcool, mineurs autorisés ...)  
L’application permet en plus d'accéder aux commentaires laissés par d’autres utilisateurs et d’en laisser à son tour. Ceci est accompagné d’un système de notation (étoile, valeur numérique)


# VOCABULAIRE

L'application permet de se donner des idées de soirée.
Pour cela, en plus des informations liées à son compte utilisateur, on peut définir des CRITERES pour sa soirée permettant de préciser le CONTEXTE de sa soirée (combien de personnes, quel âge, etc). On peut aussi préciser des PREFERENCES : sport, film, usique, danser, etc.
Une PROPOSITION de soirée correspond à un lieu, un ou plusieurs types d'activités, des commentaires qui lui sont liés, etc.


# LIENS

Doc Travis CI : goo.gl/9ce2X5
<br/><br/>
Image Travis "trusty" : goo.gl/sBYZpS
<br/><br/>
Definition of Done : goo.gl/u4zWQz
<br/><br/>


# DEPLOIEMENT
https://sortir-ce-soir.herokuapp.com/

# AUTHENTIFICATION

Pour tester l'inscription : curl --data "mail=test@test.fr&login=test&password=azerty&passwordConfirmation=azerty" http://localhost:8080/signup

Pour tester la connection : curl --data "login=test&password=azerty" http://localhost:8080/signin

Pour tester la page "mon compte" (nécessite de s'authentifier avec son token reçu lors de la connection) : curl -H "Authorization: <token>"  http://localhost:8080/my 
