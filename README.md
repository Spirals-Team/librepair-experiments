[![Build Status](https://travis-ci.org/Jmaquin/kata-mastermind.svg?branch=master)](https://travis-ci.org/Jmaquin/kata-mastermind)
[![codecov](https://codecov.io/gh/Jmaquin/kata-mastermind/branch/master/graph/badge.svg)](https://codecov.io/gh/Jmaquin/kata-mastermind)
# kata-mastermind

## Le Mastermind (implémentation)
Pour référence, voir https://fr.wikipedia.org/wiki/Mastermind

## Résultat attendu:
Une implémentation du jeu qui permette de jouer au mastermind avec les couleurs (rouge
(R), jaune (J), bleu (B), orange (O), vert (V), noir (N)).
Vous pouvez choisir le langage d’implémentation.

## Règles du jeu:

L’ordinateur choisit au hasard 4 pions ayant chacun une des 6 couleurs possibles (rouge
(R), jaune (J), bleu (B), orange (O), vert (V), noir (N)).
Par exemple: ROOJ pour (Rouge, Orange, Orange, Jaune).

L’utilisateur doit alors rentrer 4 lettres parmi R,J,B,O,V et N. Si l’utilisateur se trompe, un
message d’erreur lui indique.

Supposons que l’utilisateur entre NBJV, alors, l’ordinateur affiche un récapitulatif (affiché ici
sous forme de console) :

| Try   | True | Partially true | Turn |
| ----- |----  | -------------- |----- |
| NBJV  | 0    | 1              | 1/10 |
| ....  | .    | .              | 2/10 |

NBJV est donc ce que l’utilisateur a entré, puis la 1ère case contient le nombre de pions
“justes”, et la deuxième le nombre de pions de couleur juste mais mal placés.
Un pion “juste” est un pion qui a la bonne couleur et est bien placé.
Ici, l’utilisateur a un pion de bonne couleur mais mal placé: le J.

Le 1/10 représente le numéro du tour de l’utilisateur. L’utilisateur a 10 tours pour deviner la
combinaison choisie par l’ordinateur. S’il réussi en 10 coups ou moins, l’utilisateur gagne,
sinon il perd.